package pendenzenliste.domain.achievements;

import java.io.Serializable;
import java.util.Objects;

/**
 * An entity that can be used to represent the progress of an achievement.
 *
 * @param identity     The identity.
 * @param currentValue The current value.
 * @param targetValue  The target value.
 */
public record ProgressItemEntity(IdentityValueObject identity, Integer currentValue,
                                 Integer targetValue) implements Serializable
{
  /**
   * Increments the progress.
   *
   * @return The incremented progress entity.
   */
  public ProgressItemEntity inc()
  {
    if (isCompleted())
    {
      return this;
    }

    return new ProgressItemEntity(identity, currentValue + 1, targetValue);
  }

  /**
   * Decrements the progress.
   *
   * @return The decremented progress entity.
   */
  public ProgressItemEntity dec()
  {
    if (currentValue == 0)
    {
      return this;
    }

    return new ProgressItemEntity(identity, currentValue - 1, targetValue);
  }

  /**
   * Checks whether the progress is completed.
   *
   * @return True if the progress is completed, otherwise false.
   */
  public boolean isCompleted()
  {
    return Objects.equals(currentValue, targetValue);
  }
}
