package pendenzenliste.achievements.gateway.filesystem;

import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.achievements.model.AchievementAggregate;
import pendenzenliste.achievements.model.AchievementFactory;
import pendenzenliste.messaging.EventBus;

import java.util.Collection;

/**
 * A class that provides access to a {@link FilesystemAchievementGateway}.
 *
 * @deprecated Use a proper dependency injection framework instead.
 */
@Deprecated(forRemoval = true)
public class FilesystemAchievementGatewayProvider implements AchievementGatewayProvider {
    private static final AchievementGateway INSTANCE;

    static {
        INSTANCE = new FilesystemAchievementGateway("/tmp/achievementData", EventBus.defaultEventBus());

        //TODO: Find a better way to seed the data
        final Collection<AchievementAggregate> storedAchievements = INSTANCE.fetchAll().toList();

        for (final AchievementAggregate achievement : AchievementFactory.initialAchievements()) {
            if (storedAchievements.stream().noneMatch(
                    stored -> stored.aggregateRoot().name().equals(achievement.aggregateRoot().name()))) {
                INSTANCE.store(achievement);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementGateway getInstance() {
        return INSTANCE;
    }

}
