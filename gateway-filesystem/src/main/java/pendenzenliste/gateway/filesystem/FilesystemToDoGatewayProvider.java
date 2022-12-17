package pendenzenliste.gateway.filesystem;

import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.gateway.ToDoGatewayProvider;

/**
 * A provider for a {@link FilesystemToDoGateway}.
 */
public class FilesystemToDoGatewayProvider implements ToDoGatewayProvider
{
  private ToDoGateway instance = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public ToDoGateway getInstance()
  {
    if (instance == null)
    {
      // TODO: Make the path to the storage configurable
      instance = new FilesystemToDoGateway("/tmp/todoData");
    }

    return instance;
  }
}
