module pendenzenliste.gateway.postgresql.main {
    requires pendenzenliste.core.domain.todos.main;
    requires pendenzenliste.supporting.domain.achievements.main;
    requires pendenzenliste.gateway.main;
    requires pendenzenliste.messaging.main;
    requires org.jooq;

    /**
     * provides ToDoGatewayProvider
     * with RedisToDoGatewayProvider;
     * <p>
     * provides AchievementGatewayProvider
     * with RedisAchievementGatewayProvider;
     **/
}