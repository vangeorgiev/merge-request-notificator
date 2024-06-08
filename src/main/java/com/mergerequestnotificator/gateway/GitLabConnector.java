package com.mergerequestnotificator.gateway;

import com.mergerequestnotificator.core.data.MergeRequest;
import com.mergerequestnotificator.core.service.spi.VersionControlCommunicator;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitLabConnector implements VersionControlCommunicator {

    private static final String FILE_PATH = "/Users/georgiev/Downloads/mergeRequests.txt";
    private static final String DELIMITER = "\\|";

    @Override
    public List<MergeRequest> getOpenMergeRequestsByProject(String projectName) {
        List<MergeRequest> mergeRequests = readMergeRequestsFromFile();
        return mergeRequests.stream()
                .filter(mr -> mr.projectName().equals(projectName))
                .collect(Collectors.toList());
    }

    @Override
    public List<MergeRequest> getOpenMergeRequestsBy(String author) {
        List<MergeRequest> mergeRequests = readMergeRequestsFromFile();
        return mergeRequests.stream()
                .filter(mr -> mr.author().equals(author))
                .collect(Collectors.toList());
    }

    private List<MergeRequest> readMergeRequestsFromFile() {
        List<MergeRequest> mergeRequests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length == 8) {
                    String projectName = parts[0].trim();
                    String author = parts[1].trim();
                    Instant openDate = Instant.parse(parts[2].trim());
                    Instant lastUpdated = Instant.parse(parts[3].trim());
                    boolean isDraft = Boolean.parseBoolean(parts[4].trim());
                    String url = parts[5].trim();
                    String title = parts[6].trim();
                    MergeRequest mergeRequest = new MergeRequest(author, openDate, lastUpdated, isDraft, projectName, url, title);
                    mergeRequests.add(mergeRequest);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mergeRequests;
    }
}
