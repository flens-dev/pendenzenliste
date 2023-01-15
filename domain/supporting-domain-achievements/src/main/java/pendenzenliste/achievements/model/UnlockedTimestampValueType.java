package pendenzenliste.achievements.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A timestamp that can be used to represent the time when an achievement has been unlocked.
 *
 * @param value The value.
 */
public record UnlockedTimestampValueType(LocalDateTime value) implements Serializable
{

  /**
   * Creates a new instance with the current timestamp.
   *
   * @return The unlocked timestamp value type.
   */
  public static UnlockedTimestampValueType now()
  {
    return new UnlockedTimestampValueType(LocalDateTime.now());
  }
}
