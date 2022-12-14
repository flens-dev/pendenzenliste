package pendenzenliste.usecases;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.CompletedTimestampValueObject;
import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.domain.ToDoIdentityValueObject;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.FetchToDoInputBoundary;
import pendenzenliste.ports.in.FetchToDoRequest;
import pendenzenliste.ports.out.FetchToDoFailedResponse;
import pendenzenliste.ports.out.FetchToDoResponse;
import pendenzenliste.ports.out.ToDoFetchedResponse;

/**
 * A use case that can be used to fetch a ToDo.
 */
public class FetchToDoUseCase implements FetchToDoInputBoundary
{
  private final ToDoGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public FetchToDoUseCase(final ToDoGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoResponse execute(final FetchToDoRequest request)
  {
    try
    {
      final var id = new ToDoIdentityValueObject(request.id());

      final var todo = gateway.findById(id);

      return todo.map(mapToResponse())
          .orElse(new FetchToDoFailedResponse("The ToDo does not exist"));
    } catch (final IllegalArgumentException e)
    {
      return new FetchToDoFailedResponse(e.getMessage());
    }

  }

  /**
   * Maps the given entity to a response.
   *
   * @return The mapped response.
   */
  private Function<ToDoEntity, FetchToDoResponse> mapToResponse()
  {
    return todo -> new ToDoFetchedResponse(
        todo.identity().value(),
        todo.headline().value(),
        todo.description().value(),
        todo.created().value(),
        todo.lastModified().value(),
        Optional.ofNullable(todo.completed())
            .map(CompletedTimestampValueObject::value)
            .orElse(null),
        todo.state().name());
  }
}
