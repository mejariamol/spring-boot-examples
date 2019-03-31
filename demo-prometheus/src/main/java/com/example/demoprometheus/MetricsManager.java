package com.example.demoprometheus;

public interface MetricsManager {

    /**
     * To monitor counts & time
     */
    void trackTimerMetrics(String metricName, String... tags);

    /**
     * To monitor incremental count
     */
    void trackCounterMetrics(String metricName, double increment, String... tags);
}
