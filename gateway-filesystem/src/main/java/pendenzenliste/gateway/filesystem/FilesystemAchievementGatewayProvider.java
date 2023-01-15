package pendenzenliste.gateway.filesystem;

import java.util.Collection;

import pendenzenliste.achievements.model.AchievementAggregate;
import pendenzenliste.achievements.model.AchievementSeed;
import pendenzenliste.gateway.AchievementGateway;
import pendenzenliste.gateway.AchievementGatewayProvider;
import pendenzenliste.messaging.EventBus;

/**
 * A class that provides access to a {@link FilesystemAchievementGateway}.
 */
public class FilesystemAchievementGatewayProvider implements AchievementGatewayProvider
{
  private static final AchievementGateway INSTANCE;

  static
  {
    INSTANCE = new FilesystemAchievementGateway("/tmp/achievementData", EventBus.defaultEventBus());

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
