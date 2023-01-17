package pendenzenliste.achievements.model;

import java.util.Collection;

import pendenzenliste.todos.model.ToDoCompletedEvent;

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
  public void visit(final ToDoCompletedEvent event)
  {
    final ProgressItemEntity progressItem = progressItems().stream().findFirst()
        .orElse(new ProgressItemEntity(new IdentityValueObject("*"), 0, 1));

    addProgressItem(progressItem.inc());
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
