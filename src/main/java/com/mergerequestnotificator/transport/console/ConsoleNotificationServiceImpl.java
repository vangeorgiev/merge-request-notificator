package com.mergerequestnotificator.transport.console;

import com.mergerequestnotificator.core.data.Report;
import com.mergerequestnotificator.core.service.spi.NotificationService;
import com.mergerequestnotificator.transport.ReportSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ConsoleNotificationServiceImpl")
public class ConsoleNotificationServiceImpl implements NotificationService {

    private final ReportSerializer reportSerializer;

    public ConsoleNotificationServiceImpl(@Qualifier("consoleReportSerializer") ReportSerializer reportSerializer) {
        this.reportSerializer = reportSerializer;
    }

    @Override
    public void send(Report report) {
        System.out.println(reportSerializer.serialize(report));
    }
}
