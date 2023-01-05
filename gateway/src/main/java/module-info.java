import pendenzenliste.gateway.ToDoGatewayProvider;

module pendenzenliste.gateway.main {
  requires pendenzenliste.domain;
  requires pendenzenliste.core.domain.todos.main;
  exports pendenzenliste.gateway;

  uses ToDoGatewayProvider;
}