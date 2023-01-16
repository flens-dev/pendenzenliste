package pendenzenliste.todos.usecases;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.todos.boundary.in.DeleteToDoInputBoundary;
import pendenzenliste.todos.boundary.in.DeleteToDoRequest;
import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoResponse;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoCapabilityValueObject;

/**
 * A use case that can be used to delete an existing ToDo.
 */
public class DeleteToDoUseCase implements DeleteToDoInputBoundary
{
  private final ToDoGateway gateway;
  private final UpdateToDoOutputBoundary outputBoundary;

  /**
   * Creates a new instance.
   *
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
   */
  public DeleteToDoUseCase(final ToDoGateway gateway,
                           final UpdateToDoOutputBoundary outputBoundary)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final DeleteToDoRequest request)
  {
    executeRequest(request).applyTo(outputBoundary);
  }

  /**
   * Executes the request.
   *
   * @param request The request that should be executed.
   * @return The response.
   */
  public UpdateToDoResponse executeRequest(final DeleteToDoRequest request)
  {
    try
    {
      final var identity = new IdentityValueObject(request.identity());

      final var todo = gateway.findById(identity);


      if (todo.isEmpty())
      {
        return new ToDoUpdateFailedResponse("The ToDo does not exist");
      }

      if (todo.get().doesNotHave(ToDoCapabilityValueObject.DELETE))
      {
        return new ToDoUpdateFailedResponse("The ToDo cannot be deleted in its current state");
      }

      final var deleted = gateway.delete(todo.get().aggregateRoot().identity());

      if (deleted)
      {
        return new ToDoUpdatedResponse();
      } else
      {
        return new ToDoUpdateFailedResponse("Deleting the todo failed");
      }
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
