package pendenzenliste.gateway.redis;


import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.gateway.ToDoGatewayProvider;
import redis.clients.jedis.Jedis;

/**
 * A {@link ToDoGatewayProvider} that provides access to a {@link RedisToDoGateway}
 *
 * @deprecated Use a proper dependency injection framework instead.
 */
@Deprecated(forRemoval = true)
public class RedisToDoGatewayProvider implements ToDoGatewayProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public ToDoGateway getInstance() {
        return new RedisToDoGateway(new Jedis(), EventBus.defaultEventBus());
    }
}
