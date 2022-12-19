import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.gateway.filesystem.FilesystemToDoGatewayProvider;

module pendenzenliste.gateway.filesystem.main {
  requires pendenzenliste.domain.main;
  requires pendenzenliste.gateway.main;

  provides ToDoGatewayProvider
      with FilesystemToDoGatewayProvider;
}