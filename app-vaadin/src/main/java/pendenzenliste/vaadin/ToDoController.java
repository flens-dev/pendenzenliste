package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.boundary.in.CompleteToDoRequest;
import pendenzenliste.boundary.in.CreateToDoRequest;
import pendenzenliste.boundary.in.DeleteToDoRequest;
import pendenzenliste.boundary.in.FetchToDoRequest;
import pendenzenliste.boundary.in.ResetToDoRequest;
import pendenzenliste.boundary.in.SubscribeAchievementsRequest;
import pendenzenliste.boundary.in.SubscribeToDoListRequest;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.boundary.in.UpdateToDoRequest;

/**
 * A controller that can be used in the context the application.
 */
public class ToDoController
{
  private final ToDoInputBoundaryFactory factory;
  private final AchievementInputBoundaryFactory achievementFactory;

  /**
   * Creates a new instance.
   *
   * @param todoFactory The factory that should be used by this instance.
   */
  public ToDoController(final ToDoInputBoundaryFactory todoFactory,
                        final AchievementInputBoundaryFactory achievementFactory)
  {
    this.factory = requireNonNull(todoFactory, "The factory may not be null");
    this.achievementFactory = requireNonNull(achievementFactory, "The achievement factory");
  }

  /**
   * Loads the displayed todo for editing.
   *
   * @param identity The identity of the todo that should be loaded.
   */
  public void loadForEdit(final String identity)
  {
    final var request = new FetchToDoRequest(identity);

    factory.fetch().execute(request);
  }

  /**
   * Updates the specified todo.
   *
   * @param identity    The identity.
   * @param headline    The headline.
   * @param description The description.
   */
  public void update(final String identity, final String headline, final String description)
  {
    final var request = new UpdateToDoRequest(identity, headline, description);

    factory.update().execute(request);
  }

  /**
   * Creates a new todo.
   *
   * @param headline    The headline.
   * @param description The description.
   */
  public void create(final String headline, final String description)
  {
    final var request = new CreateToDoRequest(headline, description);

    factory.create().execute(request);
  }

  /**
   * Deletes a todo.
   *
   * @param identity The identity.
   */
  public void delete(final String identity)
  {
    final var request = new DeleteToDoRequest(identity);

    factory.delete().execute(request);
  }

  /**
   * Completes a todo.
   *
   * @param identity The identity.
   */
  public void complete(final String identity)
  {
    final var request = new CompleteToDoRequest(identity);

    factory.complete().execute(request);
  }

  /**
   * Resets a todo.
   *
   * @param identity The identity.
   */
  public void reset(final String identity)
  {
    final var request = new ResetToDoRequest(identity);

    factory.reset().execute(request);
  }

  /**
   * Subscribes to the todo list.
   */
  public void subscribeToDoList()
  {
    final var request = new SubscribeToDoListRequest();

    factory.subscribe().execute(request);
  }

  /**
   * Saves the given data.
   *
   * @param identity    The identity.
   * @param headline    The headline.
   * @param description The description.
   */
  public void save(final String identity, final String headline, final String description)
  {
    if (identity == null || identity.isEmpty())
    {
      create(headline, description);
    } else
    {
      update(identity, headline, description);
    }
  }

  /**
   * Subscribes the achievements.
   */
  public void subscribeAchievements()
  {
    final var request = new SubscribeAchievementsRequest();

    achievementFactory.subscribe().execute(request);
  }
}
