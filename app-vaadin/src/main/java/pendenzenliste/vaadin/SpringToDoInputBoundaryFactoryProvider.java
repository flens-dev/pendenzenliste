package pendenzenliste.vaadin;

import org.springframework.stereotype.Component;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;

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
