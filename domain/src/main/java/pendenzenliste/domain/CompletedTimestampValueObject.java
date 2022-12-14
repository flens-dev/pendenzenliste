package pendenzenliste.domain;

import java.time.LocalDateTime;

/**
 * A value object that can be used to represent a completed timestamp.
 *
 * @param value The value.
 */
public record CompletedTimestampValueObject(LocalDateTime value)
{
  /**
   * Creates a new instance with the current timestamp.
   *
   * @return The instance.
   */
  public static CompletedTimestampValueObject now()
  {
    return new CompletedTimestampValueObject(LocalDateTime.now());
  }
}
