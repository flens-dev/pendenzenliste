package pendenzenliste.achievements.model;

import java.util.Collection;

/**
 * An achievement aggregate that tracks the achievement hunter achievement.
 * <p>
 * This achievement is unlocked when the user unlocks his third achievement.
 */
public class AchievementHunterAchievementAggregate extends AbstractAchievementAggregate
{
  /**
   * Creates a new instance.
   *
   * @param achievement   The achievement that should be represented by this instance.
   * @param events        The events that should be represented by this instance.
   * @param progressItems The progress that should be represented by this instance.
   */
  public AchievementHunterAchievementAggregate(final AchievementEntity achievement,
                                               final Collection<AchievementEventEntity> events,
                                               final Collection<ProgressItemEntity> progressItems)
  {
    super(achievement, events, progressItems);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final AchievementUnlockedEvent event)
  {
    final ProgressItemEntity progressItem = progressItems().stream()
        .filter(item -> item.identity().value().equals("*"))
        .findAny()
        .orElse(new ProgressItemEntity(new IdentityValueObject("*"), 0, 3));

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
  public static AbstractBuilder<DonezoAchievementAggregate.Builder> builder()
  {
    return new Builder().name(AchievementValueType.ACHIEVEMENT_HUNTER);
  }

  /**
   * A builder implementation
   */
  public static class Builder extends AbstractBuilder<DonezoAchievementAggregate.Builder>
  {

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementAggregate build()
    {
      return new AchievementHunterAchievementAggregate(
          new AchievementEntity(identity, name, state, unlocked),
          events, progressItems);
    }
  }
}
