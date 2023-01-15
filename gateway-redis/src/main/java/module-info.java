import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.gateway.redis.RedisAchievementGatewayProvider;
import pendenzenliste.gateway.redis.RedisToDoGatewayProvider;
import pendenzenliste.todos.gateway.ToDoGatewayProvider;

module pendenzenliste.gateway.redis.main {
  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.supporting.domain.achievements.main;
  requires pendenzenliste.gateway.main;
  requires pendenzenliste.messaging.main;
  requires pendenzenliste.util.serialization.main;
  requires redis.clients.jedis;

  provides ToDoGatewayProvider
      with RedisToDoGatewayProvider;

  provides AchievementGatewayProvider
      with RedisAchievementGatewayProvider;
}