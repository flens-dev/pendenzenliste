package pendenzenliste.achievements.boundary.in;

import java.util.ServiceLoader;

import pendenzenliste.achievements.boundary.out.AchievementOutputBoundaryFactory;


/**
 * A provider that can be used to access an {@link AchievementInputBoundaryFactory}.
 */
public interface AchievementInputBoundaryFactoryProvider
{
  /**
   * Retrieves an instance of the input boundary factory for the given output boundary factory.
   *
   * @param factory The factory.
   * @return The factory.
   */
  AchievementInputBoundaryFactory getInstance(AchievementOutputBoundaryFactory factory);

  /**
   * Retrieves the default provider.
   *
   * @return The default provider.
   */
  static AchievementInputBoundaryFactoryProvider defaultProvider()
  {
    return ServiceLoader.load(AchievementInputBoundaryFactoryProvider.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("No default provider registered"));
  }
}
