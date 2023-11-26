package pendenzenliste.achievements.gateway;

import pendenzenliste.achievements.model.AchievementEvent;
import pendenzenliste.messaging.Subscriber;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A subscriber that can be used to track the achievement events.
 */
public class AchievementEventTrackingSubscriber implements Subscriber<AchievementEvent> {
    private final AchievementGateway gateway;

    /**
     * Creates a new instance.
     */
    public AchievementEventTrackingSubscriber() {
        this(AchievementGatewayProvider.defaultProvider().getInstance());
    }

    /**
     * Creates a new instance.
     *
     * @param gateway The gateway.
     */
    public AchievementEventTrackingSubscriber(final AchievementGateway gateway) {
        this.gateway = requireNonNull(gateway, "The gateway may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(final AchievementEvent event) {
        gateway.fetchLockedAchievements()
                .forEach(achievement -> {
                    achievement.trackProgress(event);
                    gateway.store(achievement);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Class<? extends AchievementEvent>> eventTypes() {
        return List.of(AchievementEvent.class);
    }
}
