package com.mergerequestnotificator.transport.console;

import com.mergerequestnotificator.core.data.Report;
import com.mergerequestnotificator.transport.ReportSerializer;
import org.springframework.stereotype.Service;

@Service
public class ConsoleReportSerializer implements ReportSerializer {
    @Override
    public String serialize(Report report) {
        return report.toString();
    }
}
