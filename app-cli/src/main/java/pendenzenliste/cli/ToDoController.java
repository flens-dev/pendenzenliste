package pendenzenliste.cli;

import static java.util.Objects.requireNonNull;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import pendenzenliste.boundary.in.CompleteToDoRequest;
import pendenzenliste.boundary.in.DeleteToDoRequest;
import pendenzenliste.boundary.in.FetchToDoRequest;
import pendenzenliste.boundary.in.FetchTodoListRequest;
import pendenzenliste.boundary.in.ResetToDoRequest;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;

public class ToDoController
{
  private final ToDoInputBoundaryFactory factory;

  /**
   * Creates a new instance.
   *
   * @param factory The factory that should be used by this instance.
   */
  public ToDoController(final ToDoInputBoundaryFactory factory)
  {
    this.factory = requireNonNull(factory, "The factory may not be null");
  }

  /**
   * Handles the given command.
   *
   * @param cmd     The command.
   * @param options The options.
   */
  public void handle(final CommandLine cmd, final Options options)
  {
    if (cmd.hasOption("list"))
    {
      factory.list().execute(new FetchTodoListRequest());
    } else if (cmd.hasOption("fetch"))
    {
      factory.fetch().execute(new FetchToDoRequest(cmd.getOptionValue("fetch")));
    } else if (cmd.hasOption("reset"))
    {
      factory.reopen().execute(new ResetToDoRequest(cmd.getOptionValue("reset")));
    } else if (cmd.hasOption("complete"))
    {
      factory.complete().execute(new CompleteToDoRequest(cmd.getOptionValue("complete")));
    } else if (cmd.hasOption("delete"))
    {
      factory.delete().execute(new DeleteToDoRequest(cmd.getOptionValue("delete")));
    } else
    {
      final var formatter = new HelpFormatter();

      formatter.printHelp("app-cli", options);
    }
  }
}
