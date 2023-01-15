package pendenzenliste.todos.gateway;

import java.util.ServiceLoader;

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

  /**
   * Retrieves the default provider.
   *
   * @return The default provider.
   */
  static ToDoGatewayProvider defaultProvider()
  {
    return ServiceLoader.load(ToDoGatewayProvider.class)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No default provider registered"));
  }
}
