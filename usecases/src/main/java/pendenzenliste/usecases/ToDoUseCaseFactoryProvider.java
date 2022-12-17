package pendenzenliste.usecases;

import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.ports.in.ToDoInputBoundaryFactory;
import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.ports.out.ToDoOutputBoundaryFactory;

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
        outputBoundaryFactory);
  }
}
