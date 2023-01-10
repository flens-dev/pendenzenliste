import pendenzenliste.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactoryProvider;

module pendenzenliste.boundary.main {
  exports pendenzenliste.boundary.in;
  exports pendenzenliste.boundary.out;

  requires pendenzenliste.gateway.main;

  uses AchievementInputBoundaryFactory;
  uses ToDoInputBoundaryFactoryProvider;
}