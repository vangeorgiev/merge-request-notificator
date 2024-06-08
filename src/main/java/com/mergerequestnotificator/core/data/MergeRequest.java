package com.mergerequestnotificator.core.data;

import java.time.Instant;

public record MergeRequest(String author, Instant openDate, Instant lastUpdated, boolean isDraft, String projectName,
                           String url, String title) {
    @Override
    public String toString() {
        return "MergeRequest:" +
                "Author='" + author + '\'' +
                ", Open Date=" + openDate +
                ", Last Updated=" + lastUpdated +
                ", Is Draft=" + isDraft +
                ", URL=" + url;
    }
}
