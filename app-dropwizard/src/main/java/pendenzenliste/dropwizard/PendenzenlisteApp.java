package pendenzenliste.dropwizard;

import java.util.Collection;
import java.util.List;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;

/**
 * The application used to run the dropwizard API.
 */
public class PendenzenlisteApp extends Application<PendenzenlisteConfig>
{
  /**
   * The main entry point of the application.
   *
   * @param args The arguments passed from the command line.
   * @throws Exception Might throw any kind of exception in case of an unexpected failure.
   */
  public static void main(final String[] args) throws Exception
  {
    new PendenzenlisteApp().run(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run(final PendenzenlisteConfig configuration, final Environment environment)
  {
    buildResources().forEach(resource -> environment.jersey().register(resource));
  }

  /**
   * Builds the resources for the application.
   *
   * @return The resources.
   */
  private Collection<Object> buildResources()
  {
    return List.of(new ToDoResource(ToDoInputBoundaryFactoryProvider.defaultProvider()));
  }
}
