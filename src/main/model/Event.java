package model;

import java.time.LocalDateTime;

public class Event {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;

    public Event(String title, LocalDateTime startTime, LocalDateTime endTime, String location) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public String toString() {
        return title + " | " + location + " | " + startTime + " to " + endTime;
    }
}
