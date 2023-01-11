package pendenzenliste.vaadin;

import java.util.function.Function;

import pendenzenliste.boundary.out.ToDoListResponseModel;

/**
 * A utility class that can be used to map view models.
 */
public final class ViewModelMappingUtils
{
  /**
   * Private constructor to prevent instantiation.
   */
  private ViewModelMappingUtils()
  {
  }

  /**
   * Maps the given response model to an appropriate view model.
   *
   * @return The mapping function.
   */
  public static Function<ToDoListResponseModel, ToDoListItemViewModel> mapToViewModel()
  {
    return todo -> {
      final var viewModel = new ToDoListItemViewModel();

      viewModel.identity.set(todo.identity());
      viewModel.headline.set(todo.headline());
      viewModel.created.set(todo.created());
      viewModel.lastModified.set(todo.lastModified());
      viewModel.completed.set(todo.completed());
      viewModel.state.set(todo.state());
      viewModel.deletable.set(todo.capabilities().contains("DELETE"));
      viewModel.editable.set(todo.capabilities().contains("UPDATE"));
      viewModel.completable.set(todo.capabilities().contains("COMPLETE"));
      viewModel.reopenable.set(todo.capabilities().contains("REOPEN"));

      return viewModel;
    };
  }
}
