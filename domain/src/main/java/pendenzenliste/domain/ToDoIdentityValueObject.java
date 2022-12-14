package pendenzenliste.domain;

import java.util.UUID;

/**
 * A value object that can be used to represent a ToDo's identity.
 *
 * @param value The value.
 */
public record ToDoIdentityValueObject(String value)
{

  public ToDoIdentityValueObject
  {
    if (value == null)
    {
      throw new IllegalArgumentException("The value may not be null");
    }

    if (value.isEmpty())
    {
      throw new IllegalArgumentException("The value may not be empty");
    }
  }

  /**
   * Generates a random value.
   *
   * @return The generated value.
   */
  public static ToDoIdentityValueObject random()
  {
    return new ToDoIdentityValueObject(UUID.randomUUID().toString());
  }
}
