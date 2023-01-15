package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.boundary.out.AchievementOutputBoundaryFactory;
import pendenzenliste.achievements.boundary.out.SubscribeAchievementsOutputBoundary;

/**
 * A factory that can be used to provide presenters for the achievements.
 */
public class AchievementPresenterFactory implements AchievementOutputBoundaryFactory
{
  private final ToDoListViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by this instance.
   */
  public AchievementPresenterFactory(final ToDoListViewModel viewModel)
  {

    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SubscribeAchievementsOutputBoundary subscribe()
  {
    return new SubscribeAchievementsPresenter(viewModel);
  }
}
