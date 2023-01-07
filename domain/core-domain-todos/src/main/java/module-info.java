import pendenzenliste.messaging.EventBus;

module pendenzenliste.core.domain.todos.main {
  exports pendenzenliste.domain.todos;

  requires pendenzenliste.messaging.main;

  uses EventBus;
}