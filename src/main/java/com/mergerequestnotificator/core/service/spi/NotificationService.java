package com.mergerequestnotificator.core.service.spi;

import com.mergerequestnotificator.core.data.Report;

public interface NotificationService {
    void send(Report report);
}
