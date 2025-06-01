package com.example.WeatherForecast.dto;

import java.time.Instant;
import java.util.Map;

public class WeatherAuditDTO {
    private String commitId;
    private Instant commitDate;
    private String author;
    private Map<String, Object> state;

    public WeatherAuditDTO(String commitId, Instant commitDate, String author, Map<String, Object> state) {
        this.commitId = commitId;
        this.commitDate = commitDate;
        this.author = author;
        this.state = state;
    }

    // Getters and setters

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public Instant getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Instant commitDate) {
        this.commitDate = commitDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }
}

