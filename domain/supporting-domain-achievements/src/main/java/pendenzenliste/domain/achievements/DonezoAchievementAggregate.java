package pendenzenliste.domain.achievements;

import java.util.Collection;
import java.util.Optional;

import pendenzenliste.domain.todos.ToDoCompletedEvent;
import pendenzenliste.domain.todos.ToDoEvent;

/**
 * An achievement aggregate that can be used to represent the 'Donezo!' achievement.
 * <p>
 * This achievement should be unlocked when the user closes his first todo.
 */
public class DonezoAchievementAggregate extends AbstractAchievementAggregate
{
  /**
   * Creates a new instance.
   *
   * @param achievement   The achievement that should be represented by this instance.
   * @param events        The events that should be represented by this instance.
   * @param progressItems The progress that should be represented by this instance.
   */
  public DonezoAchievementAggregate(final AchievementEntity achievement,
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
  public void visit(final ToDoCompletedEvent event)
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
  public static AbstractBuilder<Builder> builder()
  {
    return new Builder().name(AchievementValueType.DONEZO);
  }

  /**
   * A builder implementation
   */
  public static class Builder extends AbstractBuilder<Builder>
  {

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementAggregate build()
    {
      return new DonezoAchievementAggregate(new AchievementEntity(identity, name, state, unlocked),
          events, progressItems);
    }
  }
}
