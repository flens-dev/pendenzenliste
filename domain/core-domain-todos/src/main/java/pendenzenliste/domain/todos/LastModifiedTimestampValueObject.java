package pendenzenliste.domain.todos;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A value object that can be used to represent the last modified timestamp.
 *
 * @param value The value.
 */
public record LastModifiedTimestampValueObject(LocalDateTime value) implements Serializable
{
  public LastModifiedTimestampValueObject
  {
    if (value == null)
    {
      throw new IllegalArgumentException("The value may not be null");
    }
  }

  /**
   * Creates a new instance with the current timestamp.
   *
   * @return The instance.
   */
  public static LastModifiedTimestampValueObject now()
  {
    return new LastModifiedTimestampValueObject(LocalDateTime.now());
  }
}
