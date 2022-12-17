package pendenzenliste.domain;

import java.io.Serializable;

import static pendenzenliste.domain.StringValidationUtil.expectMaxLength;
import static pendenzenliste.domain.StringValidationUtil.expectNoLineBreaks;
import static pendenzenliste.domain.StringValidationUtil.expectNotEmpty;
import static pendenzenliste.domain.StringValidationUtil.expectNotNull;

/**
 * A value object that can be used to represent the headline of a ToDo.
 *
 * @param value The value.
 */
public record HeadlineValueObject(String value) implements Serializable
{
  public HeadlineValueObject(final String value)
  {
    expectNotNull(value);
    expectNotEmpty(value);
    expectMaxLength(value, 60);
    expectNoLineBreaks(value);

    this.value = value.trim();
  }
}
