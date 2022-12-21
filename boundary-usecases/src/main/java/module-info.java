import pendenzenliste.boundary.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.domain.ToDoEventPublisher;
import pendenzenliste.domain.ToDoEventSubscriptionTopic;
import pendenzenliste.usecases.ToDoUseCaseFactoryProvider;

module pendenzenliste.usecases.main {
  exports pendenzenliste.usecases;

  requires pendenzenliste.domain.main;
  requires pendenzenliste.boundary.main;
  requires pendenzenliste.gateway.main;

  provides ToDoInputBoundaryFactoryProvider
      with ToDoUseCaseFactoryProvider;

  requires io.reactivex.rxjava3;

  uses ToDoEventPublisher;
  uses ToDoEventSubscriptionTopic;
}