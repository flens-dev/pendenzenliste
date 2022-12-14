package pendenzenliste.usecases;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.IdentityValueObject;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.DeleteToDoInputBoundary;
import pendenzenliste.ports.in.DeleteToDoRequest;
import pendenzenliste.ports.out.ToDoUpdateFailedResponse;
import pendenzenliste.ports.out.ToDoUpdatedResponse;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * A use case that can be used to delete an existing ToDo.
 */
public class DeleteToDoUseCase implements DeleteToDoInputBoundary
{
  private final ToDoGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be ud by this instance.
   */
  public DeleteToDoUseCase(final ToDoGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoResponse execute(final DeleteToDoRequest request)
  {
    try
    {
      final var identity = new IdentityValueObject(request.identity());

      return gateway.findById(identity).map(todo -> gateway.delete(identity))
          .map(mapDeletedFlagToResponse())
          .orElse(new ToDoUpdateFailedResponse("The ToDo does not exist"));
    } catch (final IllegalArgumentException e)
    {
      return new ToDoUpdateFailedResponse(e.getMessage());
    }
  }

  /**
   * Maps the deleted flag to an appropriate response.
   *
   * @return The mapping function.
   */
  private static Function<Boolean, UpdateToDoResponse> mapDeletedFlagToResponse()
  {
    return deleted -> deleted ? new ToDoUpdatedResponse() :
        new ToDoUpdateFailedResponse("Failed to delete the ToDo");
  }
}
