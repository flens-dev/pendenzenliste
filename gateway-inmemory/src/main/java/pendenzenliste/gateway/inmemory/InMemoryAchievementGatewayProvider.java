package pendenzenliste.gateway.inmemory;

import java.util.Collection;

import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.achievements.model.AchievementAggregate;
import pendenzenliste.achievements.model.AchievementSeed;
import pendenzenliste.messaging.EventBus;

/**
 * An implementation of the {@link AchievementGatewayProvider} that can be used to access an
 * in-memory gateway.
 */
public class InMemoryAchievementGatewayProvider implements AchievementGatewayProvider
{
  private static final AchievementGateway INSTANCE =
      new InMemoryAchievementGateway(EventBus.defaultEventBus());

  static
  {
    //TODO: Find a better way to seed the data
    final Collection<AchievementAggregate> storedAchievements = INSTANCE.fetchAll().toList();

    for (final AchievementAggregate achievement : AchievementSeed.seededAchievements())
    {
      if (storedAchievements.stream().noneMatch(
          stored -> stored.aggregateRoot().name().equals(achievement.aggregateRoot().name())))
      {
        INSTANCE.store(achievement);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AchievementGateway getInstance()
  {
    return INSTANCE;
  }
}
