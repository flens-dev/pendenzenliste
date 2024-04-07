package pendenzenliste.achievements.gateway.eclipsestore;

import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.messaging.EventBus;

import java.io.IOException;
import java.nio.file.Files;

@Deprecated(forRemoval = true)
public class EclipseStoreAchievementGatewayProvider implements AchievementGatewayProvider {

    private static final EclipseStoreAchievementGateway INSTANCE;

    static {
        try {
            final var storageManager = EmbeddedStorage.start(
                    Files.createTempDirectory("achievements")
            );

            storageManager.storeRoot();

            INSTANCE = new EclipseStoreAchievementGateway(storageManager,
                    EventBus.defaultEventBus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementGateway getInstance() {
        return INSTANCE;
    }
}
