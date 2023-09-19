package pendenzenliste.todos.usecases;

import jakarta.inject.Inject;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.messaging.Subscriber;
import pendenzenliste.todos.boundary.in.SubscribeToDoListInputBoundary;
import pendenzenliste.todos.boundary.in.SubscribeToDoListRequest;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.ToDoEvent;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.todos.usecases.ResponseModelMappingUtils.mapToResponseModel;

/**
 * A use case that can be used to subscribe to a list of todos.
 */
public class SubscribeToDoListUseCase implements SubscribeToDoListInputBoundary {
    private final ToDoGateway gateway;
    private final FetchToDoListOutputBoundary outputBoundary;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param gateway        The gateway that should be used by this instance.
     * @param outputBoundary The output boundary that should be used by this instance.
     * @param eventBus       The event bus that should be used by this instance.
     */
    @Inject
    public SubscribeToDoListUseCase(final ToDoGateway gateway,
                                    final FetchToDoListOutputBoundary outputBoundary,
                                    final EventBus eventBus) {
        this.gateway = requireNonNull(gateway, "The gateway may not be null");
        this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final SubscribeToDoListRequest request) {
        eventBus.subscribe(subscriber());

        fetchList();
    }

    /**
     * Fetches the list of todos.
     */
    private void fetchList() {
        final var todos = gateway.fetchAll().map(mapToResponseModel()).toList();
        final var response = new FetchedToDoListResponse(todos);

        response.applyTo(outputBoundary);
    }

    /**
     * Builds the subscriber.
     *
     * @return The subscriber.
     */
    private Subscriber<ToDoEvent> subscriber() {
        return new Subscriber<>() {
            @Override
            public void onEvent(final ToDoEvent event) {
                if (outputBoundary.isDetached()) {
                    eventBus.unsubscribe(this);
                } else {
                    fetchList();
                }
            }

            @Override
            public Collection<Class<? extends ToDoEvent>> eventTypes() {
                return List.of(ToDoEvent.class);
            }
        };
    }
}
