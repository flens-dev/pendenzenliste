package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.IdentityValueObject;
import pendenzenliste.domain.ToDoCapability;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.CompleteToDoInputBoundary;
import pendenzenliste.ports.in.CompleteToDoRequest;
import pendenzenliste.ports.out.ToDoUpdateFailedResponse;
import pendenzenliste.ports.out.ToDoUpdatedResponse;
import pendenzenliste.ports.out.UpdateToDoOutputBoundary;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * A use case that can be used to complete a ToDo.
 */
public class CompleteToDoUseCase implements CompleteToDoInputBoundary
{
  private final ToDoGateway gateway;
  private final UpdateToDoOutputBoundary outputBoundary;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public CompleteToDoUseCase(final ToDoGateway gateway,
                             final UpdateToDoOutputBoundary outputBoundary)
  {

    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final CompleteToDoRequest request)
  {
    executeRequest(request).applyTo(outputBoundary);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoResponse executeRequest(final CompleteToDoRequest request)
  {
    try
    {
      final var identity =
          new IdentityValueObject(request.identity());

      final var todo = gateway.findById(identity);

      if (todo.isEmpty())
      {
        return new ToDoUpdateFailedResponse("The ToDo does not exist");
      }

      if (todo.get().doesNotHave(ToDoCapability.COMPLETE))
      {
        return new ToDoUpdateFailedResponse("The ToDo cannot be completed in its current state");
      }

      gateway.store(todo.get().complete());

      return new ToDoUpdatedResponse();
    } catch (final IllegalArgumentException e)
    {
      return new ToDoUpdateFailedResponse(e.getMessage());
    }

  }
}
