package pendenzenliste.todos.model;

import java.io.Serializable;

/**
 * A value object that can be used to represent the headline of a ToDo.
 *
 * @param value The value.
 */
public record HeadlineValueObject(String value) implements Serializable
{
  public HeadlineValueObject(final String value)
  {
    StringValidationUtil.expectNotNull(value);
    StringValidationUtil.expectNotEmpty(value);
    StringValidationUtil.expectMaxLength(value, 60);
    StringValidationUtil.expectNoLineBreaks(value);

    this.value = value.trim();
  }
}
