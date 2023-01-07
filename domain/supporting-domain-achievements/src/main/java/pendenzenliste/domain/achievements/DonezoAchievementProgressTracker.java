package pendenzenliste.domain.achievements;

import pendenzenliste.domain.todos.ToDoCompletedEvent;

/**
 * An achievement progress tracker that can be used to track the 'Donezo!' achievement.
 * <p>
 * This achievement should be unlocked when the user closes his first todo.
 */
public class DonezoAchievementProgressTracker implements AchievementProgressTracker
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
    return AchievementValueType.DONEZO;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ToDoCompletedEvent event)
  {
    progress = progress.increment();
  }
}
