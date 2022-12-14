package pendenzenliste.domain;

import static pendenzenliste.domain.StringValidationUtil.expectMaxLength;
import static pendenzenliste.domain.StringValidationUtil.expectNotNull;


/**
 * A value object that can be used to represent the description of a ToDo.
 *
 * @param value The value that should be represented by this instance.
 */
public record DescriptionValueObject(String value)
{
  public DescriptionValueObject
  {
    expectNotNull(value);
    expectMaxLength(value, 1000);
  }
}
