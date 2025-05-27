package lv.wings.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.service.FrontendCacheInvalidator;

@RequiredArgsConstructor
@Service
@Slf4j
public class FrontendCacheInvalidatorImpl implements FrontendCacheInvalidator {

    private final RestTemplate restTemplate;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void invalidateProducts() {
        log.info("Notifying frontend about updated product stock");

        try {
            BasicMessageDto response = restTemplate.getForObject(frontendUrl + "/api/invalidate-product", BasicMessageDto.class);
            log.info("Next.js responded with message of {}", response.getMessage());
        } catch (HttpClientErrorException e) {
            log.error("Client error: {}, body: {}", e.getStatusCode(), e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            log.error("Server error: {}, body: {}", e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
