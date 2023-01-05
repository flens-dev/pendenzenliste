package archunit;

import java.io.Serializable;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(
    packages = "pendenzenliste.domain",
    importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class}
)
public class SerializationTest
{
  @ArchTest
  public void valueObjectsShouldBeSerializable(final JavaClasses javaClasses)
  {
    classes().that()
        .haveNameMatching("(.*)ValueObject")
        .should()
        .implement(Serializable.class)
        .because("they may be stored with java serialization")
        .check(javaClasses);
  }

  @ArchTest
  public void entityShouldBeSerializable(final JavaClasses javaClasses)
  {
    classes().that()
        .haveNameMatching("(.*)Entity")
        .and()
        .areNotInterfaces()
        .should()
        .implement(Serializable.class)
        .because("they may be stored with java serialization")
        .check(javaClasses);
  }
}
