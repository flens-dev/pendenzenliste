package pendenzenliste.vaadin;

import pendenzenliste.achievements.boundary.out.AchievementResponseModel;
import pendenzenliste.achievements.boundary.out.FetchAchievementListOutputBoundary;
import pendenzenliste.achievements.boundary.out.FetchAchievementListResponse;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to fetch a list of achievements.
 */
public class FetchAchievementListPresenter implements FetchAchievementListOutputBoundary {
    private final ToDoListViewModel viewModel;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model that should be used by this instance.
     */
    public FetchAchievementListPresenter(final ToDoListViewModel viewModel) {
        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final FetchAchievementListResponse response) {
        viewModel.achievements.set(response.achievements().stream()
                .map(mapToViewModel())
                .toList());
    }

    /**
     * A function that can be used to map the response model to a view model.
     *
     * @return The mapping function.
     */
    private static Function<AchievementResponseModel, AchievementViewModel> mapToViewModel() {
        return achievement -> {
            final var viewModel = new AchievementViewModel();

            if ("UNLOCKED".equals(achievement.state())) {
                viewModel.title.set(AchievementTranslationUtils.getTitleOf(achievement.name()));
                viewModel.description.set(AchievementTranslationUtils.getDescriptionOf(achievement.name()));
            } else {
                viewModel.title.set(AchievementTranslationUtils.LOCKED_TITLE);
                viewModel.description.set(AchievementTranslationUtils.LOCKED_DESCRIPTION);
            }

            viewModel.unlocked.set(achievement.unlocked());
            viewModel.state.set(achievement.state());

            return viewModel;
        };
    }
}
