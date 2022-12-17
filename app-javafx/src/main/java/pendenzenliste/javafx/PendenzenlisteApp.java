package pendenzenliste.javafx;

import io.reactivex.rxjava3.disposables.Disposable;
import javafx.application.Application;
import javafx.stage.Stage;
import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;

/**
 * An application that runs the pendenzenliste app.
 */
public class PendenzenlisteApp extends Application
{

  private Disposable subscription;

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

    final var presenterFactory = new ToDoPresenterFactory(listViewModel, editViewModel);

    final var controller =
        new ToDoController(provider.getInstance(presenterFactory));

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
  private void registerListeners(final ToDoListViewModel listViewModel,
                                 final EditToDoViewModel editViewModel,
                                 final ToDoController controller)
  {
    final ControllerEventVisitor visitor =
        new ControllerEventVisitor(controller, listViewModel, editViewModel);

    subscription = editViewModel.events().subscribe(event -> event.visit(visitor));

    //TODO: Figure out if there is a better way to achieve this
    listViewModel.selectedTodo.addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        controller.loadForEdit(newValue.identity.get());
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop() throws Exception
  {
    super.stop();

    if (subscription != null && !subscription.isDisposed())
    {
      subscription.dispose();
    }
  }
}
