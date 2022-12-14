package pendenzenliste.domain;

import java.time.LocalDateTime;

/**
 * A value object that can be used to represent the last modified timestamp.
 *
 * @param value The value.
 */
public record LastModifiedTimestampValueObject(LocalDateTime value)
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
