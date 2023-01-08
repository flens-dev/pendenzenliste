import pendenzenliste.gateway.AchievementGatewayProvider;
import pendenzenliste.gateway.AchievementTrackingSubscriber;
import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.messaging.Subscriber;

module pendenzenliste.gateway.main {
  requires pendenzenliste.domain;
  requires pendenzenliste.core.domain.todos.main;
  requires pendenzenliste.supporting.domain.achievements.main;
  requires pendenzenliste.messaging.main;

  exports pendenzenliste.gateway;

  uses AchievementGatewayProvider;
  uses ToDoGatewayProvider;

  provides Subscriber with AchievementTrackingSubscriber;
}