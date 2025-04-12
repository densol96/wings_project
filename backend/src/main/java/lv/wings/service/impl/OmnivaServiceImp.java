package lv.wings.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.request.omniva.TerminalLocationDto;
import lv.wings.enums.Country;
import lv.wings.enums.Status;
import lv.wings.model.entity.ActionLog;
import lv.wings.model.entity.GlobalParam;
import lv.wings.model.entity.Terminal;
import lv.wings.repo.ActionLogRepository;
import lv.wings.repo.GlobalParamsRepository;
import lv.wings.repo.TerminalRepository;
import lv.wings.service.OmnivaService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OmnivaServiceImp implements OmnivaService {

    private final GlobalParamsRepository paramRepo;
    private final ActionLogRepository actionLogRepository;
    private final TerminalRepository terminalRepo;

    private final String PARAM_KEY = "omniva_api_link";
    private final String JOB_KEY = "omniva_sync_job";


    private List<TerminalLocationDto> loadFreshDataFromApi() {
        RestTemplate rest = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            GlobalParam gb = paramRepo.findByTitle(PARAM_KEY).orElseThrow(() -> new Exception("Unable to load " + PARAM_KEY + " from a DB."));
            String jsonResponse = rest.getForObject(gb.getValue(), String.class);
            return objectMapper.readValue(jsonResponse, new TypeReference<List<TerminalLocationDto>>() {});
        } catch (Exception e) {
            logTheJob(Status.FAILURE, e.getMessage());
            return List.of();
        }
    }

    @Override
    @Scheduled(cron = "0 44 13 * * *") // 4am everyday
    public void syncDbDataWithApi() {
        List<TerminalLocationDto> terminalsFromApi = loadFreshDataFromApi();
        if (terminalsFromApi.isEmpty()) {
            // logging done in loadFreshDataFromApi => simply return
            return;
        }
        if (terminalRepo.count() == 0) {
            terminalsFromApi.forEach(this::saveToDb);
        } else {
            Map<String, TerminalLocationDto> terminalsFromApiMap = new HashMap<>();
            terminalsFromApi.forEach((tl -> terminalsFromApiMap.put(tl.getZip(), tl)));

            Map<String, Terminal> terminalsFromDbMap = new HashMap<>();
            List<Terminal> terminalsFromDb = terminalRepo.findAll();
            terminalsFromDb.forEach((tl -> terminalsFromDbMap.put(tl.getZip(), tl)));

            for (TerminalLocationDto tFromApi : terminalsFromApi) {
                if (!terminalsFromDbMap.containsKey(tFromApi.getZip())) {
                    saveToDb(tFromApi);
                } else {
                    Terminal isActive = terminalsFromDbMap.get(tFromApi.getZip());
                    Terminal versionFromApi = mapApiLocationToTerminal(tFromApi);
                    if (!isActive.equals(versionFromApi)) {
                        isActive.updateSelf(versionFromApi);
                        terminalRepo.save(isActive);
                    }
                }
            }

            List<Terminal> terminalsForDelete = terminalsFromDb.stream().filter((t) -> !terminalsFromApiMap.containsKey(t.getZip())).toList();
            // I am using a soft deletin here so it is safe and will not break the relationships
            terminalRepo.deleteAll(terminalsForDelete);
            logTheJob(Status.SUCCESS, "DB terminals successfully synchronized with Omniva API.");
        }
    }

    @Override
    public List<Terminal> getAllTerminals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTerminals'");
    }

    private void logTheJob(Status status, String message) {
        ActionLog actionLog = actionLogRepository.findByName(JOB_KEY).orElseGet(() -> ActionLog.builder().name(JOB_KEY).build());
        actionLog.setStatus(status);
        actionLog.setLastRunTime(LocalDateTime.now());
        actionLogRepository.save(actionLog);

        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (status == Status.FAILURE)
            log.error("Synchronising Omniva terminal's DB failed due to {} at {}", message, formattedDate);

        else
            log.info("Synchronising Omniva terminal's DB suceeded at {}", formattedDate);
    }

    private String buildFullAddress(TerminalLocationDto dto) {
        List<String> parts = Arrays.asList(
                dto.getA1Name(),
                dto.getA2Name(),
                dto.getA3Name(),
                dto.getA4Name(),
                dto.getA5Name(),
                dto.getA6Name(),
                dto.getA7Name(),
                dto.getA8Name());

        return parts.stream()
                .filter(part -> part != null && !part.isBlank())
                .collect(Collectors.joining(", "));
    }

    private Terminal mapApiLocationToTerminal(TerminalLocationDto t) {
        return Terminal.builder()
                .zip(t.getZip())
                .name(t.getName())
                .type(Integer.parseInt(t.getType()))
                .country(Country.valueOf(t.getA0Name()))
                .address(buildFullAddress(t))
                .xCoordinate(Double.parseDouble(t.getXCoordinate()))
                .yCoordinate(Double.parseDouble(t.getYCoordinate()))
                .build();
    }

    private void saveToDb(TerminalLocationDto t) {
        terminalRepo.save(mapApiLocationToTerminal(t));
    }



}
