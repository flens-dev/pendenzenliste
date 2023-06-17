package pendenzenliste.todos.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A value object that can be used to represent a created timestamp.
 *
 * @param value The value.
 */
public record CreatedTimestampValueObject(LocalDateTime value) implements Serializable
{
  public CreatedTimestampValueObject
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
  public static CreatedTimestampValueObject now()
  {
    return new CreatedTimestampValueObject(LocalDateTime.now());
  }
}
