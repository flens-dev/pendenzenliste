package pendenzenliste.gateway;

/**
 * A provider that can be used to access an instance of a {@link ToDoGateway}
 */
public interface ToDoGatewayProvider
{
  /**
   * Retrieves an instance of the provider.
   *
   * @return The instance.
   */
  ToDoGateway getInstance();
}
