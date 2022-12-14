package pendenzenliste.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ToDoIdentityValueObjectTest
{

  @Test
  public void mayNotBeNull()
  {
    assertThatThrownBy(() -> new ToDoIdentityValueObject(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be null");
  }

  @Test
  public void mayNotBeEmpty()
  {
    assertThatThrownBy(() -> new ToDoIdentityValueObject(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be empty");
  }
}