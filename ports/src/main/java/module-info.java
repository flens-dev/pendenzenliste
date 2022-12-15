import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;

module pendenzenliste.ports.main {
  exports pendenzenliste.ports.in;
  exports pendenzenliste.ports.out;

  requires pendenzenliste.gateway.main;

  uses ToDoInputBoundaryFactoryProvider;
}