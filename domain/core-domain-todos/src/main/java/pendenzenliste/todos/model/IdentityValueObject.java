package pendenzenliste.todos.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * A value object that can be used to represent a ToDo's identity.
 *
 * @param value The value.
 */
public record IdentityValueObject(String value) implements Serializable
{

  public IdentityValueObject
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
  public static IdentityValueObject random()
  {
    return new IdentityValueObject(UUID.randomUUID().toString());
  }

  /**
   * Creates a new instance of the given value.
   *
   * @param value The value.
   * @return The identity.
   */
  public static IdentityValueObject of(final String value)
  {
    return new IdentityValueObject(value);
  }

  /**
   * Creates a new instance of the given value.
   *
   * @param value The value.
   * @return The identity.
   */
  public static IdentityValueObject of(final int value)
  {
    return of(String.valueOf(value));
  }
}
