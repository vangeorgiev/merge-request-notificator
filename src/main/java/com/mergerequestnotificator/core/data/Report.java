package com.mergerequestnotificator.core.data;

import java.util.List;

public record Report(List<Project> projects) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Daily report:\n");
        for (Project project : projects) {
            sb.append(project).append("\n\n");
        }
        return sb.toString();
    }
}
