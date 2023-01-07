package pendenzenliste.domain.achievements;

/**
 * A value object that can be used to represent the progress of an achievement.
 *
 * @param value  The current value.
 * @param target The target value.
 */
public record ProgressValueObject(int value, int target)
{
  /**
   * Increments the value.
   *
   * @return The progress.
   */
  public ProgressValueObject increment()
  {
    if (isCompleted())
    {
      return this;
    }

    return new ProgressValueObject(value + 1, target);
  }

  /**
   * Checks whether the progress has been completed.
   *
   * @return True if the progress has been completed, otherwise false.
   */
  public boolean isCompleted()
  {
    return value == target;
  }
}
