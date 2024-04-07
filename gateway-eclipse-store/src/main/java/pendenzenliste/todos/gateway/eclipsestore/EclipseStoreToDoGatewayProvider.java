package pendenzenliste.todos.gateway.eclipsestore;

import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.gateway.ToDoGatewayProvider;

import java.io.IOException;
import java.nio.file.Files;

@Deprecated(forRemoval = true)
public class EclipseStoreToDoGatewayProvider implements ToDoGatewayProvider {

    private static final EclipseStoreTodoGateway INSTANCE;

    static {
        try {
            final var storageManager = EmbeddedStorage.start(
                    Files.createTempDirectory("todos")
            );

            storageManager.storeRoot();

            INSTANCE = new EclipseStoreTodoGateway(storageManager,
                    EventBus.defaultEventBus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ToDoGateway getInstance() {
        return INSTANCE;
    }
}
