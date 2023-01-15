import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;

module pendenzenliste.boundary.main {
  exports pendenzenliste.boundary.in;
  exports pendenzenliste.boundary.out;
  exports pendenzenliste.achievements.boundary.in;
  exports pendenzenliste.achievements.boundary.out;
  exports pendenzenliste.todos.boundary.in;
  exports pendenzenliste.todos.boundary.out;

  requires pendenzenliste.gateway.main;

  uses AchievementInputBoundaryFactory;
  uses ToDoInputBoundaryFactoryProvider;
}