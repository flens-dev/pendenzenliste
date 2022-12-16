package pendenzenliste.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;

/**
 * An application that runs the pendenzenliste app.
 */
public class PendenzenlisteApp extends Application
{

  /**
   * The main entry point for the application.
   *
   * @param args The command line arguments.
   */
  public static void main(final String[] args)
  {
    launch(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(final Stage window)
  {
    window.setTitle("Pendenzenliste");
    window.show();
    window.setMaximized(true);

    final var provider = ToDoInputBoundaryFactoryProvider.defaultProvider();

    final var listViewModel = new ToDoListViewModel();
    final var editViewModel = new EditToDoViewModel();

    final var fetchPresenter = new FetchToDoListPresenter(listViewModel);
    final var createPresenter = new CreateToDoPresenter(editViewModel);
    final var editPresenter = new EditToDoPresenter(editViewModel);
    final var updatePresenter = new UpdateToDoPresenter(editViewModel);

    final var controller =
        new ToDoController(provider.getInstance(), fetchPresenter, createPresenter,
            editPresenter, updatePresenter);

    registerListeners(listViewModel, editViewModel, controller);

    final var view = new ToDoListView(listViewModel, editViewModel);

    window.setScene(view);
  }

  /**
   * Registers the listeners for the application.
   *
   * @param listViewModel The list view model.
   * @param editViewModel The edit view model.
   * @param controller    The controller.
   */
  private static void registerListeners(final ToDoListViewModel listViewModel,
                                        final EditToDoViewModel editViewModel,
                                        final ToDoController controller)
  {
    final ControllerEventVisitor visitor =
        new ControllerEventVisitor(controller, listViewModel, editViewModel);

    editViewModel.events().subscribe(event -> event.visit(visitor));

    //TODO: Figure out if there is a better way to achieve this
    listViewModel.selectedTodo.addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        controller.loadForEdit(newValue.identity());
      }
    });
  }
}
