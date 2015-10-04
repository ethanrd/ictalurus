package edu.ucar.ictalurus.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class for ThreddsBuilder classes and their handling of collections of BuilderIssue-s.
 *
 * @author edavis
 */
public class BuilderIssues {
  private List<BuilderIssue> issues;
  private int numFatalIssues = 0;
  private int numErrorIssues = 0;
  private int numWarningIssues = 0;


  private void createIssueListIfNeeded() {
    if ( this.issues == null )
      this.issues = new ArrayList<BuilderIssue>();
  }

  public BuilderIssues() {}

  public BuilderIssues( BuilderIssue issue ) {
    if ( issue == null )
      return;
    this.issues = new ArrayList<BuilderIssue>();
    this.issues.add( issue );
  }

  public void addIssue( BuilderIssue.Severity severity, String msg, ThreddsBuilder builder ) {
    this.addIssue( new BuilderIssue( severity, msg, builder));
  }

  public void addIssue( BuilderIssue issue ) {
    if ( issue == null )
      return;
    this.createIssueListIfNeeded();
    this.issues.add( issue );
    trackSeverity( issue.getSeverity() );
  }

  public void addAllIssues( BuilderIssues issues ) {
    if ( issues == null || issues.isEmpty() )
      return;

    this.createIssueListIfNeeded();
    for ( BuilderIssue curIssue : issues.getIssues() ) {
      this.issues.add( curIssue);
      trackSeverity( curIssue.getSeverity() );
    }
  }

  public void clear() {
    if ( this.issues != null )
      this.issues.clear();
    this.numWarningIssues = 0;
    this.numErrorIssues = 0;
    this.numFatalIssues = 0;
  }

  public boolean isEmpty() {
    if ( this.issues == null )
      return true;
    return this.issues.isEmpty();
  }

  public int size() {
    if ( this.issues == null )
      return 0;
    return this.issues.size();
  }

  public int getNumWarningIssues() {
    return this.numWarningIssues;
  }

  public int getNumErrorIssues() {
    return this.numErrorIssues;
  }

  public int getNumFatalIssues() {
    return this.numFatalIssues;
  }

  public List<BuilderIssue> getIssues() {
    if ( this.issues == null || this.issues.isEmpty() )
      return Collections.emptyList();
    return Collections.unmodifiableList( this.issues );
  }

  public boolean isValid() {
    return this.numFatalIssues <= 0;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    if ( this.issues == null || this.issues.isEmpty() )
      sb.append( "No Issues.\n");
    else
      for ( BuilderIssue bfi : this.issues )
        sb.append( bfi.toString());
    return sb.toString();
  }

  private void trackSeverity( BuilderIssue.Severity severity )
  {
    if ( severity.equals( BuilderIssue.Severity.FATAL ) )
      this.numFatalIssues++;
    else if ( severity.equals( BuilderIssue.Severity.ERROR ) )
      this.numErrorIssues++;
    else if ( severity.equals( BuilderIssue.Severity.WARNING ) )
      this.numWarningIssues++;
  }

}
