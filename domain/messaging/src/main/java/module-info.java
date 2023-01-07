import pendenzenliste.messaging.EventBus;
import pendenzenliste.messaging.InMemoryEventBus;

module pendenzenliste.messaging.main {
  exports pendenzenliste.messaging;

  uses EventBus;

  provides EventBus with InMemoryEventBus;
}