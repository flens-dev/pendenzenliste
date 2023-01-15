import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactoryProvider;
import pendenzenliste.achievements.usecases.AchievementUseCaseFactoryProvider;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;
import pendenzenliste.todos.usecases.ToDoUseCaseFactoryProvider;

module pendenzenliste.usecases.main {
  exports pendenzenliste.achievements.usecases;
  exports pendenzenliste.todos.usecases;

  requires pendenzenliste.boundary.main;
  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.gateway.main;
  requires pendenzenliste.messaging.main;
  requires pendenzenliste.supporting.domain.achievements.main;

  provides AchievementInputBoundaryFactoryProvider
      with AchievementUseCaseFactoryProvider;

  provides ToDoInputBoundaryFactoryProvider
      with ToDoUseCaseFactoryProvider;

  requires io.reactivex.rxjava3;
}