package pendenzenliste.domain.achievements;

public record ProgressEntity(IdentityValueObject identity,
                             Integer currentValue,
                             Integer targetValue)
{
}
