package com.assistant.cistent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
    private String id; // Used to track the Firebase Document ID for editing/deleting

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("VENUE")
    private String venue;

    @JsonProperty("DESCRIPTION")
    private String description;

    @JsonProperty("TIMINGS")
    private String timings;

    @JsonProperty("GUESTS")
    private String guests;

    @JsonProperty("EMP_ID")
    private String empId;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTimings() { return timings; }
    public void setTimings(String timings) { this.timings = timings; }

    public String getGuests() { return guests; }
    public void setGuests(String guests) { this.guests = guests; }

    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }
}