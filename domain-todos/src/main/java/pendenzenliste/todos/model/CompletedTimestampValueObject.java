package pendenzenliste.todos.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A value object that can be used to represent a completed timestamp.
 *
 * @param value The value.
 */
public record CompletedTimestampValueObject(LocalDateTime value) implements Serializable {
    /**
     * Creates a new instance with the current timestamp.
     *
     * @return The instance.
     */
    public static CompletedTimestampValueObject now() {
        return new CompletedTimestampValueObject(LocalDateTime.now());
    }

    /**
     * Checks whether the timestamp is before the given timestamp.
     *
     * @param timestamp The timestamp.
     * @return True if the timestamp is before the given timestamp, otherwise false.
     */
    public boolean isBefore(LocalDateTime timestamp) {
        return value.isBefore(timestamp);
    }
}
