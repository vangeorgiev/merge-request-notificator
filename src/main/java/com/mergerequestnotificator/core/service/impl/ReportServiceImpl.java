package com.mergerequestnotificator.core.service.impl;

import java.util.List;

import com.mergerequestnotificator.core.data.Report;
import com.mergerequestnotificator.core.service.ProjectService;
import com.mergerequestnotificator.core.service.ReportService;
import com.mergerequestnotificator.core.service.spi.NotificationService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    private static final String NOTIFICATION_SERVICE_IMPLEMENTATION_POSTFIX = "NotificationServiceImpl";
    private final BeanFactory beanFactory;
    private final ProjectService projectService;

    @Value("${communication.channels}")
    private List<String> communicationChannels;

    public ReportServiceImpl(ProjectService assembleProjectService, BeanFactory beanFactory) {
        this.projectService = assembleProjectService;
        this.beanFactory = beanFactory;
    }

    @Override
    public void generateReport(String teamName) {
        Report generatedReport = new Report(projectService.assembleProjectsBy(teamName));

        communicationChannels.stream()
                .map(this::getNotificationServiceBy)
                .forEach(notificationService -> notificationService.send(generatedReport));
    }

    private NotificationService getNotificationServiceBy(String communicationChannel) {
        return beanFactory.getBean(communicationChannel + NOTIFICATION_SERVICE_IMPLEMENTATION_POSTFIX, NotificationService.class);
    }
}