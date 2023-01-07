import pendenzenliste.boundary.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.usecases.ToDoUseCaseFactoryProvider;

module pendenzenliste.usecases.main {
  exports pendenzenliste.usecases;

  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.boundary.main;
  requires pendenzenliste.gateway.main;
  requires pendenzenliste.messaging.main;

  provides ToDoInputBoundaryFactoryProvider
      with ToDoUseCaseFactoryProvider;

  requires io.reactivex.rxjava3;
}