import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.gateway.inmemory.InMemoryToDoGatewayProvider;

module pendenzenliste.gateway.inmemory.main {
  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.gateway.main;

  provides ToDoGatewayProvider
      with InMemoryToDoGatewayProvider;
}