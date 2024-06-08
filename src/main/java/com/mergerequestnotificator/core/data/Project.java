package com.mergerequestnotificator.core.data;

import java.util.List;

public record Project(String name, List<MergeRequest> openMergeRequests) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Project Name: ").append(name).append("\n");
        sb.append("Open Merge Requests: \n");
        for (MergeRequest mergeRequest : openMergeRequests) {
            sb.append("\t").append(mergeRequest).append("\n");
        }
        return sb.toString();
    }
}
