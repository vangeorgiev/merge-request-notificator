package com.mergerequestnotificator.schedule.config;

import com.mergerequestnotificator.schedule.data.ScheduledJobConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public List<ScheduledJobConfig> jobConfigs() {
        List<ScheduledJobConfig> jobConfigs = new ArrayList<>();
        jobConfigs.add(new ScheduledJobConfig("Orion", "*/10 * * * * *", "C06NK9JB31U"));
        jobConfigs.add(new ScheduledJobConfig("Hydra", "0/50 * * * * ?", "C06NK9JB31U123132321"));

        return jobConfigs;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
