package com.mergerequestnotificator.core.service.spi;

import java.util.List;

public interface TeamDataSourceCommunicator {
    List<String> getProjectNamesOwnedBy(String teamName);
    List<String> getTeamMembers(String teamName);
}
