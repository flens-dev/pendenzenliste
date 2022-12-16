package pendenzenliste.javafx;

import static java.util.Objects.requireNonNull;

import pendenzenliste.ports.in.CompleteToDoRequest;
import pendenzenliste.ports.in.CreateToDoRequest;
import pendenzenliste.ports.in.DeleteToDoRequest;
import pendenzenliste.ports.in.FetchToDoRequest;
import pendenzenliste.ports.in.FetchTodoListRequest;
import pendenzenliste.ports.in.ResetToDoRequest;
import pendenzenliste.ports.in.ToDoInputBoundaryFactory;
import pendenzenliste.ports.in.UpdateToDoRequest;

/**
 * A controller that can be used in the context the application.
 */
public class ToDoController
{
  private final ToDoInputBoundaryFactory factory;
  private final FetchToDoListPresenter fetchListPresenter;
  private final CreateToDoPresenter createPresenter;
  private final EditToDoPresenter editPresenter;
  private final UpdateToDoPresenter updatePresenter;

  /**
   * Creates a new instance.
   *
   * @param factory            The factory that should be used by this instance.
   * @param fetchListPresenter The fetch list presenter.
   * @param createPresenter    The create presenter.
   * @param editPresenter      The edit presenter.
   * @param updatePresenter    The update presenter.
   */
  public ToDoController(final ToDoInputBoundaryFactory factory,
                        final FetchToDoListPresenter fetchListPresenter,
                        final CreateToDoPresenter createPresenter,
                        final EditToDoPresenter editPresenter,
                        final UpdateToDoPresenter updatePresenter)
  {
    this.factory = requireNonNull(factory, "The factory may not be null");
    this.fetchListPresenter =
        requireNonNull(fetchListPresenter, "The fetch list presenter may not be null");
    this.createPresenter = requireNonNull(createPresenter, "The create presenter may not be null");
    this.editPresenter = requireNonNull(editPresenter, "The edit presenter may not be null");
    this.updatePresenter = requireNonNull(updatePresenter, "The update presenter may not be null");
  }

  /**
   * Loads the todos that should be displayed.
   */
  public void loadTodos()
  {
    final var request = new FetchTodoListRequest();

    factory.list().execute(request, fetchListPresenter);
  }

  /**
   * Loads the displayed todo for editing.
   *
   * @param identity The identity of the todo that should be loaded.
   */
  public void loadForEdit(final String identity)
  {
    final var request = new FetchToDoRequest(identity);

    factory.fetch().execute(request, editPresenter);
  }

  /**
   * Updates the specified todo.
   *
   * @param identity    The identity.
   * @param headline    The headline.
   * @param description The description.
   */
  public void update(final String identity, final String headline, final String description)
  {
    final var request = new UpdateToDoRequest(identity, headline, description);

    factory.update().execute(request, updatePresenter);
  }

  /**
   * Creates a new todo.
   *
   * @param headline    The headline.
   * @param description The description.
   */
  public void create(final String headline, final String description)
  {
    final var request = new CreateToDoRequest(headline, description);

    factory.create().execute(request, createPresenter);
  }

  /**
   * Deletes a todo.
   *
   * @param identity The identity.
   */
  public void delete(final String identity)
  {
    final var request = new DeleteToDoRequest(identity);

    factory.delete().execute(request, updatePresenter);
  }

  /**
   * Completes a todo.
   *
   * @param identity The identity.
   */
  public void complete(final String identity)
  {
    final var request = new CompleteToDoRequest(identity);

    factory.complete().execute(request, updatePresenter);
  }

  /**
   * Resets a todo.
   *
   * @param identity The identity.
   */
  public void reset(final String identity)
  {
    final var request = new ResetToDoRequest(identity);

    factory.reset().execute(request, updatePresenter);
  }
}
