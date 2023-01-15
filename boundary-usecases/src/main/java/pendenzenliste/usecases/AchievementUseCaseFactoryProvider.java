package pendenzenliste.usecases;

import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactoryProvider;
import pendenzenliste.achievements.boundary.out.AchievementOutputBoundaryFactory;
import pendenzenliste.messaging.EventBus;

/**
 * A provider that can be used to access a {@link AchievementUseCaseFactory}.
 */
public class AchievementUseCaseFactoryProvider implements AchievementInputBoundaryFactoryProvider
{
  /**
   * {@inheritDoc}
   */
  @Override
  public AchievementInputBoundaryFactory getInstance(final AchievementOutputBoundaryFactory factory)
  {
    return new AchievementUseCaseFactory(factory, EventBus.defaultEventBus());
  }
}
