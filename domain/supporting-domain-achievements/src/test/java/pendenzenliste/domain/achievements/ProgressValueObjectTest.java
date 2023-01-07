package pendenzenliste.domain.achievements;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ProgressValueObjectTest
{
  @Test
  public void isCompleted_incomplete()
  {
    final var progress = new ProgressValueObject(1, 10);

    assertThat(progress.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_complete()
  {
    final var progress = new ProgressValueObject(10, 10);

    assertThat(progress.isCompleted()).isTrue();
  }

  @Test
  public void increment()
  {
    final var progress = new ProgressValueObject(1, 10);

    final var update = progress.increment();

    final var assertions = new SoftAssertions();

    assertions.assertThat(update.value()).isEqualTo(2);
    assertions.assertThat(update.target()).isEqualTo(10);
    assertions.assertThat(update.isCompleted()).isFalse();

    assertions.assertAll();
  }

  @Test
  public void increment_overTarget()
  {
    final var progress = new ProgressValueObject(1, 1);

    final var update = progress.increment();

    final var assertions = new SoftAssertions();

    assertions.assertThat(update.value()).isEqualTo(1);
    assertions.assertThat(update.target()).isEqualTo(1);
    assertions.assertThat(update.isCompleted()).isTrue();

    assertions.assertAll();
  }
}