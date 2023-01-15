package pendenzenliste.vaadin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.boundary.out.SubscribeAchievementsOutputBoundary;

/**
 * A presenter that can be used to display the subscribed achievements.
 */
public class SubscribeAchievementsPresenter implements SubscribeAchievementsOutputBoundary
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
   * @param viewModel The view model.
   */
  public SubscribeAchievementsPresenter(final ToDoListViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void displayUnlockedAchievement(final String name)
  {
    viewModel.unlockedAchievement.set(new UnlockedAchievementDTO(
        TITLES.getOrDefault(name, name),
        DESCRIPTION.getOrDefault(name, name)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDetached()
  {
    return viewModel.detached.get();
  }
}
