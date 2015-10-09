/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package edu.ucar.ictalurus.straightimpl;

import edu.ucar.ictalurus.ServiceType;
import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.Service;
import edu.ucar.ictalurus.builder.BuilderIssue;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.ServiceBuilder;
import edu.ucar.ictalurus.util.PropertyBuilderContainer;
//import edu.ucar.ictalurus.straightimpl.PropertyBuilderContainer;
//import edu.ucar.ictalurus.straightimpl.ServiceBuilderContainer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * _more_
 *
 * @author edavis
 * @since 4.0
 */
class ServiceBuilderImpl implements ServiceBuilder
{
  private String name;
  private String description;
  private ServiceType type;
  private String baseUri;
  private String suffix;

  private PropertyBuilderContainer propertyBuilderContainer;

  private ServiceBuilderContainer serviceBuilderContainer;

//  /**
//   * Generally, a CatalogBuilder will create the CatalogWideServiceBuilderTracker
//   * and pass it to ServiceBuilders to use. However, if a ServiceBuilder exists
//   * without being contained by a CatalogBuilder, it might need to create its own
//   * CatalogWideServiceBuilderTracker (in which case CatalogWide can be thought
//   * of as DocumentWide).
//   */
//  private CatalogWideServiceBuilderTracker catalogWideServiceBuilderTracker;

  /**
   * When isRootServiceContainer is true, it indicates that this ServiceBuilder is not
   * contained by a CatalogBuilder and it has created a
   * CatalogWideServiceBuilderTracker that it will pass to any contained
   * ServiceBuilders.
   */
  private boolean isRootServiceContainer;

  private BuilderIssues builderIssues;
  private Buildable isBuildable;

  ServiceBuilderImpl( String name, ServiceType type, String baseUri, ServiceBuilderContainer serviceBuilderContainer ) {
    if ( serviceBuilderContainer == null )
      throw new IllegalArgumentException( "ServiceBuilderContainer may not be null, call other constructor if no containing catalog." );
    this.name = name != null ? name : "";
    this.description = "";
    this.type = type != null ? type : ServiceType.NONE;
    this.baseUri = baseUri != null ? baseUri : "";
    this.suffix = "";
    this.propertyBuilderContainer = null;

    this.serviceBuilderContainer = serviceBuilderContainer;
    this.isRootServiceContainer = false;

    this.isBuildable = Buildable.DONT_KNOW;
  }

  ServiceBuilderImpl( String name, ServiceType type, String baseUri ) {
    this( name, type, baseUri, new ServiceBuilderContainer());
    this.isRootServiceContainer = true;
  }

  public void initialize() {
//    this.initialize( null );
  }

//  public void initialize( CatalogWideServiceBuilderTracker catalogWideServiceBuilderTracker ) {
//    if ( this.catalogWideServiceBuilderTracker != null )
//      throw new IllegalStateException( "CatalogWideServiceBuilderTracker already set." );
//
//    if ( catalogWideServiceBuilderTracker == null ) {
//      this.isRootServiceContainer = true;
//      this.catalogWideServiceBuilderTracker = new CatalogWideServiceBuilderTracker();
//    } else {
//      this.isRootServiceContainer = false;
//      this.catalogWideServiceBuilderTracker = catalogWideServiceBuilderTracker;
//    }
//
//    this.serviceBuilderContainer = new ServiceBuilderContainer( this.catalogWideServiceBuilderTracker);
//  }

  public void setName( String name ) {
    this.isBuildable = Buildable.DONT_KNOW;
    this.name = name != null ? name : "";
  }

  public String getName() {
    return this.name;
  }

  public void setDescription( String description ) {
    this.isBuildable = Buildable.DONT_KNOW;
    this.description = description != null ? description : "";
  }

  public String getDescription() {
    return this.description;
  }

  public void setType( ServiceType type ) {
    this.isBuildable = Buildable.DONT_KNOW;
    this.type = type != null ? type : ServiceType.NONE;
  }

  public ServiceType getType() {
    return this.type;
  }

  // ToDo Test that an empty baseUri is OK for a "Compound" service.
  public void setBaseUriAsString( String baseUriAsString ) {
    this.baseUri = baseUriAsString != null ? baseUriAsString : "";
    this.isBuildable = Buildable.DONT_KNOW;
  }

  public String getBaseUriAsString() {
    return this.baseUri;
  }

  public void setSuffix( String suffix ) {
    this.isBuildable = Buildable.DONT_KNOW;
    this.suffix = suffix != null ? suffix : "";
  }

  public String getSuffix() {
    return this.suffix;
  }

  public void addProperty( String name, String value ) {
    this.isBuildable = Buildable.DONT_KNOW;
    if ( this.propertyBuilderContainer == null ) {
      this.propertyBuilderContainer = new PropertyBuilderContainer();
      this.propertyBuilderContainer.setContainingBuilder( this );
    }
    this.propertyBuilderContainer.addProperty(name, value);
  }

  public boolean removeProperty( Property property ) {
    if ( this.propertyBuilderContainer == null )
      return false;
    this.isBuildable = Buildable.DONT_KNOW;
    return this.propertyBuilderContainer.removeProperty( property );
  }

  public List<Property> getProperties() {
    if ( this.propertyBuilderContainer == null )
      return Collections.emptyList();
    return this.propertyBuilderContainer.getProperties();
  }

