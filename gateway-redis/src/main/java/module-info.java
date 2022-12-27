import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.gateway.redis.RedisToDoGatewayProvider;

module pendenzenliste.gateway.redis.main {
  requires pendenzenliste.domain.main;
  requires pendenzenliste.gateway.main;
  requires redis.clients.jedis;

  provides ToDoGatewayProvider
      with RedisToDoGatewayProvider;
}