package pendenzenliste.achievements.usecases;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.boundary.in.FetchAchievementListInputBoundary;
import pendenzenliste.achievements.boundary.in.FetchAchievementListRequest;
import pendenzenliste.achievements.boundary.out.AchievementResponseModel;
import pendenzenliste.achievements.boundary.out.FetchAchievementListOutputBoundary;
import pendenzenliste.achievements.boundary.out.FetchAchievementListResponse;
import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.model.AchievementAggregate;
import pendenzenliste.achievements.model.UnlockedTimestampValueType;

/**
 * A use case that can be used to fetch a list of achievements.
 */
public class FetchAchievementListUseCase implements FetchAchievementListInputBoundary
{
  private final FetchAchievementListOutputBoundary outputBoundary;
  private final AchievementGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param outputBoundary The output boundary that should be used by this instance.
   * @param gateway        The gateway that should be used by this instance.
   */
  public FetchAchievementListUseCase(final FetchAchievementListOutputBoundary outputBoundary,
                                     final AchievementGateway gateway)
  {
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final FetchAchievementListRequest request)
  {
    final var achievements = gateway.fetchAll().map(mapToResponseModel()).toList();

    outputBoundary.handleSuccessfulResponse(new FetchAchievementListResponse(achievements));
  }

  /**
   * A function that can be used to map an aggregate to a response model.
   *
   * @return The mapping function.
   */
  private Function<AchievementAggregate, AchievementResponseModel> mapToResponseModel()
  {
    return achievement -> new AchievementResponseModel(achievement.aggregateRoot().name().name(),
        achievement.aggregateRoot().state().name(),
        Optional.ofNullable(achievement.aggregateRoot().unlocked())
            .map(UnlockedTimestampValueType::value).orElse(null));
  }
}
