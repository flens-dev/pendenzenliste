package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.ToDoCapability;
import pendenzenliste.domain.ToDoIdentityValueObject;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.ResetToDoInputBoundary;
import pendenzenliste.ports.in.ResetToDoRequest;
import pendenzenliste.ports.out.ToDoUpdateFailedResponse;
import pendenzenliste.ports.out.ToDoUpdatedResponse;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * A use case that can be used to reset a closed ToDo.
 */
public class ResetToDoUseCase implements ResetToDoInputBoundary
{
  private final ToDoGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public ResetToDoUseCase(final ToDoGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoResponse execute(final ResetToDoRequest request)
  {
    try
    {
      final var identity =
          new ToDoIdentityValueObject(request.identity());

      final var todo = gateway.findById(identity);

      if (todo.isEmpty())
      {
        return new ToDoUpdateFailedResponse("The ToDo does not exist");
      }

      if (todo.get().doesNotHave(ToDoCapability.RESET))
      {
        return new ToDoUpdateFailedResponse("The ToDo cannot be reset in its current state");
      }

      gateway.store(todo.get().reset());

      return new ToDoUpdatedResponse();
    } catch (final IllegalArgumentException e)
    {
      return new ToDoUpdateFailedResponse(e.getMessage());
    }
  }
}
