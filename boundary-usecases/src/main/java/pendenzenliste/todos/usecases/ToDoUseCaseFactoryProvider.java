package pendenzenliste.todos.usecases;

import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.todos.gateway.ToDoGatewayProvider;

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
        EventBus.defaultEventBus());
  }
}
