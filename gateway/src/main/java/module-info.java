import pendenzenliste.gateway.ToDoGatewayProvider;

module pendenzenliste.gateway.main {
  requires pendenzenliste.domain.main;
  exports pendenzenliste.gateway;

  uses ToDoGatewayProvider;
}