package pendenzenliste.gateway.redis;

import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.gateway.ToDoGatewayProvider;
import redis.clients.jedis.Jedis;

/**
 * A {@link ToDoGatewayProvider} that provides access to a {@link RedisToDoGateway}
 */
public class RedisToDoGatewayProvider implements ToDoGatewayProvider
{
  /**
   * {@inheritDoc}
   */
  @Override
  public ToDoGateway getInstance()
  {
    return new RedisToDoGateway(new Jedis());
  }
}
