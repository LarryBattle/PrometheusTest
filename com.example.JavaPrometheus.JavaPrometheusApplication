package com.example.JavaPrometheus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JavaPrometheusApplication {

    public static void main(String[] args) {
        String prometheusUrl = "http://localhost:9090"; // Replace with your Prometheus URL
        var objectMapper = new ObjectMapper();
        var restTemplate = new RestTemplate();
        PrometheusMetricReader reader = new PrometheusMetricReader(prometheusUrl, restTemplate, objectMapper);

        try {
            var metrics = reader.fetchTopKMetrics("up", 5);
            parseMetricResponse("tokp('up', 5)", metrics);

            var query = "up{job=\"prometheus\"}";
            var metrics2 = reader.fetchMetricsByQuery(query);
            parseMetricResponse(query, metrics2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseMetricResponse(String query, JsonNode resultNode) {
        System.out.println("query => " + query);

        for (JsonNode metricNode : resultNode) {
            String instance = metricNode.path("metric").path("instance").asText();
            String job = metricNode.path("metric").path("job").asText();
            double value = metricNode.path("value").path(1).asDouble();

            System.out.println("instance: " + instance);
            System.out.println("job: " + job);
            System.out.println("value: " + value);
            System.out.println("---");
//            String pod = metricNode.path("metric").path("pod").asText();
//            String namespace = metricNode.path("metric").path("namespace").asText();
//            String uid = metricNode.path("metric").path("uid").asText();
//            double value = metricNode.path("value").path(1).asDouble();
//
//            System.out.println("Pod: " + pod);
//            System.out.println("Namespace: " + namespace);
//            System.out.println("UID: " + uid);
//            System.out.println("Value: " + value);
//            System.out.println("---");
        }
    }


}
