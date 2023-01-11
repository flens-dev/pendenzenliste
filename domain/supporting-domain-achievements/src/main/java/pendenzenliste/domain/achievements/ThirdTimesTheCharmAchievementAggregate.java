package pendenzenliste.domain.achievements;

import java.util.Collection;

import pendenzenliste.domain.todos.ToDoEvent;
import pendenzenliste.domain.todos.ToDoResetEvent;

/**
 * An achievement aggregate that can be used to track the "Third time's the charm" achievement.
 * <p>
 * This achievement should be unlocked when the user reopens a todo for the third time.
 */
public class ThirdTimesTheCharmAchievementAggregate extends AbstractAchievementAggregate
{

  /**
   * Creates a new instance.
   *
   * @param achievement   The achievement that should be represented by this instance.
   * @param events        The events that should be represented by this instance.
   * @param progressItems The progress that should be represented by this instance.
   */
  public ThirdTimesTheCharmAchievementAggregate(final AchievementEntity achievement,
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
  public void visit(final ToDoResetEvent event)
  {
    final ProgressItemEntity progressItem = progressItems().stream()
        .filter(item -> item.identity().value().equals(event.identity().value()))
        .findAny()
        .orElse(new ProgressItemEntity(new IdentityValueObject(event.identity().value()), 0, 3));

    if (progressItems().contains(progressItem))
    {
      replaceProgressItem(progressItem, progressItem.inc());
    } else
    {
      addProgressItem(progressItem.inc());
    }
  }

  /**
   * Creates a new builder instance.
   *
   * @return The builder.
   */
  public static AbstractBuilder<ThirdTimesTheCharmAchievementAggregate.Builder> builder()
  {
    return new ThirdTimesTheCharmAchievementAggregate.Builder().name(
        AchievementValueType.THIRD_TIMES_THE_CHARM);
  }

  /**
   * A builder implementation
   */
  public static class Builder
      extends AbstractBuilder<ThirdTimesTheCharmAchievementAggregate.Builder>
  {

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementAggregate build()
    {
      return new ThirdTimesTheCharmAchievementAggregate(
          new AchievementEntity(identity, name, state, unlocked),
          events, progressItems);
    }
  }
}
