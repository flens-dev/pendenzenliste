package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.boundary.in.CreateToDoRequest;
import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.CreateToDoResponse;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;
import pendenzenliste.domain.todos.DescriptionValueObject;
import pendenzenliste.domain.todos.HeadlineValueObject;
import pendenzenliste.domain.todos.ToDoCreatedEvent;
import pendenzenliste.domain.todos.ToDoEntity;
import pendenzenliste.domain.todos.ToDoEventPublisher;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to create a new todo.
 */
public class CreateToDoUseCase implements CreateToDoInputBoundary
{
  private final ToDoGateway gateway;
  private final CreateToDoOutputBoundary outputBoundary;
  private final ToDoEventPublisher eventPublisher;

  /**
   * Creates a new instance.
   *
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
   * @param eventPublisher The event publisher that should be used by this instance.
   */
  public CreateToDoUseCase(final ToDoGateway gateway, final CreateToDoOutputBoundary outputBoundary,
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
  public void execute(final CreateToDoRequest request)
  {
    executeRequest(request).applyTo(outputBoundary);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoResponse executeRequest(final CreateToDoRequest request)
  {
    try
    {
      final var headline = new HeadlineValueObject(request.headline());
      final var description = new DescriptionValueObject(request.description());

      final var todo = ToDoEntity.createOpenToDo(headline, description);

      gateway.store(todo);
      eventPublisher.publish(new ToDoCreatedEvent(todo.created().value(), todo.identity()));

      return new ToDoCreatedResponse(todo.identity().value());
    } catch (final IllegalArgumentException e)
    {
      return new ToDoCreationFailedResponse(e.getMessage());
    }
  }
}
