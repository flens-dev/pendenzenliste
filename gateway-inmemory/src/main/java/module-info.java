import pendenzenliste.gateway.AchievementGatewayProvider;
import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.gateway.inmemory.InMemoryAchievementGatewayProvider;
import pendenzenliste.gateway.inmemory.InMemoryToDoGatewayProvider;

module pendenzenliste.gateway.inmemory.main {
  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.supporting.domain.achievements.main;
  requires pendenzenliste.gateway.main;
  requires pendenzenliste.messaging.main;

  provides AchievementGatewayProvider
      with InMemoryAchievementGatewayProvider;

  provides ToDoGatewayProvider
      with InMemoryToDoGatewayProvider;
}