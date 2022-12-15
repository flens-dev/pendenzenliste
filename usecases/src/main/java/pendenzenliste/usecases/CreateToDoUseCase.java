package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.DescriptionValueObject;
import pendenzenliste.domain.HeadlineValueObject;
import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.CreateToDoInputBoundary;
import pendenzenliste.ports.in.CreateToDoRequest;
import pendenzenliste.ports.out.CreateToDoResponse;
import pendenzenliste.ports.out.ToDoCreatedResponse;
import pendenzenliste.ports.out.ToDoCreationFailedResponse;

/**
 * A use case that can be used to create a new todo.
 */
public class CreateToDoUseCase implements CreateToDoInputBoundary
{
  private final ToDoGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public CreateToDoUseCase(final ToDoGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoResponse execute(final CreateToDoRequest request)
  {
    try
    {
      final var headline = new HeadlineValueObject(request.headline());
      final var description =
          new DescriptionValueObject(request.description());

      final var todo = ToDoEntity.createOpenToDo(headline, description);

      gateway.store(todo);

      return new ToDoCreatedResponse(todo.identity().value());
    } catch (final IllegalArgumentException e)
    {
      return new ToDoCreationFailedResponse(e.getMessage());
    }
  }
}
