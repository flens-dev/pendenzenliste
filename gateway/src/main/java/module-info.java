import pendenzenliste.achievements.gateway.AchievementEventTrackingSubscriber;
import pendenzenliste.achievements.gateway.AchievementGatewayProvider;
import pendenzenliste.achievements.gateway.ToDoEventAchievementTrackingSubscriber;
import pendenzenliste.messaging.Subscriber;
import pendenzenliste.todos.gateway.ToDoGatewayProvider;

module pendenzenliste.gateway.main {
  requires pendenzenliste.domain;
  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.supporting.domain.achievements.main;
  requires pendenzenliste.messaging.main;

  exports pendenzenliste.todos.gateway;
  exports pendenzenliste.achievements.gateway;

  uses AchievementGatewayProvider;
  uses ToDoGatewayProvider;

  provides Subscriber
      with ToDoEventAchievementTrackingSubscriber, AchievementEventTrackingSubscriber;
}