package pendenzenliste.achievements.model;

import java.util.Collection;
import java.util.Optional;

import pendenzenliste.todos.model.ToDoCreatedEvent;
import pendenzenliste.todos.model.ToDoEvent;

/**
 * An achievement aggregate that can be used to track the 'The journey of a thousand miles
 * begins with one step' achievement.
 * <p>
 * This achievement should be unlocked when the user creates his first todo.
 */
public class JourneyBeginsAchievementAggregate extends AbstractAchievementAggregate
{
  /**
   * Creates a new instance.
   *
   * @param achievement The achievement that should be represented by this instance.
   * @param events      The events that should be represented by this instance.
   * @param progress    The progress that should be represented by this instance.
   */
  public JourneyBeginsAchievementAggregate(final AchievementEntity achievement,
                                           final Collection<AchievementEventEntity> events,
                                           final Collection<ProgressItemEntity> progress)
  {
    super(achievement, events, progress);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trackProgress(final ToDoEvent event)
  {
    if (progressItems().isEmpty())
    {
      addProgressItem(new ProgressItemEntity(new IdentityValueObject("*"), 0, 1));
    }

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
  public void visit(final ToDoCreatedEvent event)
  {
    final Optional<ProgressItemEntity> progressItem = progressItems().stream().findFirst();

    progressItem.ifPresent(
        progressItemEntity -> replaceProgressItem(progressItemEntity, progressItemEntity.inc()));
  }

  /**
   * Creates a new builder instance.
   *
   * @return The builder.
   */
  public static AbstractBuilder<JourneyBeginsAchievementAggregate.Builder> builder()
  {
    return new JourneyBeginsAchievementAggregate.Builder().name(
        AchievementValueType.JOURNEY_BEGINS);
  }

  /**
   * A builder implementation
   */
  public static class Builder extends AbstractBuilder<JourneyBeginsAchievementAggregate.Builder>
  {

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementAggregate build()
    {
      return new JourneyBeginsAchievementAggregate(
          new AchievementEntity(identity, name, state, unlocked),
          events, progressItems);
    }
  }
}
