package com.assistant.cistent.model; // Update to match your package

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Classroom {

    @JsonProperty("BLOCK")
    private String block;

    @JsonProperty("ROOM_NO")
    private String roomNo;

    @JsonProperty("FREE_TIMINGS")
    private List<String> freeTimings;

    // Default Constructor
    public Classroom() {}

    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public List<String> getFreeTimings() { return freeTimings; }
    public void setFreeTimings(List<String> freeTimings) { this.freeTimings = freeTimings; }
}