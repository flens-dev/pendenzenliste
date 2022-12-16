import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;

module pendenzenliste.app.javafx.main {
  exports pendenzenliste.javafx;

  requires pendenzenliste.ports.main;
  uses ToDoInputBoundaryFactoryProvider;

  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires javafx.fxml;

  requires io.reactivex.rxjava3;
}