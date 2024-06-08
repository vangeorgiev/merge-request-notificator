package com.mergerequestnotificator.core.service;

import com.mergerequestnotificator.core.data.Project;

import java.util.List;

public interface ProjectService {
    List<Project> assembleProjectsBy(String teamName);

}
