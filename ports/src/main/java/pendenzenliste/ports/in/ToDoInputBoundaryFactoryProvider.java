package pendenzenliste.ports.in;

import java.util.ServiceLoader;

/**
 * A provider that can be used to access an instance of a {@link ToDoInputBoundaryFactory}
 */
public interface ToDoInputBoundaryFactoryProvider
{
  /**
   * Retrieves the instance.
   *
   * @return The instance.
   */
  ToDoInputBoundaryFactory getInstance();

  /**
   * Retrieves the default provider.
   *
   * @return The default provider.
   */
  static ToDoInputBoundaryFactory defaultFactory()
  {
    return ServiceLoader.load(ToDoInputBoundaryFactory.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("No default factory registered"));
  }
}
