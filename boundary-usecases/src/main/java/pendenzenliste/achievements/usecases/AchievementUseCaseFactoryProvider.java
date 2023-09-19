package pendenzenliste.achievements.usecases;

import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactoryProvider;
import pendenzenliste.achievements.boundary.out.AchievementOutputBoundaryFactory;
import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.messaging.EventBus;

/**
 * A provider that can be used to access a {@link AchievementUseCaseFactory}.
 *
 * @deprecated Use a proper dependency injection framework instead.
 */
@Deprecated(forRemoval = true)
public class AchievementUseCaseFactoryProvider implements AchievementInputBoundaryFactoryProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementInputBoundaryFactory getInstance(final AchievementOutputBoundaryFactory factory) {
        return new AchievementUseCaseFactory(factory,
                EventBus.defaultEventBus(),
                AchievementGatewayProvider.defaultProvider().getInstance());
    }
}
