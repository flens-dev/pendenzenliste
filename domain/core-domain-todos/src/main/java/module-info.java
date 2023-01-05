import pendenzenliste.domain.todos.MultiThreadedToDoEventPublisher;
import pendenzenliste.domain.todos.ToDoEventPublisher;
import pendenzenliste.domain.todos.ToDoEventSubscriptionTopic;

module pendenzenliste.core.domain.todos.main {
  exports pendenzenliste.domain.todos;

  uses ToDoEventSubscriptionTopic;
  provides ToDoEventSubscriptionTopic with MultiThreadedToDoEventPublisher;

  uses ToDoEventPublisher;
  provides ToDoEventPublisher with MultiThreadedToDoEventPublisher;
}