import pendenzenliste.messaging.EventBus;
import pendenzenliste.messaging.Subscriber;
import pendenzenliste.messaging.inmemory.InMemoryEventBus;

module pendenzenliste.messaging.inmemory.main {
    exports pendenzenliste.messaging.inmemory;
    requires pendenzenliste.messaging.main;

    uses EventBus;
    uses Subscriber;

    provides EventBus with InMemoryEventBus;
}