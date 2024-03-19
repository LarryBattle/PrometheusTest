# PrometheusTest
SpringBoot Prometheus 2.5 Simple RestTemplate Demo

Prometheus API Docs: https://prometheus.io/docs/prometheus/latest/querying/api/

## Output

```
query => tokp('up', 5)
instance: localhost:9090
job: prometheus
value: 1.0
---
query => up{job="prometheus"}
instance: localhost:9090
job: prometheus
value: 1.0
---
```
