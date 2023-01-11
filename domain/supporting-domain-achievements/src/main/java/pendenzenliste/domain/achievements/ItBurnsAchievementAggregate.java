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
import pendenzenliste.domain.todos.ToDoEvent;

/**
 * An achievement aggregate that can be used to track the 'It burns' achievement.
 * <p>
 * This achievement should be unlocked when the user closes ten different todos in one week.
 */
public class ItBurnsAchievementAggregate extends AbstractAchievementAggregate
{

  private static final TemporalField WEEK_OF_YEAR =
      WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

  private final Map<String, Collection<IdentityValueObject>> completedToDoMap =
      new ConcurrentHashMap<>();


  /**
   * Creates a new instance.
   *
   * @param achievement   The achievement that should be represented by this instance.
   * @param events        The events that should be represented by this instance.
   * @param progressItems The progress that should be represented by this instance.
   */
  public ItBurnsAchievementAggregate(final AchievementEntity achievement,
                                     final Collection<AchievementEventEntity> events,
                                     final Collection<ProgressItemEntity> progressItems)
  {
    super(achievement, events, progressItems);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trackProgress(final ToDoEvent event)
  {
    event.visit(this);

    unlockIfCompleted();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trackProgress(final AchievementEvent event)
  {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ToDoCompletedEvent event)
  {
    final var identifier =
        String.format("%d/%d",
            event.timestamp().get(WEEK_OF_YEAR),
            event.timestamp().getYear());

    final var previouslyCompletedToDos =
        completedToDoMap.getOrDefault(identifier, new HashSet<>());

    if (!previouslyCompletedToDos.contains(event.identity()))
    {
      final ProgressItemEntity progressItem =
          progressItems().stream().filter(item -> item.identity().value().equals(identifier))
              .findFirst().orElse(new ProgressItemEntity(
                  new pendenzenliste.domain.achievements.IdentityValueObject(identifier),
                  0,
                  10));

      if (progressItems().contains(progressItem))
      {
        replaceProgressItem(progressItem, progressItem.inc());
      } else
      {
        addProgressItem(progressItem.inc());
      }

      previouslyCompletedToDos.add(event.identity());

      completedToDoMap.put(identifier, previouslyCompletedToDos);
    }
  }

  /**
   * Creates a new builder instance.
   *
   * @return The builder.
   */
  public static AbstractBuilder<Builder> builder()
  {
    return new ItBurnsAchievementAggregate.Builder().name(AchievementValueType.IT_BURNS);
  }

  /**
   * A builder implementation
   */
  public static class Builder extends AbstractBuilder<ItBurnsAchievementAggregate.Builder>
  {

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementAggregate build()
    {
      return new ItBurnsAchievementAggregate(
          new AchievementEntity(identity, name, state, unlocked),
          events, progressItems);
    }
  }
}
