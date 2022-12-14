package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.DescriptionValueObject;
import pendenzenliste.domain.HeadlineValueObject;
import pendenzenliste.domain.IdentityValueObject;
import pendenzenliste.domain.ToDoCapability;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.UpdateToDoInputBoundary;
import pendenzenliste.ports.in.UpdateToDoRequest;
import pendenzenliste.ports.out.ToDoUpdateFailedResponse;
import pendenzenliste.ports.out.ToDoUpdatedResponse;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * A use case that can be used to update an existing todo.
 */
public class UpdateToDoUseCase implements UpdateToDoInputBoundary
{
  private final ToDoGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public UpdateToDoUseCase(final ToDoGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoResponse execute(final UpdateToDoRequest request)
  {
    try
    {
      final var identity =
          new IdentityValueObject(request.identity());

      final var headline =
          new HeadlineValueObject(request.headline());

      final var description =
          new DescriptionValueObject(request.description());

      final var todo = gateway.findById(identity);

      if (todo.isEmpty())
      {
        return new ToDoUpdateFailedResponse("The ToDo does not exist");
      }

      if (todo.get().doesNotHave(ToDoCapability.UPDATE))
      {
        return new ToDoUpdateFailedResponse("The ToDo cannot be updated in its current state");
      }

      gateway.store(todo.get().update(headline, description));

      return new ToDoUpdatedResponse();
    } catch (final IllegalArgumentException e)
    {
      return new ToDoUpdateFailedResponse(e.getMessage());
    }
  }
}
