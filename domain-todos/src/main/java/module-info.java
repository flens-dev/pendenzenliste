import pendenzenliste.messaging.EventBus;

module pendenzenliste.core.domain.todos.main {
    exports pendenzenliste.todos.model;
    
    requires pendenzenliste.messaging.main;
    requires pendenzenliste.util.domain.main;

    uses EventBus;
}