package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.UpdateToDoInputBoundary;
import pendenzenliste.boundary.in.UpdateToDoRequest;
import pendenzenliste.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;
import pendenzenliste.domain.todos.DescriptionValueObject;
import pendenzenliste.domain.todos.HeadlineValueObject;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCapabilityValueObject;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to update an existing todo.
 */
public class UpdateToDoUseCase implements UpdateToDoInputBoundary
{
  private final ToDoGateway gateway;
  private final UpdateToDoOutputBoundary outputBoundary;

  /**
   * Creates a new instance.
   *
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
   */
  public UpdateToDoUseCase(final ToDoGateway gateway, final UpdateToDoOutputBoundary outputBoundary)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final UpdateToDoRequest request)
  {
    executeRequest(request).applyTo(outputBoundary);
  }

  /**
   * Executes the request.
   *
   * @param request The request that should be executed.
   * @return The response.
   */
  public UpdateToDoResponse executeRequest(final UpdateToDoRequest request)
  {
    try
    {
      final var identity = new IdentityValueObject(request.identity());

      final var headline = new HeadlineValueObject(request.headline());

      final var description = new DescriptionValueObject(request.description());

      final var todo = gateway.findById(identity);

      if (todo.isEmpty())
      {
        return new ToDoUpdateFailedResponse("The ToDo does not exist");
      }

      if (todo.get().doesNotHave(ToDoCapabilityValueObject.UPDATE))
      {
        return new ToDoUpdateFailedResponse("The ToDo cannot be updated in its current state");
      }

      todo.get().update(headline, description);

      gateway.store(todo.get());

      return new ToDoUpdatedResponse();
    } catch (final IllegalArgumentException e)
    {
      return new ToDoUpdateFailedResponse(e.getMessage());
    }
  }
}
