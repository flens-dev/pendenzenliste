import pendenzenliste.messaging.EventBus;
import pendenzenliste.messaging.Subscriber;

module pendenzenliste.messaging.main {
  exports pendenzenliste.messaging;

  uses EventBus;
  uses Subscriber;
}