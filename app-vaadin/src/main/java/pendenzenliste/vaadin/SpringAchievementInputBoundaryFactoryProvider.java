package pendenzenliste.vaadin;

import org.springframework.stereotype.Component;
import pendenzenliste.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.boundary.in.AchievementInputBoundaryFactoryProvider;
import pendenzenliste.boundary.out.AchievementOutputBoundaryFactory;

/**
 * A spring based achievement input boundary factory provider.
 */
@Component
public class SpringAchievementInputBoundaryFactoryProvider
    implements AchievementInputBoundaryFactoryProvider
{
  /**
   * {@inheritDoc}
   */
  @Override
  public AchievementInputBoundaryFactory getInstance(final AchievementOutputBoundaryFactory factory)
  {
    return AchievementInputBoundaryFactoryProvider.defaultProvider().getInstance(factory);
  }
}
