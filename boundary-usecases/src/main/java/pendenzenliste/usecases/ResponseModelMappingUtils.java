package pendenzenliste.usecases;

import java.util.Optional;
import java.util.function.Function;

import pendenzenliste.boundary.out.ToDoListResponseModel;
import pendenzenliste.domain.todos.CompletedTimestampValueObject;
import pendenzenliste.domain.todos.ToDoAggregate;

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
  public static Function<ToDoAggregate, ToDoListResponseModel> mapToResponseModel()
  {
    return v -> new ToDoListResponseModel(v.aggregateRoot().identity().value(),
        v.aggregateRoot().headline().value(),
        v.aggregateRoot().description().value(), v.aggregateRoot().created().value(),
        v.aggregateRoot().lastModified().value(),
        Optional.ofNullable(v.aggregateRoot().completed()).map(CompletedTimestampValueObject::value)
            .orElse(null),
        v.aggregateRoot().state().name(),
        v.capabilities().stream().map(Enum::name).toList());
  }
}
