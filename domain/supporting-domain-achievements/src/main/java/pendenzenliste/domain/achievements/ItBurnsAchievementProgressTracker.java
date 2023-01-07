package pendenzenliste.domain.achievements;

import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCompletedEvent;

/**
 * An achievement progress tracker that can be used to track the 'It burns' achievement.
 * <p>
 * This achievement should be unlocked when the user closes ten different todos in one week.
 */
public class ItBurnsAchievementProgressTracker implements AchievementProgressTracker
{
  private static final TemporalField WEEK_OF_YEAR =
      WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

  private final Map<String, ProgressValueObject> progressMap = new ConcurrentHashMap<>();
  private final Map<String, Collection<IdentityValueObject>> completedToDoMap =
      new ConcurrentHashMap<>();

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
    return AchievementValueType.IT_BURNS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ToDoCompletedEvent event)
  {
    final var identifier =
        String.format("%d/%d", event.timestamp().get(WEEK_OF_YEAR), event.timestamp().getYear());

    final var previouslyCompletedToDos = completedToDoMap.getOrDefault(identifier, new HashSet<>());

    if (!previouslyCompletedToDos.contains(event.identity()))
    {
      previouslyCompletedToDos.add(event.identity());
      completedToDoMap.put(identifier, previouslyCompletedToDos);

      final var progress = progressMap.getOrDefault(identifier, new ProgressValueObject(0, 10));

      progressMap.put(identifier, progress.increment());
    }
  }
}
