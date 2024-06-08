package com.mergerequestnotificator.transport;

import com.mergerequestnotificator.core.data.Report;

public interface ReportSerializer {
    String serialize(Report report);
}
