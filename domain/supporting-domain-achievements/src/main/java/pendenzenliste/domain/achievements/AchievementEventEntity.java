package pendenzenliste.domain.achievements;

import java.io.Serializable;

/**
 * An entity that can be used to represent a persistent achievement entity.
 *
 * @param identity The identity of the event.
 * @param event    The event.
 */
public record AchievementEventEntity(IdentityValueObject identity, AchievementEvent event)
    implements Serializable
{
  /**
   * Creates an instance of
   *
   * @param identity The identity.
   * @return The event.
   */
  public AchievementEventEntity withIdentity(final IdentityValueObject identity)
  {
    return new AchievementEventEntity(identity, event);
  }
}
