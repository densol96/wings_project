package lv.wings.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lv.wings.service.TranslationService;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

        @Value("${openai.api-key}")
        private String apiKey;

        @Value("${openai.base-url}")
        private String baseUrl;

        private final RestTemplate restTemplate;

        @Override
        public String translateToEnglish(String text) {
                String url = baseUrl + "/chat/completions";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(apiKey);

                Map<String, Object> requestBody = Map.of(
                                "model", "gpt-3.5-turbo",
                                "messages", List.of(
                                                Map.of("role", "system", "content", "You are a translator from Latvian to English."),
                                                Map.of("role", "user", "content", text)));

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

                ResponseEntity<JsonNode> response = restTemplate.exchange(
                                url,
                                HttpMethod.POST,
                                request,
                                JsonNode.class);

                String translation = response.getBody()
                                .get("choices")
                                .get(0)
                                .get("message")
                                .get("content")
                                .asText()
                                .trim();

                System.out.println("TRANSLATION ===> " + translation);
                return translation.replaceAll("[\"'()\\[\\]{}]", "");
        }
}
