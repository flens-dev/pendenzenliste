package pendenzenliste.domain.achievements;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ProgressItemEntityTest
{

  @Test
  public void inc_notCompleted()
  {
    final var progress =
        new ProgressItemEntity(new IdentityValueObject("*"), 0, 2).inc();

    final var assertions = new SoftAssertions();

    assertions.assertThat(progress.identity()).isEqualTo(new IdentityValueObject("*"));
    assertions.assertThat(progress.currentValue()).isEqualTo(1);
    assertions.assertThat(progress.targetValue()).isEqualTo(2);
    assertions.assertThat(progress.isCompleted()).isFalse();

    assertions.assertAll();
  }

  @Test
  public void inc_completed()
  {
    final var progress =
        new ProgressItemEntity(new IdentityValueObject("*"), 1, 2).inc();

    final var assertions = new SoftAssertions();

    assertions.assertThat(progress.identity()).isEqualTo(new IdentityValueObject("*"));
    assertions.assertThat(progress.currentValue()).isEqualTo(2);
    assertions.assertThat(progress.targetValue()).isEqualTo(2);
    assertions.assertThat(progress.isCompleted()).isTrue();

    assertions.assertAll();
  }

  @Test
  public void inc_alreadyCompleted()
  {
    final var progress =
        new ProgressItemEntity(new IdentityValueObject("*"), 2, 2).inc();

    final var assertions = new SoftAssertions();

    assertions.assertThat(progress.identity()).isEqualTo(new IdentityValueObject("*"));
    assertions.assertThat(progress.currentValue()).isEqualTo(2);
    assertions.assertThat(progress.targetValue()).isEqualTo(2);
    assertions.assertThat(progress.isCompleted()).isTrue();

    assertions.assertAll();
  }

  @Test
  public void dec_toZero()
  {
    final var progress =
        new ProgressItemEntity(new IdentityValueObject("*"), 1, 2).dec();

    final var assertions = new SoftAssertions();

    assertions.assertThat(progress.identity()).isEqualTo(new IdentityValueObject("*"));
    assertions.assertThat(progress.currentValue()).isEqualTo(0);
    assertions.assertThat(progress.targetValue()).isEqualTo(2);
    assertions.assertThat(progress.isCompleted()).isFalse();

    assertions.assertAll();
  }

  @Test
  public void dec_fromZero()
  {
    final var progress =
        new ProgressItemEntity(new IdentityValueObject("*"), 0, 2).dec();

    final var assertions = new SoftAssertions();

    assertions.assertThat(progress.identity()).isEqualTo(new IdentityValueObject("*"));
    assertions.assertThat(progress.currentValue()).isEqualTo(0);
    assertions.assertThat(progress.targetValue()).isEqualTo(2);
    assertions.assertThat(progress.isCompleted()).isFalse();

    assertions.assertAll();
  }
}