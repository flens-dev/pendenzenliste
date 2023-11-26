package pendenzenliste.vaadin;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.achievements.boundary.out.AchievementResponseModel;
import pendenzenliste.achievements.boundary.out.FetchAchievementListResponse;

import java.time.LocalDateTime;
import java.util.List;

class FetchAchievementListPresenterTest {

    @Test
    public void handleSuccessfulResponse_lockedAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("DONEZO", "LOCKED", LocalDateTime.now()))));

        final var assertions = new SoftAssertions();

        assertions.assertThat(viewModel.achievements.get()).isNotEmpty();
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().title.get())
                .isEqualTo("Hidden");
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().description.get())
                .isEqualTo("This achievement is hidden");
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().unlocked.get())
                .isNotNull();
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().state.get())
                .isEqualTo("LOCKED");

        assertions.assertAll();
    }

    @Test
    public void handleSuccessfulResponse_unlockedJourneyBeginsAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("JOURNEY_BEGINS", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "The journey of a thousand miles begins with one step";
        final String expectedDescription = "Create your first todo";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    @Test
    public void handleSuccessfulResponse_unlockedDonezoAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("DONEZO", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "Donezo!";
        final String expectedDescription = "Complete your first todo";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    @Test
    public void handleSuccessfulResponse_unlockedNewYearNewMeAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("NEW_YEAR_NEW_ME", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "New year, new me!";
        final String expectedDescription = "Create a todo on new year";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    @Test
    public void handleSuccessfulResponse_unlockedItBurnsAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("IT_BURNS", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "Oh, it burns!";
        final String expectedDescription = "Complete ten todos in one week";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    @Test
    public void handleSuccessfulResponse_unlockedThirdTimesTheCharmAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("THIRD_TIMES_THE_CHARM", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "Third time's the charm";
        final String expectedDescription = "Reopen a todo for the third time";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    @Test
    public void handleSuccessfulResponse_unlockedAchievementHunterAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("ACHIEVEMENT_HUNTER", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "Achievement hunter";
        final String expectedDescription = "Collect three achievements";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    @Test
    public void handleSuccessfulResponse_unlockedAllDoneAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("ALL_DONE", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "All done!";
        final String expectedDescription = "You did it! You completed all of your todos!";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    @Test
    public void handleSuccessfulResponse_unlockedLazyDogAchievement() {
        final var viewModel = new ToDoListViewModel();
        final var presenter = new FetchAchievementListPresenter(viewModel);

        presenter.handleSuccessfulResponse(new FetchAchievementListResponse(
                List.of(new AchievementResponseModel("LAZY_DOG", "UNLOCKED", LocalDateTime.now()))));

        final String expectedTitle = "You lazy dog!";
        final String expectedDescription = "Leave one of your todos open for three months";

        assertUnlockedAchievement(viewModel, expectedTitle, expectedDescription);
    }

    /**
     * Asserts an unlocked achievement.
     *
     * @param viewModel           The view model.
     * @param expectedTitle       The expected title.
     * @param expectedDescription The expected description.
     */
    private static void assertUnlockedAchievement(
            final ToDoListViewModel viewModel,
            final String expectedTitle,
            final String expectedDescription) {
        final var assertions = new SoftAssertions();

        assertions.assertThat(viewModel.achievements.get()).isNotEmpty();
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().title.get())
                .isEqualTo(expectedTitle);
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().description.get())
                .isEqualTo(expectedDescription);
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().unlocked.get())
                .isNotNull();
        assertions.assertThat(viewModel.achievements.get().stream().findFirst().orElseThrow().state.get())
                .isEqualTo("UNLOCKED");

        assertions.assertAll();
    }
}