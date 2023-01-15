package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.CompleteToDoInputBoundary;
import pendenzenliste.boundary.in.CompleteToDoRequest;
import pendenzenliste.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCapabilityValueObject;
import pendenzenliste.gateway.ToDoGateway;

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
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
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
   * Executes the request.
   *
   * @param request The request that should be executed.
   * @return The response.
   */
  public UpdateToDoResponse executeRequest(final CompleteToDoRequest request)
  {
    try
    {
      final var identity = new IdentityValueObject(request.identity());

      final var todo = gateway.findById(identity);

      if (todo.isEmpty())
      {
        return new ToDoUpdateFailedResponse("The ToDo does not exist");
      }

      if (todo.get().doesNotHave(ToDoCapabilityValueObject.COMPLETE))
      {
        return new ToDoUpdateFailedResponse("The ToDo cannot be completed in its current state");
      }

      todo.get().complete();

      gateway.store(todo.get());

      return new ToDoUpdatedResponse();
    } catch (final IllegalArgumentException e)
    {
      return new ToDoUpdateFailedResponse(e.getMessage());
    }

  }
}
