package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import pendenzenliste.domain.todos.ToDoCreatedEvent;

/**
 * An achievement progress tracker that can be used to track the 'New Year, New Me!' achievement
 * <p>
 * This achievement should be unlocked when the user creates his first todo on new year.
 */
public class NewYearNewMeAchievementProgressTracker implements AchievementProgressTracker
{
  private ProgressValueObject progress = new ProgressValueObject(0, 1);

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ToDoCreatedEvent event)
  {
    if (isNewYear(event.timestamp()))
    {
      progress = progress.increment();
    }
  }

  /**
   * Checks whether the given timestamp is on new year.
   *
   * @param timestamp The timestamp.
   * @return True if the given timestamp is on new year.
   */
  private boolean isNewYear(final LocalDateTime timestamp)
  {
    return timestamp.getDayOfYear() == 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleted()
  {
    return progress.isCompleted();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AchievementValueType achievement()
  {
    return AchievementValueType.NEW_YEAR_NEW_ME;
  }
}
