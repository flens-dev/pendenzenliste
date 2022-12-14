import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.gateway.inmemory.InMemoryToDoGatewayProvider;

module pendenzenliste.gateway.inmemory.main {
  requires pendenzenliste.domain.main;
  requires pendenzenliste.gateway.main;

  provides ToDoGatewayProvider with InMemoryToDoGatewayProvider;
}