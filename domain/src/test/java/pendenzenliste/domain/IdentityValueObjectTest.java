package pendenzenliste.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class IdentityValueObjectTest
{

  @Test
  public void mayNotBeNull()
  {
    assertThatThrownBy(() -> new IdentityValueObject(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be null");
  }

  @Test
  public void mayNotBeEmpty()
  {
    assertThatThrownBy(() -> new IdentityValueObject(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be empty");
  }
}