package pendenzenliste.usecases;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.FetchToDoInputBoundary;
import pendenzenliste.boundary.in.FetchToDoRequest;
import pendenzenliste.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoResponse;
import pendenzenliste.boundary.out.ToDoFetchedResponse;
import pendenzenliste.domain.todos.CompletedTimestampValueObject;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to fetch a ToDo.
 */
public class FetchToDoUseCase implements FetchToDoInputBoundary
{
  private final ToDoGateway gateway;
  private final FetchToDoOutputBoundary outputBoundary;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public FetchToDoUseCase(final ToDoGateway gateway,
                          final FetchToDoOutputBoundary outputBoundary)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final FetchToDoRequest request)
  {
    executeRequest(request).applyTo(outputBoundary);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoResponse executeRequest(final FetchToDoRequest request)
  {
    try
    {
      final var id = new IdentityValueObject(request.id());

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
    return todo -> new ToDoFetchedResponse(todo.identity().value(), todo.headline().value(),
        todo.description().value(), todo.created().value(), todo.lastModified().value(),
        Optional.ofNullable(todo.completed()).map(CompletedTimestampValueObject::value)
            .orElse(null), todo.state().name(),
        todo.capabilities().stream().map(Enum::name).toList());
  }
}
