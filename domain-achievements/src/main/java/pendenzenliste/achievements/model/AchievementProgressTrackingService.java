package pendenzenliste.achievements.model;

import pendenzenliste.todos.model.ToDoEvent;

import static java.util.Objects.requireNonNull;

/**
 * A service that can be used to track the progress of the achievements.
 */
public final class AchievementProgressTrackingService {

    private final AchievementRepository repository;

    /**
     * Creates a new instance.
     *
     * @param repository The repository that should be used by this instance.
     */
    public AchievementProgressTrackingService(final AchievementRepository repository) {
        this.repository = requireNonNull(repository, "The repository may not be null");
    }

    /**
     * Handles a {@link ToDoEvent}
     *
     * @param event The event that should be handled.
     */
    public void onEvent(final ToDoEvent event) {
        repository.fetchAll().forEach(achievement -> achievement.trackProgress(event));
    }
}
