package pendenzenliste.todos.model;

import java.io.Serializable;


/**
 * A value object that can be used to represent the description of a ToDo.
 *
 * @param value The value that should be represented by this instance.
 */
public record DescriptionValueObject(String value) implements Serializable
{
  public DescriptionValueObject
  {
    StringValidationUtil.expectNotNull(value);
    StringValidationUtil.expectMaxLength(value, 1000);
  }
}
