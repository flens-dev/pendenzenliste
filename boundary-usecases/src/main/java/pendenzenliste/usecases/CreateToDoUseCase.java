package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.DescriptionValueObject;
import pendenzenliste.domain.HeadlineValueObject;
import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.boundary.in.CreateToDoRequest;
import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.CreateToDoResponse;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;

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
   * @param gateway The gateway that should be used by this instance.
   */
  public CreateToDoUseCase(final ToDoGateway gateway,
                           final CreateToDoOutputBoundary outputBoundary)
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
   * {@inheritDoc}
   */
  @Override
  public CreateToDoResponse executeRequest(final CreateToDoRequest request)
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
