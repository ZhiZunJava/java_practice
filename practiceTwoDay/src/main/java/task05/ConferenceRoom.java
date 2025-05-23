package task05;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class ConferenceRoom {
    private String name;
    private int capacity;
    private boolean hasMultimedia;
    private Map<LocalDateTime, LocalDateTime> reservations = new HashMap<>();

    public ConferenceRoom(String name, int capacity, boolean hasMultimedia) {
        this.name = name;
        this.capacity = capacity;
        this.hasMultimedia = hasMultimedia;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasMultimedia() {
        return hasMultimedia;
    }

    public Map<LocalDateTime, LocalDateTime> getReservations() {
        return reservations;
    }

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        for (Map.Entry<LocalDateTime, LocalDateTime> entry : reservations.entrySet()) {
            LocalDateTime reservedStart = entry.getKey();
            LocalDateTime reservedEnd = entry.getValue();
            if ((start.isAfter(reservedStart) && start.isBefore(reservedEnd))
                    || (end.isAfter(reservedStart) && end.isBefore(reservedEnd))
                    || (start.isBefore(reservedStart) && end.isAfter(reservedEnd))) {
                return false;
            }
        }
        return true;
    }

    public boolean reserve(LocalDateTime start, LocalDateTime end) {
        if (isAvailable(start, end)) {
            reservations.put(start, end);
            return true;
        }
        return false;
    }
}
