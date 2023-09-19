package pendenzenliste.gateway.redis;


import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.messaging.EventBus;
import redis.clients.jedis.Jedis;

/**
 * A redis based implementation of the {@link AchievementGatewayProvider}
 *
 * @deprecated Use a proper dependency injection framework instead.
 */
@Deprecated(forRemoval = true)
public class RedisAchievementGatewayProvider implements AchievementGatewayProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementGateway getInstance() {
        return new RedisAchievementGateway(new Jedis(), EventBus.defaultEventBus());
    }
}
