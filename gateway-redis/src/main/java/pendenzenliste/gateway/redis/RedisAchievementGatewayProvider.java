package pendenzenliste.gateway.redis;

import pendenzenliste.gateway.AchievementGateway;
import pendenzenliste.gateway.AchievementGatewayProvider;
import pendenzenliste.messaging.EventBus;
import redis.clients.jedis.Jedis;

/**
 * A redis based implementation of the {@link AchievementGatewayProvider}
 */
public class RedisAchievementGatewayProvider implements AchievementGatewayProvider
{
  /**
   * {@inheritDoc}
   */
  @Override
  public AchievementGateway getInstance()
  {
    return new RedisAchievementGateway(new Jedis(), EventBus.defaultEventBus());
  }
}
