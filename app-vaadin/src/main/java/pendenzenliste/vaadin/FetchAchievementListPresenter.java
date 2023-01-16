package pendenzenliste.vaadin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.boundary.out.AchievementResponseModel;
import pendenzenliste.achievements.boundary.out.FetchAchievementListOutputBoundary;
import pendenzenliste.achievements.boundary.out.FetchAchievementListResponse;

/**
 * A presenter that can be used to fetch a list of achievements.
 */
public class FetchAchievementListPresenter implements FetchAchievementListOutputBoundary
{
  private static final Map<String, String> TITLES = new ConcurrentHashMap<>();
  private static final Map<String, String> DESCRIPTION = new ConcurrentHashMap<>();

  static
  {
    TITLES.put("JOURNEY_BEGINS", "The journey of a thousand miles begins with one step");
    DESCRIPTION.put("JOURNEY_BEGINS", "Create your first todo");

    TITLES.put("DONEZO", "Donezo!");
    DESCRIPTION.put("DONEZO", "Complete your first todo");

    TITLES.put("NEW_YEAR_NEW_ME", "New year, new me!");
    DESCRIPTION.put("NEW_YEAR_NEW_ME", "Create a todo on new year");

    TITLES.put("IT_BURNS", "Oh, it burns!");
    DESCRIPTION.put("IT_BURNS", "Complete ten todos in one week");

    TITLES.put("THIRD_TIMES_THE_CHARM", "Third time's the charm");
    DESCRIPTION.put("THIRD_TIMES_THE_CHARM", "Reopen a todo for the third time");
  }

  private final ToDoListViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by this instance.
   */
  public FetchAchievementListPresenter(final ToDoListViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final FetchAchievementListResponse response)
  {
    viewModel.achievements.set(response.achievements().stream().map(mapToViewModel()).toList());
  }

  /**
   * A function that can be used to map the response model to a view model.
   *
   * @return The mapping function.
   */
  private static Function<AchievementResponseModel, AchievementViewModel> mapToViewModel()
  {
    return achievement -> {
      final var viewModel = new AchievementViewModel();

      if ("UNLOCKED".equals(achievement.state()))
      {
        viewModel.title.set(TITLES.getOrDefault(achievement.name(), achievement.name()));
        viewModel.description.set(DESCRIPTION.getOrDefault(achievement.name(), achievement.name()));
      } else
      {
        viewModel.title.set("Hidden");
        viewModel.description.set("This achievement is hidden");
      }

      viewModel.unlocked.set(achievement.unlocked());
      viewModel.state.set(achievement.state());

      return viewModel;
    };
  }
}
