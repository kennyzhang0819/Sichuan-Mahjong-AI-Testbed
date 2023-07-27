package model.log;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Log {
    private static class LogEntry {
        LocalDateTime time;
        String message;

        LogEntry(String message) {
            this.time = LocalDateTime.now();
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    private List<LogEntry> logEntries;

    public Log() {
        logEntries = new LinkedList<>();
        this.addMessage("Instantiated log");
    }

    public void addMessage(String message) {
        logEntries.add(new LogEntry(message));
    }

    public List<String> getLastXMessages(int x) {
        return logEntries.stream()
                .skip(Math.max(0, logEntries.size() - x))
                .map(LogEntry::toString)
                .collect(Collectors.toList());
    }
}

