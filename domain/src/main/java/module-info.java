import pendenzenliste.domain.MultiThreadedToDoEventPublisher;
import pendenzenliste.domain.ToDoEventPublisher;
import pendenzenliste.domain.ToDoEventSubscriptionTopic;

module pendenzenliste.domain.main {
  exports pendenzenliste.domain;

  uses pendenzenliste.domain.ToDoEventSubscriptionTopic;
  provides ToDoEventSubscriptionTopic with MultiThreadedToDoEventPublisher;

  uses ToDoEventPublisher;
  provides ToDoEventPublisher with MultiThreadedToDoEventPublisher;
}