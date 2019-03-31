package com.example.demoprometheus;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@SpringBootApplication
public class DemoPrometheusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPrometheusApplication.class, args);
    }

    /**
     * Test metric we shall monitor using {@link MetricsManager}
     * at endpoint /actuator/prometheus
     */
    private static int metric = 0;

    @Autowired private MetricsManager metricsManager;

    @Autowired private SampleStore sampleStore;

    /**
     * On each request, metric is incremented with some random
     * value.
     * @return metric current value
     */
    @GetMapping("/some/endpoint")
    public int getMetric() {
        sampleStore.set(Timer.start(Clock.SYSTEM));
        int inc = new Random().nextInt(10);
        metric += inc;
        metricsManager.trackCounterMetrics("metric.count", inc, "sampleAttr", "Attr1");
        metricsManager.trackTimerMetrics("metric.transaction", "sampleAttr", "Attr2");
        return metric;
    }

}
