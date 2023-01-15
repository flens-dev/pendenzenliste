package pendenzenliste.gateway.inmemory;

import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.messaging.EventBus;

/**
 * A provider that can be used to access the instance of an {@link InMemoryToDoGateway}.
 */
public class InMemoryToDoGatewayProvider implements ToDoGatewayProvider
{
  private static final ToDoGateway INSTANCE = new InMemoryToDoGateway(EventBus.defaultEventBus());

  /**
   * {@inheritDoc}
   */
  @Override
  public ToDoGateway getInstance()
  {
    return INSTANCE;
  }
}
