package pendenzenliste.achievements.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactory;

import pendenzenliste.achievements.boundary.in.FetchAchievementListInputBoundary;
import pendenzenliste.achievements.boundary.in.SubscribeAchievementsInputBoundary;
import pendenzenliste.achievements.boundary.out.AchievementOutputBoundaryFactory;
import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.messaging.EventBus;

/**
 * A factory that can be used to access the achievement specific use cases.
 */
public class AchievementUseCaseFactory implements AchievementInputBoundaryFactory
{
  private final AchievementOutputBoundaryFactory factory;
  private final EventBus eventBus;
  private final AchievementGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param factory  The factory that should be used by this instance.
   * @param eventBus The event bus that should be used by this instance.
   * @param gateway  The gateway that should be used by this instance.
   */
  public AchievementUseCaseFactory(final AchievementOutputBoundaryFactory factory,
                                   final EventBus eventBus,
                                   final AchievementGateway gateway)
  {

    this.factory = requireNonNull(factory, "The factory may not be null");
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchAchievementListInputBoundary list()
  {
    return new FetchAchievementListUseCase(factory.list(), gateway);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SubscribeAchievementsInputBoundary subscribe()
  {
    return new SubscribeAchievementsUseCase(factory.subscribe(), eventBus);
  }
}
