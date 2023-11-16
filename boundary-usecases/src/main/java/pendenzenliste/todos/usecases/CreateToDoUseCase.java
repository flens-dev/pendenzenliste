package pendenzenliste.todos.usecases;

import jakarta.inject.Inject;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.todos.boundary.in.CreateToDoRequest;
import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.CreateToDoResponse;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.*;

import static java.util.Objects.requireNonNull;

/**
 * A use case that can be used to create a new todo.
 */
public class CreateToDoUseCase implements CreateToDoInputBoundary {
    private final ToDoGateway gateway;
    private final CreateToDoOutputBoundary outputBoundary;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param gateway        The gateway that should be used by this instance.
     * @param outputBoundary The output boundary that should be used by this instance.
     * @param eventBus       The event bus that should be used by this instance.
     */
    @Inject
    public CreateToDoUseCase(final ToDoGateway gateway,
                             final CreateToDoOutputBoundary outputBoundary,
                             final EventBus eventBus) {
        this.gateway = requireNonNull(gateway, "The gateway may not be null");
        this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final CreateToDoRequest request) {
        executeRequest(request).applyTo(outputBoundary);
    }

    /**
     * Executes the request.
     *
     * @param request The request that should be executed.
     * @return The response.
     */
    public CreateToDoResponse executeRequest(final CreateToDoRequest request) {
        try {
            final var headline = new HeadlineValueObject(request.headline());
            final var description = new DescriptionValueObject(request.description());

            final var todo =
                    new ToDoAggregate(new ToDoEntity(IdentityValueObject.random(), headline, description, CreatedTimestampValueObject.now(), LastModifiedTimestampValueObject.now(), null, ToDoStateValueObject.OPEN), gateway,
                            eventBus);


            gateway.store(todo);
            eventBus.publish(new ToDoCreatedEvent(todo.aggregateRoot().created().value(), todo.aggregateRoot().identity()));

            return new ToDoCreatedResponse(todo.aggregateRoot().identity().value());
        } catch (final IllegalArgumentException e) {
            return new ToDoCreationFailedResponse(e.getMessage());
        }
    }
}
