package edu.ucar.ictalurus.straightimpl;

import edu.ucar.ictalurus.ThreddsCatalogIssueContainer;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.ServiceBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilder;
import edu.ucar.ictalurus.util.ThreddsCatalogIssuesImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class for those ThreddsBuilder classes that have child ServiceBuilder classes:
 * CatalogBuilder and ServiceBuilder.
 *
 * @author edavis
 */
public class ServiceBuilderContainer // implements ThreddsBuilder
{
  /** List of all contained Service-s */
  private List<ServiceBuilder> serviceBuilderList;

  private final CatalogWideServiceBuilderTracker catalogWideServiceBuilderTracker;

  private boolean isRootServiceContainer;

  private ThreddsBuilder.Buildable isBuildable;
  private BuilderIssues builderIssues;

  public ServiceBuilderContainer() {
    this( new CatalogWideServiceBuilderTracker());
    this.isRootServiceContainer = true;
  }

  /**
   *
   * @param catalogWideServiceBuilderTracker
   */
  ServiceBuilderContainer( CatalogWideServiceBuilderTracker catalogWideServiceBuilderTracker )
  {
    if ( catalogWideServiceBuilderTracker == null )
      throw new IllegalArgumentException( "CatalogWideServiceBuilderTracker may not be null." );
    this.catalogWideServiceBuilderTracker = catalogWideServiceBuilderTracker;

    this.serviceBuilderList = null;
    this.isRootServiceContainer = false;

    this.isBuildable = ThreddsBuilder.Buildable.YES;
  }

//  ServiceBuilder getServiceByGloballyUniqueName( String name ) {
//    return this.catalogWideServiceBuilderTracker.getReferenceableService(name);
//  }

  /**
   * Add a ServiceBuilder to this container.
   *
   * @param serviceBuilder the ServiceBuilder to add to this container.
   */
  public void addService( ServiceBuilder serviceBuilder ) {
    if ( this.serviceBuilderList == null ) {
      this.serviceBuilderList = new ArrayList<ServiceBuilder>();
    }
    this.serviceBuilderList.add( serviceBuilder );

    this.catalogWideServiceBuilderTracker.addService( serviceBuilder );

    this.isBuildable = ThreddsBuilder.Buildable.DONT_KNOW;
  }

  /**
   * Remove the given Service from this container.
   *
   * @param serviceBuilder the Service to be removed.
   * @return true if a Service was removed, otherwise false.
   */
  public boolean removeService( ServiceBuilder serviceBuilder )
  {
    if ( serviceBuilder == null || this.serviceBuilderList == null
        || this.serviceBuilderList.isEmpty() )
      return false;

    if ( ! this.serviceBuilderList.remove( serviceBuilder ) ) {
      return false;
    }
    boolean success = this.catalogWideServiceBuilderTracker.removeService( serviceBuilder );
    assert success;
    if ( this.isBuildable == ThreddsBuilder.Buildable.NO)
      this.isBuildable = ThreddsBuilder.Buildable.DONT_KNOW;

    return true;
  }

  /**
   * Return a list of all contained Service-s.
   * @return a list of all contained Service-s.
   */
  public List<ServiceBuilder> getServices() {
    if ( this.serviceBuilderList == null || this.serviceBuilderList.isEmpty() )
      return Collections.emptyList();

    return Collections.unmodifiableList( this.serviceBuilderList );
  }

  public ServiceBuilder findReferencableServiceBuilderByName( String serviceName) {
    return this.catalogWideServiceBuilderTracker.getReferenceableService( serviceName );
  }

  boolean isEmpty() {
    if ( this.serviceBuilderList == null )
      return true;
    return this.serviceBuilderList.isEmpty();
  }

  int size() {
    if ( this.serviceBuilderList == null )
      return 0;
    return this.serviceBuilderList.size();
  }

  public ThreddsCatalogIssueContainer getCatalogIssues() {
    if ( this.isBuildable != ThreddsBuilder.Buildable.YES) {
      this.checkForIssues();
    }
    return new ThreddsCatalogIssuesImpl( this.builderIssues );
  }

  public BuilderIssues checkForIssues()
  {
    builderIssues = new BuilderIssues();
    if ( this.serviceBuilderList != null ) {
      for ( ServiceBuilder curServiceBuilder : this.serviceBuilderList ) {
        builderIssues.addAllIssues( curServiceBuilder.checkForIssues() );
      }

      if ( this.isRootServiceContainer)
        builderIssues.addAllIssues( this.catalogWideServiceBuilderTracker.checkForIssues() );
    }
    if ( builderIssues.isValid()) {
      this.isBuildable = ThreddsBuilder.Buildable.YES;
    } else {
      this.isBuildable = ThreddsBuilder.Buildable.NO;
    }
    return builderIssues;
  }

  public ThreddsBuilder.Buildable isBuildable() {
    return this.isBuildable;
  }
  /**
   * Build the ServiceContainer
   *
   * @throws IllegalStateException if any of the contained services are not in a valid state.
   */
  public ServiceContainer build() throws IllegalStateException
  {
    if ( this.isBuildable != ThreddsBuilder.Buildable.YES )
      throw new IllegalStateException( "ServiceBuilderContainer is not in buildable state.");

    if ( this.isRootServiceContainer)
      return new ServiceContainer( this.serviceBuilderList, this.catalogWideServiceBuilderTracker );
    else
      return new ServiceContainer( this.serviceBuilderList );
  }
}
