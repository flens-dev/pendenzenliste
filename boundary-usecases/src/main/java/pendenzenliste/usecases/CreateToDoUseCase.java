package pendenzenliste.usecases;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.boundary.in.CreateToDoRequest;
import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.CreateToDoResponse;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;
import pendenzenliste.domain.todos.DescriptionValueObject;
import pendenzenliste.domain.todos.HeadlineValueObject;
import pendenzenliste.domain.todos.ToDoAggregate;
import pendenzenliste.domain.todos.ToDoCreatedEvent;
import pendenzenliste.domain.todos.ToDoStateValueObject;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to create a new todo.
 */
public class CreateToDoUseCase implements CreateToDoInputBoundary
{
  private final ToDoGateway gateway;
  private final CreateToDoOutputBoundary outputBoundary;

  /**
   * Creates a new instance.
   *
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
   */
  public CreateToDoUseCase(final ToDoGateway gateway, final CreateToDoOutputBoundary outputBoundary)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
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
   * Executes the request.
   *
   * @param request The request that should be executed.
   * @return The response.
   */
  public CreateToDoResponse executeRequest(final CreateToDoRequest request)
  {
    try
    {
      final var headline = new HeadlineValueObject(request.headline());
      final var description = new DescriptionValueObject(request.description());

      final var todo = ToDoAggregate.builder().randomIdentity()
          .headline(headline.value())
          .description(description.value())
          .state(ToDoStateValueObject.OPEN)
          .created(LocalDateTime.now())
          .lastModified(LocalDateTime.now())
          .build();

      todo.appendEvent(new ToDoCreatedEvent(todo.aggregateRoot().created().value(),
          todo.aggregateRoot().identity()));

      gateway.store(todo);

      return new ToDoCreatedResponse(todo.aggregateRoot().identity().value());
    } catch (final IllegalArgumentException e)
    {
      return new ToDoCreationFailedResponse(e.getMessage());
    }
  }
}
