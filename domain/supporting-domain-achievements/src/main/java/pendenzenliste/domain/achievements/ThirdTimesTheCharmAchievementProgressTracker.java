package pendenzenliste.domain.achievements;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoResetEvent;

/**
 * An achievement progress tracker that can be used to track the "Third time's the charm" achievement.
 * <p>
 * This achievement should be unlocked when the user reopens a todo for the third time.
 */
public class ThirdTimesTheCharmAchievementProgressTracker implements AchievementProgressTracker
{
  private final Map<IdentityValueObject, ProgressValueObject> progressMap =
      new ConcurrentHashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ToDoResetEvent event)
  {
    final var progress = progressMap.getOrDefault(event.identity(), new ProgressValueObject(0, 3));

    progressMap.put(event.identity(), progress.increment());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleted()
  {
    for (final ProgressValueObject progress : progressMap.values())
    {
      if (progress.isCompleted())
      {
        return true;
      }
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AchievementValueType achievement()
  {
    return AchievementValueType.THIRD_TIMES_THE_CHARM;
  }
}
