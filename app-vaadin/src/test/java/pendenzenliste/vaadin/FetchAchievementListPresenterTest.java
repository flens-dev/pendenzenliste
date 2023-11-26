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
}