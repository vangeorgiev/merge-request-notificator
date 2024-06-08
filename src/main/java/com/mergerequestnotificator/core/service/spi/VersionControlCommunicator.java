package com.mergerequestnotificator.core.service.spi;

import com.mergerequestnotificator.core.data.MergeRequest;

import java.util.List;

public interface VersionControlCommunicator {
    List<MergeRequest> getOpenMergeRequestsByProject(String projectName);

    List<MergeRequest> getOpenMergeRequestsBy(String author);
}
