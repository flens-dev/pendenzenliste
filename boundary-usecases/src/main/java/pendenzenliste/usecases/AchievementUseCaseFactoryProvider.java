package pendenzenliste.usecases;

import pendenzenliste.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.boundary.in.AchievementInputBoundaryFactoryProvider;
import pendenzenliste.boundary.out.AchievementOutputBoundaryFactory;
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
