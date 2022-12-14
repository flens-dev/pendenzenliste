package pendenzenliste.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DescriptionValueObjectTest
{
  @Test
  public void mayNotBeNull() {
    assertThatThrownBy(() -> new DescriptionValueObject(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be null");
  }

  @Test
  public void mayNotBeLongerThatThousandCharacters() {
    assertThatThrownBy(() -> new DescriptionValueObject("a".repeat(1001)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be longer than 1000 characters. (The given value was 1001 characters long)");
  }
}