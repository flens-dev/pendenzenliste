package pendenzenliste.usecases;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.ResetToDoInputBoundary;
import pendenzenliste.boundary.in.ResetToDoRequest;
import pendenzenliste.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;
import pendenzenliste.domain.IdentityValueObject;
import pendenzenliste.domain.ToDoCapabilityValueObject;
import pendenzenliste.domain.ToDoEventPublisher;
import pendenzenliste.domain.ToDoResetEvent;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to reset a closed ToDo.
 */
public class ResetToDoUseCase implements ResetToDoInputBoundary
{
  private final ToDoGateway gateway;
  private final UpdateToDoOutputBoundary outputBoundary;
  private final ToDoEventPublisher eventPublisher;

  /**
   * Creates a new instance.
   *
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
   * @param eventPublisher The event publisher that should be used by this instance.
   */
  public ResetToDoUseCase(final ToDoGateway gateway, final UpdateToDoOutputBoundary outputBoundary,
                          final ToDoEventPublisher eventPublisher)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
    this.eventPublisher = requireNonNull(eventPublisher, "The event publisher may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final ResetToDoRequest request)
  {
    executeRequest(request).applyTo(outputBoundary);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoResponse executeRequest(final ResetToDoRequest request)
  {
    try
    {
      final var identity = new IdentityValueObject(request.identity());

      final var todo = gateway.findById(identity);

      if (todo.isEmpty())
      {
        return new ToDoUpdateFailedResponse("The ToDo does not exist");
      }

      if (todo.get().doesNotHave(ToDoCapabilityValueObject.RESET))
      {
        return new ToDoUpdateFailedResponse("The ToDo cannot be reset in its current state");
      }

      gateway.store(todo.get().reset());
      eventPublisher.publish(new ToDoResetEvent(LocalDateTime.now(), identity));

      return new ToDoUpdatedResponse();
    } catch (final IllegalArgumentException e)
    {
      return new ToDoUpdateFailedResponse(e.getMessage());
    }
  }
}
