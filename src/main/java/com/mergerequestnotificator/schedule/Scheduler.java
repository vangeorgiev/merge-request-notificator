package com.mergerequestnotificator.schedule;

import com.mergerequestnotificator.core.service.ReportService;
import com.mergerequestnotificator.schedule.data.ScheduledJobConfig;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Scheduler {
    private final ReportService reportService;
    private final TaskScheduler taskScheduler;
    private final List<ScheduledJobConfig> jobConfigs;

    public Scheduler(ReportService reportService, List<ScheduledJobConfig> jobConfigs, ThreadPoolTaskScheduler taskScheduler) {
        this.reportService = reportService;
        this.jobConfigs = jobConfigs;
        this.taskScheduler = taskScheduler;
    }

    public void scheduleJobs() {
        jobConfigs.forEach(jobConfig -> {
            Runnable task = () -> {
                System.out.println("Running job for team: " + jobConfig.teamName());
                reportService.generateReport(jobConfig.teamName());
            };

            taskScheduler.schedule(task, new CronTrigger(jobConfig.cronExpression()));
        });
    }
}
