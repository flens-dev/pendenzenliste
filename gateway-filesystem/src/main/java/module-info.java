import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.achievements.gateway.filesystem.FilesystemAchievementGatewayProvider;
import pendenzenliste.todos.gateway.ToDoGatewayProvider;
import pendenzenliste.todos.gateway.filesystem.FilesystemToDoGatewayProvider;

module pendenzenliste.gateway.filesystem.main {
  requires pendenzenliste.supporting.domain.achievements.main;
  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.gateway.main;
  requires pendenzenliste.messaging.main;

  provides AchievementGatewayProvider
      with FilesystemAchievementGatewayProvider;

  provides ToDoGatewayProvider
      with FilesystemToDoGatewayProvider;
}