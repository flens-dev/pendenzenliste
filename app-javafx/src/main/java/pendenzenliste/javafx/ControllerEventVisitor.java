package pendenzenliste.javafx;

import static java.util.Objects.requireNonNull;

/**
 * A {@link ToDoEventVisitor} that can be used to synchronize the events with a controller.
 */
public class ControllerEventVisitor implements ToDoEventVisitor
{
  private final ToDoController controller;
  private final ToDoListViewModel listViewModel;
  private final EditToDoViewModel editViewModel;

  /**
   * Creates a new instance.
   *
   * @param controller    The controller.
   * @param listViewModel The list view model.
   * @param editViewModel The edit view model.
   */
  public ControllerEventVisitor(final ToDoController controller,
                                final ToDoListViewModel listViewModel,
                                final EditToDoViewModel editViewModel)
  {
    this.controller = requireNonNull(controller, "The controller may not be null");
    this.listViewModel = requireNonNull(listViewModel, "The list view model may not be null");
    this.editViewModel = requireNonNull(editViewModel, "The edit view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ClearEditorRequestedEvent event)
  {
    //TODO: The visitor should probably not have access to the view models
    listViewModel.selectedTodo.set(null);
    editViewModel.identity.set("");
    editViewModel.headline.set("");
    editViewModel.description.set("");
    editViewModel.errorMessage.set("");
    editViewModel.deleteButtonVisible.set(false);
    editViewModel.completeButtonVisible.set(false);
    editViewModel.resetButtonVisible.set(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final DeleteRequestedEvent event)
  {
    controller.delete(event.identity());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final CompleteRequestedEvent event)
  {
    controller.complete(event.identity());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ResetRequestedEvent event)
  {
    controller.reset(event.identity());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final SaveRequestedEvent event)
  {
    if (event.identity() == null || event.identity().isEmpty())
    {
      controller.create(event.headline(), event.description());
    } else
    {
      controller.update(event.identity(), event.headline(), event.description());
    }
  }
}
