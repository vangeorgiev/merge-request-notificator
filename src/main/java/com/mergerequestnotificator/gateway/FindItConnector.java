package com.mergerequestnotificator.gateway;

import com.mergerequestnotificator.core.service.spi.TeamDataSourceCommunicator;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class FindItConnector implements TeamDataSourceCommunicator {

    private static final String USERS_FILE_PATH = "/Users/georgiev/Downloads/users.txt";
    private static final String PROJECT_NAMES_FILE_PATH = "/Users/georgiev/Downloads/projectNames.txt";

    @Override
    public List<String> getProjectNamesOwnedBy(String teamName) {
        List<String> projectNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PROJECT_NAMES_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String currentTeamName = parts[0].trim();
                    if (currentTeamName.equals(teamName)) {
                        String[] projects = parts[1].split(",");
                        projectNames.addAll(Arrays.asList(projects));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectNames;
    }

    @Override
    public List<String> getTeamMembers(String teamName) {
        List<String> teamUsers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String currentTeamName = parts[0].trim();
                    if (currentTeamName.equals(teamName)) {
                        String[] users = parts[1].split(",");
                        teamUsers.addAll(Arrays.asList(users));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teamUsers;
    }
}
