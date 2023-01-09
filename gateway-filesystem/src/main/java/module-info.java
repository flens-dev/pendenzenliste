import pendenzenliste.gateway.AchievementGatewayProvider;
import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.gateway.filesystem.FilesystemAchievementGatewayProvider;
import pendenzenliste.gateway.filesystem.FilesystemToDoGatewayProvider;

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