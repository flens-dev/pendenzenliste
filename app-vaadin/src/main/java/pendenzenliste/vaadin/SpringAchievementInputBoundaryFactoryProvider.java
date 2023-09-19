package pendenzenliste.vaadin;

import org.springframework.stereotype.Component;
import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactoryProvider;
import pendenzenliste.achievements.boundary.out.AchievementOutputBoundaryFactory;

/**
 * A spring based achievement input boundary factory provider.
 *
 * @deprecated Use a proper dependency injection framework instead.
 */
@Deprecated(forRemoval = true)
@Component
public class SpringAchievementInputBoundaryFactoryProvider
        implements AchievementInputBoundaryFactoryProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementInputBoundaryFactory getInstance(final AchievementOutputBoundaryFactory factory) {
        return AchievementInputBoundaryFactoryProvider.defaultProvider().getInstance(factory);
    }
}
