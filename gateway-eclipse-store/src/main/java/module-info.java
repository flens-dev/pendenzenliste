module pendenzenliste.gateway.eclipse.store.main {
    requires pendenzenliste.supporting.domain.achievements.main;
    requires pendenzenliste.core.domain.todos.main;
    requires pendenzenliste.gateway.main;
    requires pendenzenliste.messaging.main;
    requires org.eclipse.store.storage;
    requires org.eclipse.store.storage.embedded;
}