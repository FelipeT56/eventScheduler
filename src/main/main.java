package app;

import model.Event;
import controller.EventController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static EventController controller = new EventController();

    public static void main(String[] args) {
        controller.loadEventsFromFile();

        JFrame frame = new JFrame("Events For You");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        DefaultListModel<Event> listModel = new DefaultListModel<>();
        JList<Event> eventList = new JList<>(listModel);
        for (Event e : controller.getEvents()) {
            listModel.addElement(e);
        }

        JTextField titleField = new JTextField(15);
        JTextField locationField = new JTextField(15);
        JTextField startField = new JTextField(15);
        startField.setToolTipText("yyyy-MM-ddTHH:mm");
        JTextField endField = new JTextField(15);
        endField.setToolTipText("yyyy-MM-ddTHH:mm");

        JButton addButton = new JButton("Add Event");
        JButton removeButton = new JButton("Remove Selected");

        addButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String location = locationField.getText();
                LocalDateTime start = LocalDateTime.parse(startField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime end = LocalDateTime.parse(endField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Event event = new Event(title, start, end, location);
                controller.addEvent(event);
                listModel.addElement(event);

                titleField.setText("");
                locationField.setText("");
                startField.setText("");
                endField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Check date format yyyy-MM-ddTHH:mm.");
            }
        });

        removeButton.addActionListener(e -> {
            Event selected = eventList.getSelectedValue();
            if (selected != null) {
                controller.removeEvent(selected);
                listModel.removeElement(selected);
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Location:"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Start Time:"));
        inputPanel.add(startField);
        inputPanel.add(new JLabel("End Time:"));
        inputPanel.add(endField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(eventList), BorderLayout.CENTER);

        frame.setVisible(true);

        // Save events on close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                controller.saveEventsToFile();
            }
        });
    }
}
