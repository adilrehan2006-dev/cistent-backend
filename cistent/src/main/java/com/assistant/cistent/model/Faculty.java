package com.assistant.cistent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Faculty {
    @JsonProperty("NAME") private String NAME;
    @JsonProperty("DEPARTMENT") private String DEPARTMENT;
    @JsonProperty("CABIN_NO") private String CABIN_NO;
    @JsonProperty("EMP_ID") private String EMP_ID;
    @JsonProperty("EMAIL") private String EMAIL;
    @JsonProperty("FREE_TIMINGS") private List<String> FREE_TIMINGS;

    public Faculty() {}

    // Getters and Setters
    public String getNAME() { return NAME; }
    public void setNAME(String NAME) { this.NAME = NAME; }
    public String getDEPARTMENT() { return DEPARTMENT; }
    public void setDEPARTMENT(String DEPARTMENT) { this.DEPARTMENT = DEPARTMENT; }
    public String getCABIN_NO() { return CABIN_NO; }
    public void setCABIN_NO(String CABIN_NO) { this.CABIN_NO = CABIN_NO; }
    public String getEMP_ID() { return EMP_ID; }
    public void setEMP_ID(String EMP_ID) { this.EMP_ID = EMP_ID; }
    public String getEMAIL() { return EMAIL; }
    public void setEMAIL(String EMAIL) { this.EMAIL = EMAIL; }
    public List<String> getFREE_TIMINGS() { return FREE_TIMINGS; }
    public void setFREE_TIMINGS(List<String> FREE_TIMINGS) { this.FREE_TIMINGS = FREE_TIMINGS; }
}