  public List<String> getPropertyNames() {
    if ( this.propertyBuilderContainer == null )
      return Collections.emptyList();
    return this.propertyBuilderContainer.getPropertyNames();
  }

  public List<Property> getProperties( String name ) {
    if ( this.propertyBuilderContainer == null )
      return Collections.emptyList();
    return this.propertyBuilderContainer.getProperties( name );
  }

  public Property getProperty(String name) {
    if ( this.propertyBuilderContainer == null )
      return null;
    return this.propertyBuilderContainer.getProperty(name);
  }

//  public ServiceBuilder addService( String name, ServiceType type, String baseUri ) {
//    if ( this.serviceBuilderContainer == null )
//      this.initialize();
//
//    this.isBuildable = Buildable.DONT_KNOW;
//
//    ServiceBuilderImpl serviceBuilder = new ServiceBuilderImpl( name, type, baseUri );
//    this.serviceBuilderContainer.addService( serviceBuilder );
//    return serviceBuilder;
//  }
//
//  public boolean removeService( ServiceBuilder serviceBuilder ) {
//    if ( serviceBuilder == null )
//      return false;
//
//    if ( this.serviceBuilderContainer == null )
//      this.initialize();
//
//    this.isBuildable = Buildable.DONT_KNOW;
//    return this.serviceBuilderContainer.removeService( serviceBuilder );
//  }
//
//  public List<ServiceBuilder> getServiceBuilders() {
//    if ( this.serviceBuilderContainer == null )
//      this.initialize();
//
//    return this.serviceBuilderContainer.getServices();
//  }

//  public ServiceBuilder getServiceBuilderByName( String name ) {
//    if ( this.serviceBuilderContainer == null )
//      this.initialize();
//
//    return this.serviceBuilderContainer.getServiceBuilderByName( name );
//  }

  public ServiceBuilder findReferencableServiceBuilderByName( String name ) {
//    if ( this.catalogWideServiceBuilderTracker == null )
//      this.initialize();
//
//    return this.catalogWideServiceBuilderTracker.getReferenceableService( name );
    return null;
  }

  public Buildable isBuildable() {
    return this.isBuildable;
  }

  /**
   * Check whether the state of this ServiceBuilder is such that build() will succeed.
   *
   * @return true if this ServiceBuilder is in a state where build() will succeed.
   */
  public BuilderIssues checkForIssues() {
//    if ( catalogWideServiceBuilderTracker == null )
//      this.initialize();

    builderIssues = new BuilderIssues();

    if ( this.name == null )
      builderIssues.addIssue( BuilderIssue.Severity.ERROR, "Service name may not be null.", this);
    if ( this.type == null )
      builderIssues.addIssue( BuilderIssue.Severity.ERROR, "Service type must not be null.", this);
    if ( baseUri == null )
      builderIssues.addIssue( BuilderIssue.Severity.ERROR, "Base URI must not be null.", this);

    // Check that the baseUri is a valid URI.
    if ( ! this.baseUri.isEmpty()) {
      try {
        new URI( this.baseUri);
      } catch (URISyntaxException e) {
        builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "The baseUri [" + this.baseUri + "] of this Service [" + this.name + "] must be a valid URI.", this));
      }
    }

    // Check subordinates.
//    builderIssues.addAllIssues( this.serviceBuilderContainer.checkForIssues());
    if ( this.propertyBuilderContainer != null )
      builderIssues.addAllIssues( this.propertyBuilderContainer.checkForIssues());
//    if ( this.isRootServiceContainer )
//      builderIssues.addAllIssues( this.catalogWideServiceBuilderTracker.checkForIssues());

    // Various checks on this service itself.
    if ( this.getType() == ServiceType.COMPOUND ) {
      // Compound services should contain nested services.
//      if ( this.serviceBuilderContainer.isEmpty()) {
//        builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "No contained services in this compound service.", this));
//      }
      // Compound services should not have a baseURI.
      if ( this.getBaseUriAsString() != null && ! this.getBaseUriAsString().isEmpty() )
        builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "This compound service has a baseURI.", this));
    } else {
      // Non-compound services should have a baseUri.
      if ( this.baseUri == null || this.baseUri == "" )
        builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, "Non-compound services must have base URI.", this ));

      // Non-compound services MAY NOT contain nested services
//      if ( ! this.serviceBuilderContainer.isEmpty() )
//        builderIssues.addIssue(new BuilderIssue(BuilderIssue.Severity.ERROR, "Non-compound services may not contian other services.", this ));
    }

    if ( builderIssues.isValid())
      this.isBuildable = Buildable.YES;
    else
      this.isBuildable = Buildable.NO;

    return builderIssues;
  }

  /**
   * Generate the Service being built by this ServiceBuilder.
   *
   * @return the Service
   * @throws IllegalStateException if this ServiceBuilder is not in a valid state.
   */
  public Service build() throws IllegalStateException
  {
    if ( this.isBuildable != Buildable.YES )
      throw new IllegalStateException( "ServiceBuilder not buildable.");

    return new ServiceImpl( this.name, this.description, this.type, baseUri, this.suffix,
        this.propertyBuilderContainer,
//        this.serviceBuilderContainer,
//        this.catalogWideServiceBuilderTracker,
        this.isRootServiceContainer, this.builderIssues );
  }
}
