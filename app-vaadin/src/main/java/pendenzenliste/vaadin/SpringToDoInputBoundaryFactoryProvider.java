package pendenzenliste.vaadin;

import org.springframework.stereotype.Component;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;

/**
 * A spring specific todo input boundary factory provider.
 */
@Component
public class SpringToDoInputBoundaryFactoryProvider implements ToDoInputBoundaryFactoryProvider
{
  /**
   * {@inheritDoc}
   */
  @Override
  public ToDoInputBoundaryFactory getInstance(
      final ToDoOutputBoundaryFactory outputBoundaryFactory)
  {
    return ToDoInputBoundaryFactoryProvider.defaultProvider().getInstance(outputBoundaryFactory);
  }
}
