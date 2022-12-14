package pendenzenliste.gateway.inmemory;

import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.gateway.ToDoGatewayProvider;

/**
 * A provider that can be used to access the instance of an {@link InMemoryToDoGateway}.
 */
public class InMemoryToDoGatewayProvider implements ToDoGatewayProvider
{
  private static final ToDoGateway INSTANCE = new InMemoryToDoGateway();

  /**
   * {@inheritDoc}
   */
  @Override
  public ToDoGateway getInstance()
  {
    return INSTANCE;
  }
}
