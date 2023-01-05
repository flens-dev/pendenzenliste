package pendenzenliste.usecases;

import java.util.Optional;
import java.util.function.Function;

import pendenzenliste.boundary.out.ToDoListResponseModel;
import pendenzenliste.domain.todos.CompletedTimestampValueObject;
import pendenzenliste.domain.todos.ToDoEntity;

/**
 * A utility class that can be used to map response models.
 */
public final class ResponseModelMappingUtils
{
  /**
   * Private constructor to prevent instantiation.
   */
  private ResponseModelMappingUtils()
  {
  }

  /**
   * Maps the todo to a response model.
   *
   * @return The mapping function.
   */
  public static Function<ToDoEntity, ToDoListResponseModel> mapToResponseModel()
  {
    return v -> new ToDoListResponseModel(v.identity().value(), v.headline().value(),
        v.description().value(), v.created().value(), v.lastModified().value(),
        Optional.ofNullable(v.completed()).map(CompletedTimestampValueObject::value).orElse(null),
        v.state().name(), v.capabilities().stream().map(Enum::name).toList());
  }
}
