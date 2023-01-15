package pendenzenliste.achievements.usecases;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.boundary.in.SubscribeAchievementsInputBoundary;
import pendenzenliste.achievements.boundary.in.SubscribeAchievementsRequest;
import pendenzenliste.achievements.boundary.out.AchievementUnlockedResponse;
import pendenzenliste.achievements.boundary.out.SubscribeAchievementsOutputBoundary;
import pendenzenliste.achievements.model.AchievementUnlockedEvent;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.messaging.Subscriber;

/**
 * A use case that can be used to subscribe to the achievements.
 */
public class SubscribeAchievementsUseCase implements SubscribeAchievementsInputBoundary
{
  private final SubscribeAchievementsOutputBoundary outputBoundary;
  private final EventBus eventBus;

  /**
   * Creates a new instance.
   *
   * @param outputBoundary The output boundary that should be used by this instance.
   * @param eventBus       The event bus.
   */
  public SubscribeAchievementsUseCase(final SubscribeAchievementsOutputBoundary outputBoundary,
                                      final EventBus eventBus)
  {
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final SubscribeAchievementsRequest request)
  {
    eventBus.subscribe(new Subscriber<AchievementUnlockedEvent>()
    {
      @Override
      public void onEvent(final AchievementUnlockedEvent event)
      {
        if (outputBoundary.isDetached())
        {
          eventBus.unsubscribe(this);
        } else
        {
          final var response = new AchievementUnlockedResponse(event.name().name());

          response.applyTo(outputBoundary);
        }
      }

      @Override
      public Collection<Class<? extends AchievementUnlockedEvent>> eventTypes()
      {
        return List.of(AchievementUnlockedEvent.class);
      }
    });
  }
}
