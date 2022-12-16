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

    final var provider = ToDoInputBoundaryFactoryProvider.defaultFactory();

    final var listViewModel = new ToDoListViewModel();
    final var editViewModel = new EditToDoViewModel();

    final var fetchPresenter = new FetchToDoListPresenter(listViewModel);
    final var createPresenter = new CreateToDoPresenter(listViewModel, editViewModel);
    final var editPresenter = new EditToDoPresenter(editViewModel);
    final var updatePresenter = new UpdateToDoPresenter(listViewModel, editViewModel);

    final var controller =
        new ToDoListController(provider.getInstance(), fetchPresenter, createPresenter,
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
                                        final ToDoListController controller)
  {
    //TODO: Figure out if there is a better way to achieve this
    listViewModel.listUpdated.addListener(
        (observable, oldValue, newValue) -> controller.loadTodos());

    listViewModel.selectedTodo.addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        controller.loadForEdit(newValue.identity());
      }
    });

    editViewModel.savedTrigger.addListener((observable, oldValue, newValue) -> {
      if (editViewModel.identity.isEmpty().get())
      {
        controller.createToDo(editViewModel.headline.get(), editViewModel.description.get());
      } else
      {
        controller.updateToDo(editViewModel.identity.get(), editViewModel.headline.get(),
            editViewModel.description.get());
      }
    });

    editViewModel.deletedTrigger.addListener(
        ((observable, oldValue, newValue) -> controller.deleteToDo(editViewModel.identity.get())));

    editViewModel.completedTrigger.addListener(
        ((observable, oldValue, newValue) -> controller.completeToDo(
            editViewModel.identity.get())));

    editViewModel.resetTrigger.addListener(
        ((observable, oldValue, newValue) -> controller.resetToDo(editViewModel.identity.get())));

    editViewModel.clearedTrigger.addListener(((observable, oldValue, newValue) -> {
      listViewModel.selectedTodo.set(null);
      editViewModel.identity.set("");
      editViewModel.headline.set("");
      editViewModel.description.set("");
      editViewModel.errorMessage.set("");
      editViewModel.deleteButtonVisible.set(false);
      editViewModel.completeButtonVisible.set(false);
      editViewModel.resetButtonVisible.set(false);
    }));
  }
}
