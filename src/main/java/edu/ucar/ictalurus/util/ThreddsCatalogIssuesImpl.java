package edu.ucar.ictalurus.util;

import edu.ucar.ictalurus.ThreddsCatalogIssueContainer;
import edu.ucar.ictalurus.builder.BuilderIssue;
import edu.ucar.ictalurus.builder.BuilderIssues;

/**
 * _more_
 *
 * @author edavis
 */
public final class ThreddsCatalogIssuesImpl implements ThreddsCatalogIssueContainer
{
  private final boolean isValid;

  private final int numWarnIssues;
  private final int numErrorIssues;
  private final int numFatalIssues;

  private final String warnMessages;
  private final String errorMessages;
  private final String fatalMessages;
  private final String allMessages;

  public ThreddsCatalogIssuesImpl( BuilderIssues issues ) {
    if ( issues == null || issues.isEmpty() ) {
      this.isValid = true;
      this.numWarnIssues = 0;
      this.numErrorIssues = 0;
      this.numFatalIssues = 0;
      this.warnMessages = "";
      this.errorMessages = "";
      this.fatalMessages = "";
      this.allMessages = "";
      return;
    }

    this.isValid = issues.isValid();
    this.numWarnIssues = issues.getNumWarningIssues();
    this.numErrorIssues = issues.getNumErrorIssues();
    this.numFatalIssues = issues.getNumFatalIssues();
    StringBuilder warnMessagesBuilder = new StringBuilder();
    StringBuilder errorMessagesBuilder = new StringBuilder();
    StringBuilder fatalMessagesBuilder = new StringBuilder();
    StringBuilder allMessagesBuilder = new StringBuilder();

    for ( BuilderIssue issue : issues.getIssues() )
    {
      String msg = String.format( "%s%n", issue.toString());
      if ( issue.getSeverity() == BuilderIssue.Severity.WARNING ) {
        warnMessagesBuilder.append( msg );
        allMessagesBuilder.append( msg );
      }
      else if ( issue.getSeverity() == BuilderIssue.Severity.ERROR ) {
        errorMessagesBuilder.append( msg );
        allMessagesBuilder.append( msg );
      }
      else if ( issue.getSeverity() == BuilderIssue.Severity.FATAL ) {
        fatalMessagesBuilder.append( msg );
        allMessagesBuilder.append( msg );
      }
      else
        allMessagesBuilder.append( msg );
    }
    this.warnMessages = warnMessagesBuilder.toString();
    this.errorMessages = errorMessagesBuilder.toString();
    this.fatalMessages = fatalMessagesBuilder.toString();
    this.allMessages = allMessagesBuilder.toString();
  }

  @Override
  public boolean isValid() {
    return isValid;
  }

  @Override
  public int getNumFatalIssues() {
    return this.numFatalIssues;
  }

  @Override
  public int getNumErrorIssues() {
    return this.numErrorIssues;
  }

  @Override
  public int getNumWarningIssues() {
    return this.numWarnIssues;
  }

  @Override
  public String getIssuesMessage() {
    return this.allMessages;
  }

  @Override
  public String getFatalIssuesMessage() {
    return this.fatalMessages;
  }

  @Override
  public String getErrorIssuesMessage() {
    return this.errorMessages;
  }

  @Override
  public String getWarningIssuesMessage() {
    return warnMessages;
  }
}
