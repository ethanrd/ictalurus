package edu.ucar.ictalurus.builder;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * _more_
 *
 * @author edavis
 */
public class BuilderIssuesTest
{
  static class MyThreddsBuilder implements ThreddsBuilder {
    MyThreddsBuilder() {}
    @Override
    public Buildable isBuildable() {
      return Buildable.YES;
    }

    @Override
    public BuilderIssues checkForIssues() {
      return new BuilderIssues();
    }

    @Override
    public Object build() throws IllegalStateException {
      return null;
    }
  }

  @Test
  public void checkValidUntilOneOrMoreFatalIssues() {
    BuilderIssues builderIssues = new BuilderIssues();
    assertTrue( "Unexpectedly not empty.", builderIssues.isEmpty());
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.WARNING, "warn", new MyThreddsBuilder()));
    assertTrue("Unexpectedly not valid.", builderIssues.isValid());
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.ERROR, "error", new MyThreddsBuilder()));
    assertTrue("Unexpectedly not valid.", builderIssues.isValid());
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.FATAL, "fatal", new MyThreddsBuilder()));
    assertFalse("Unexpectedly valid.", builderIssues.isValid());

    builderIssues.clear();
    assertTrue("Unexpectedly not empty.", builderIssues.isEmpty());
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.WARNING, "warn1", new MyThreddsBuilder()));
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.WARNING, "warn2", new MyThreddsBuilder()));
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.WARNING, "warn3", new MyThreddsBuilder()));
    assertTrue("Unexpectedly not valid.", builderIssues.isValid());
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.ERROR, "error1", new MyThreddsBuilder()));
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.ERROR, "error2", new MyThreddsBuilder()));
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.ERROR, "error3", new MyThreddsBuilder()));
    assertTrue("Unexpectedly not valid.", builderIssues.isValid());
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.FATAL, "fatal1", new MyThreddsBuilder()));
    builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.FATAL, "fatal2", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal3", new MyThreddsBuilder()));
    assertFalse( "Unexpectedly valid.", builderIssues.isValid());
  }

  @Test
  public void checkSeverityCount() {
    BuilderIssues builderIssues = new BuilderIssues();
    assertTrue( "Unexpectedly not empty.", builderIssues.isEmpty());
    assertSeverityAsExpected( builderIssues, 0, 0, 0);

    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn1", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn2", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error1", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error2", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error3", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal1", new MyThreddsBuilder()));
    assertSeverityAsExpected( builderIssues, 2, 3, 1);

    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn3", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn4", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn5", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn6", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error4", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error5", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error6", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error7", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error8", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal2", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal3", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal4", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal5", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal6", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal7", new MyThreddsBuilder()));
    assertSeverityAsExpected( builderIssues, 6, 8, 7);

    builderIssues.clear();
    assertTrue( "Unexpectedly not empty.", builderIssues.isEmpty());
    assertSeverityAsExpected( builderIssues, 0, 0, 0);

    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn1", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "warn2", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error1", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error2", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "error3", new MyThreddsBuilder()));
    builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.FATAL, "fatal1", new MyThreddsBuilder()));
    assertSeverityAsExpected( builderIssues, 2, 3, 1);
  }

  private void assertSeverityAsExpected( BuilderIssues builderIssues,
                                         int expectedNumWarn, int expectedNumError, int exectedNumFatal) {
    int actualNumWarn = builderIssues.getNumWarningIssues();
    int actualNumError = builderIssues.getNumErrorIssues();
    int actualNumFatal = builderIssues.getNumFatalIssues();
    assertTrue( String.format( "Number of Warn|Error|Fatal issues [%d|%d|%d] not as expected [%d|%d|%d].",
        actualNumWarn, actualNumError, actualNumFatal, expectedNumWarn, expectedNumError, exectedNumFatal),
        actualNumWarn == expectedNumWarn && actualNumError == expectedNumError && actualNumFatal == exectedNumFatal);
  }
}
