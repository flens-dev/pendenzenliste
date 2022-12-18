package pendenzenliste.boundary.in;

import java.util.ServiceLoader;

import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;

/**
 * A provider that can be used to access an instance of a {@link ToDoInputBoundaryFactory}
 */
public interface ToDoInputBoundaryFactoryProvider
{
  /**
   * Retrieves the instance.
   *
   * @param outputBoundaryFactory The output boundary factory.
   * @return The instance.
   */
  ToDoInputBoundaryFactory getInstance(ToDoOutputBoundaryFactory outputBoundaryFactory);

  /**
   * Retrieves the default provider.
   *
   * @return The default provider.
   */
  static ToDoInputBoundaryFactoryProvider defaultProvider()
  {
    return ServiceLoader.load(ToDoInputBoundaryFactoryProvider.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("No default provider registered"));
  }
}
