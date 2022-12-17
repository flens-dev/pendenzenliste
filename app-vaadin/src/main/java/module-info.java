import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;

module pendenzenliste.app.vaadin.main {
  exports pendenzenliste.vaadin;

  requires pendenzenliste.ports.main;
  uses ToDoInputBoundaryFactoryProvider;

  requires spring.boot;
  requires spring.boot.autoconfigure;
}