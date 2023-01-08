package pendenzenliste.gateway;

import java.util.ServiceLoader;

/**
 * A provider that can be used to access the achievements.
 */
public interface AchievementGatewayProvider
{
  /**
   * The gateway instance.
   *
   * @return The gateway instance.
   */
  AchievementGateway getInstance();

  /**
   * The default provider.
   *
   * @return The default provider.
   */
  static AchievementGatewayProvider defaultProvider()
  {
    return ServiceLoader.load(AchievementGatewayProvider.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("No default provider registered"));
  }
}
