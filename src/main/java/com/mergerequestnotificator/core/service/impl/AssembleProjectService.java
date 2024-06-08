
package com.mergerequestnotificator.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mergerequestnotificator.core.data.MergeRequest;
import com.mergerequestnotificator.core.data.Project;
import com.mergerequestnotificator.core.service.ProjectService;
import com.mergerequestnotificator.core.service.spi.TeamDataSourceCommunicator;
import com.mergerequestnotificator.core.service.spi.VersionControlCommunicator;
import org.springframework.stereotype.Service;

@Service
public class AssembleProjectService implements ProjectService {

    private final TeamDataSourceCommunicator teamDataSourceCommunicator;
    private final VersionControlCommunicator versionControlCommunicator;

    public AssembleProjectService(TeamDataSourceCommunicator teamDataSourceCommunicator,
                                  VersionControlCommunicator versionControlCommunicator) {
        this.teamDataSourceCommunicator = teamDataSourceCommunicator;
        this.versionControlCommunicator = versionControlCommunicator;
    }

    @Override
    public List<Project> assembleProjectsBy(String teamName) {
        List<String> projectsOwnedByTeam = teamDataSourceCommunicator.getProjectNamesOwnedBy(teamName);

        List<Project> projects = projectsOwnedByTeam.stream()
                .map(projectName -> new Project(projectName, versionControlCommunicator.getOpenMergeRequestsByProject(projectName)))
                .collect(Collectors.toList());

        projects.addAll(getProjectsWithOpenMrsExcludingTeamOwned(teamName, projectsOwnedByTeam));

        return getNonEmptyElements(projects);
    }

    private List<Project> getProjectsWithOpenMrsExcludingTeamOwned(String teamName, List<String> projectsOwnedByTeam) {
        return getTeamMemberOpenMergeRequests(teamName).entrySet().stream()
                .filter(projectAndMrsPair -> projectIsNotOwnedByTeam(projectsOwnedByTeam, projectAndMrsPair.getKey()))
                .map(projectAndMrsPair -> new Project(projectAndMrsPair.getKey(), projectAndMrsPair.getValue()))
                .collect(Collectors.toList());
    }

    private Map<String, List<MergeRequest>> getTeamMemberOpenMergeRequests(String teamName) {
        return teamDataSourceCommunicator.getTeamMembers(teamName).stream()
                .flatMap(teamMember -> versionControlCommunicator.getOpenMergeRequestsBy(teamMember).stream())
                .collect(Collectors.groupingBy(MergeRequest::projectName, Collectors.toList()));
    }

    private boolean projectIsNotOwnedByTeam(List<String> projectsOwnedByTeam, String project) {
        return !projectsOwnedByTeam.contains(project);
    }

    private List<Project> getNonEmptyElements(List<Project> collection) {
        return collection.stream()
                .filter(project -> !project.openMergeRequests().isEmpty())
                .collect(Collectors.toList());
    }
}