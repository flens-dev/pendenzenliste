package pendenzenliste.domain.achievements;

/**
 * An entity that can be used to represent a persistent achievement entity.
 *
 * @param identity The identity of the event.
 * @param event    The event.
 */
public record AchievementEventEntity(IdentityValueObject identity, AchievementEvent event)
{
}
