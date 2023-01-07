package pendenzenliste.domain.achievements;

import pendenzenliste.domain.todos.ToDoCreatedEvent;

/**
 * An achievement progress tracker that can be used to track the 'The journey of a thousand miles
 * begins with one step' achievement.
 * <p>
 * This achievement should be unlocked when the user creates his first todo.
 */
public class JourneyBeginsAchievementProgressTracker implements AchievementProgressTracker
{
  private ProgressValueObject progress = new ProgressValueObject(0, 1);

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
    return AchievementValueType.JOURNEY_BEGINS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ToDoCreatedEvent event)
  {
    progress = progress.increment();
  }
}
