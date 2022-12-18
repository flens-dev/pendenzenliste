package pendenzenliste.cli;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactoryProvider;

/**
 * An application that can be used to run access the pendenzenliste from the command line.
 */
public class PendenzenlisteApp
{
  /**
   * The main entry point for the application.
   *
   * @param args The command line arguments.
   * @throws Throwable Might throw any kind of exception in case of an unexpected failure.
   */
  public static void main(final String[] args) throws Throwable
  {
    final var options = new Options();

    options.addOption("list", false, "Lists the todos");
    options.addOption("fetch", true, "Fetches a todo");
    options.addOption("complete", true, "Completes a todo");
    options.addOption("reset", true, "Resets a todo");
    options.addOption("delete", true, "Deletes a todo");

    final var parser = new DefaultParser();
    final var cmd = parser.parse(options, args);

    final var provider = ToDoInputBoundaryFactoryProvider.defaultProvider();
    final var factory = provider.getInstance(new ToDoPresenterFactory());

    final var controller = new ToDoController(factory);

    controller.handle(cmd, options);
  }
}
