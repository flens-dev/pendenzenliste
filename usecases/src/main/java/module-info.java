import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.usecases.ToDoUseCaseFactoryProvider;

module pendenzenliste.usecases.main {
  exports pendenzenliste.usecases;

  requires pendenzenliste.domain.main;
  requires pendenzenliste.ports.main;
  requires pendenzenliste.gateway.main;

  provides ToDoInputBoundaryFactoryProvider with ToDoUseCaseFactoryProvider;
}