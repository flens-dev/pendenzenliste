package pendenzenliste.vaadin;

import pendenzenliste.achievements.boundary.out.SubscribeAchievementsOutputBoundary;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to display the subscribed achievements.
 */
public class SubscribeAchievementsPresenter implements SubscribeAchievementsOutputBoundary {

    private final ToDoListViewModel viewModel;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model.
     */
    public SubscribeAchievementsPresenter(final ToDoListViewModel viewModel) {
        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayUnlockedAchievement(final String name) {
        final UnlockedAchievementViewModel achievement = new UnlockedAchievementViewModel();

        achievement.title.set(AchievementTranslationUtils.getTitleOf(name));
        achievement.description.set(AchievementTranslationUtils.getDescriptionOf(name));

        viewModel.unlockedAchievement.set(achievement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDetached() {
        return viewModel.detached.get();
    }
}
