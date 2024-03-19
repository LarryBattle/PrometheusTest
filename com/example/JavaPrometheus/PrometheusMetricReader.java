// Prometheus API Docs: https://prometheus.io/docs/prometheus/latest/querying/api/
package com.example.JavaPrometheus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class PrometheusMetricReader {
    private final RestTemplate restTemplate;
    private final String prometheusUrl;
    private final ObjectMapper objectMapper;

    public PrometheusMetricReader(String prometheusUrl, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.prometheusUrl = prometheusUrl;
        this.objectMapper = objectMapper;
    }

    public JsonNode fetchTopKMetrics(String metricName, int topK) throws JsonProcessingException {
        return fetchMetricsByQuery("topk(" + topK + "," + metricName + ")");
    }

    public JsonNode fetchMetricsByQuery(String query) throws JsonProcessingException {
        String url = UriComponentsBuilder.fromHttpUrl(prometheusUrl)
                .path("/api/v1/query")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("query", query);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        String response;
        try {
            response = restTemplate.postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
            System.out.println("Failed to query " + query);
            e.printStackTrace();
            return null;
        }

        JsonNode rootNode = objectMapper.readTree(response);
        return rootNode.path("data").path("result");
    }
}
