package pendenzenliste.todos.boundary.in;

import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;

import java.util.ServiceLoader;

/**
 * A provider that can be used to access an instance of a {@link ToDoInputBoundaryFactory}
 *
 * @deprecated Use a proper dependency injection framework instead.
 */
@Deprecated(forRemoval = true)
public interface ToDoInputBoundaryFactoryProvider {
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
    static ToDoInputBoundaryFactoryProvider defaultProvider() {
        return ServiceLoader.load(ToDoInputBoundaryFactoryProvider.class).findFirst()
                .orElseThrow(() -> new IllegalStateException("No default provider registered"));
    }
}
