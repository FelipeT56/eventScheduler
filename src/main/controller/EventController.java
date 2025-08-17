package controller;

import model.Event;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventController {
    private List<Event> events;

    public EventController() {
        events = new ArrayList<>();
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
    }

    // Save events to a text file
    public void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt"))) {
            for (Event e : events) {
                writer.write(e.getTitle() + ";" + e.getLocation() + ";" +
                             e.getStartTime() + ";" + e.getEndTime() + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Load events from a text file
    public void loadEventsFromFile() {
        File file = new File("events.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Event e = new Event(parts[0],
                        LocalDateTime.parse(parts[2]),
                        LocalDateTime.parse(parts[3]),
                        parts[1]);
                events.add(e);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
