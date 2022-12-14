package pendenzenliste.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class HeadlineValueObjectTest
{

  @Test
  public void mayNotBeNull()
  {
    assertThatThrownBy(() -> new HeadlineValueObject(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be null");
  }

  @Test
  public void mayNotBeEmpty()
  {
    assertThatThrownBy(() -> new HeadlineValueObject(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not be empty");
  }

  @Test
  public void mayNotBeLongerThanSixtyCharacters()
  {
    assertThatThrownBy(() -> new HeadlineValueObject("a".repeat(61)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "The value may not be longer than 60 characters. (The given value was 61 characters long)");
  }

  @Test
  public void valueMayNotContainLineBreaks() {
    assertThatThrownBy(() -> new HeadlineValueObject("First\nSecond"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The value may not contain line breaks");
  }

  @Test
  public void valueShouldBeTrimmed()
  {
    assertThat(new HeadlineValueObject("   lorem ipsum   ").value())
        .isEqualTo("lorem ipsum");
  }
}