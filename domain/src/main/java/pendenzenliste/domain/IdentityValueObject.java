package pendenzenliste.domain;

import java.util.UUID;

/**
 * A value object that can be used to represent a ToDo's identity.
 *
 * @param value The value.
 */
public record IdentityValueObject(String value)
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
}
