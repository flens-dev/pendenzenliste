package pendenzenliste.usecases;

import pendenzenliste.domain.ToDoEventPublisher;
import pendenzenliste.domain.ToDoEventSubscriptionTopic;
import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;

/**
 * A {@link ToDoInputBoundaryFactoryProvider} that provides access to a {@link ToDoUseCaseFactory}
 */
public class ToDoUseCaseFactoryProvider implements ToDoInputBoundaryFactoryProvider
{
  /**
   * {@inheritDoc}
   */
  @Override
  public ToDoInputBoundaryFactory getInstance(final ToDoOutputBoundaryFactory outputBoundaryFactory)
  {
    return new ToDoUseCaseFactory(ToDoGatewayProvider.defaultProvider(),
        outputBoundaryFactory,
        ToDoEventPublisher.defaultPublisher(),
        ToDoEventSubscriptionTopic.defaultSubscriptionTopic());
  }
}
