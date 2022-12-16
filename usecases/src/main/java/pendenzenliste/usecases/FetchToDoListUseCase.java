package pendenzenliste.usecases;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.CompletedTimestampValueObject;
import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.FetchToDoListInputBoundary;
import pendenzenliste.ports.in.FetchTodoListRequest;
import pendenzenliste.ports.out.FetchToDoListResponse;
import pendenzenliste.ports.out.FetchedToDoListResponse;
import pendenzenliste.ports.out.ToDoListResponseModel;

/**
 * A use case that can be used to fetch a list of todos
 */
public class FetchToDoListUseCase implements FetchToDoListInputBoundary
{
  private final ToDoGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public FetchToDoListUseCase(final ToDoGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListResponse execute(final FetchTodoListRequest request)
  {
    return new FetchedToDoListResponse(gateway.fetchAll().map(mapToResponseModel()).toList());
  }

  /**
   * Maps the todo to a response model.
   *
   * @return The mapping function.
   */
  private static Function<ToDoEntity, ToDoListResponseModel> mapToResponseModel()
  {
    return v -> new ToDoListResponseModel(v.identity().value(), v.headline().value(),
        v.description().value(), v.created().value(), v.lastModified().value(),
        Optional.ofNullable(v.completed()).map(CompletedTimestampValueObject::value).orElse(null),
        v.state().name(), v.capabilities().stream().map(Enum::name).toList());
  }
}
