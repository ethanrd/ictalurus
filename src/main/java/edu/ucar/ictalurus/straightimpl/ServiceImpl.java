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
import edu.ucar.ictalurus.ThreddsCatalogIssueContainer;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.ThreddsBuilder;
import edu.ucar.ictalurus.util.PropertyBuilderContainer;
import edu.ucar.ictalurus.util.ThreddsCatalogIssuesImpl;

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
final class ServiceImpl implements Service
{
  private final String name;
  private final String description;
  private final ServiceType type;
  private final URI baseUri;
  private final String suffix;

  private final PropertyBuilderContainer propertyBuilderContainer;

  private final ServiceContainer serviceContainer;

//  private final CatalogWideServiceTracker catalogWideServiceTracker;
//  private final boolean isRootContainer;

  private final ThreddsCatalogIssueContainer threddsCatalogIssueContainer;

  /**
   * @throws IllegalArgumentException
   * @param name
   * @param description
   * @param type
   * @param baseUriAsString
   * @param suffix
   * @param propertyBuilderContainer
//   * @param serviceBuilderContainer
//   * @param catalogWideServiceBuilderTracker
   * @param isRootContainer
   * @param builderIssues
   */
  ServiceImpl( String name, String description, ServiceType type, String baseUriAsString, String suffix,
               PropertyBuilderContainer propertyBuilderContainer,
               ServiceBuilderContainer serviceBuilderContainer,
//               CatalogWideServiceBuilderTracker catalogWideServiceBuilderTracker,
               boolean isRootContainer,
               BuilderIssues builderIssues ) {
    if ( name == null || name.isEmpty() )
      throw new IllegalArgumentException( "Name must not be null or empty.");
    if ( type == null )
      throw new IllegalArgumentException( "Service type must not be null.");
    if ( propertyBuilderContainer != null && propertyBuilderContainer.isBuildable() != ThreddsBuilder.Buildable.YES )
      throw new IllegalArgumentException( "ServiceBuilder can't be built when PropertyBuilderContainer is not buildable.");
    if ( serviceBuilderContainer.isBuildable() != ThreddsBuilder.Buildable.YES )
      throw new IllegalArgumentException( "ServiceBuilder can't be built when ServiceBuilderContainer is not buildable.");
//    if ( catalogWideServiceBuilderTracker.isBuildable() != ThreddsBuilder.Buildable.YES)
//      throw new IllegalArgumentException( "ServiceBuilder can't be built when CatalogWideServiceBuilderTracker is not buildable.");

    this.name = name;
    this.description = description == null ? "" : description;
    this.type = type;
    try {
      this.baseUri = new URI( baseUriAsString == null ? "" : baseUriAsString );
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException( "ServiceBuilder can't be built when baseUri not a valid URI.");
    }
    this.suffix = suffix == null ? "" : suffix;
    this.propertyBuilderContainer = propertyBuilderContainer;
    this.serviceContainer = serviceBuilderContainer != null ? serviceBuilderContainer.build() : null;
//    this.isRootContainer = isRootContainer;
//    if ( this.isRootContainer )
//      this.catalogWideServiceTracker = catalogWideServiceBuilderTracker.build();
//    else
//      this.catalogWideServiceTracker = null;
    this.threddsCatalogIssueContainer = new ThreddsCatalogIssuesImpl( builderIssues);
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public ServiceType getType() {
    return this.type;
  }

  public URI getBaseUri() {
    return this.baseUri;
  }

  public String getSuffix() {
    return this.suffix;
  }

  public List<Property> getProperties() {
    if ( this.propertyBuilderContainer == null )   {
      return Collections.emptyList();
    }
    return this.propertyBuilderContainer.getProperties();
  }

  public List<String> getPropertyNames() {
    if ( this.propertyBuilderContainer == null )   {
      return Collections.emptyList();
    }
    return this.propertyBuilderContainer.getPropertyNames();
  }

  public Property getProperty(String name) {
    if ( this.propertyBuilderContainer == null )   {
      return null;
    }
    return this.propertyBuilderContainer.getProperty( name );
  }

  public List<Property> getProperties( String name ) {
    if ( this.propertyBuilderContainer == null )   {
      return Collections.emptyList();
    }
    return this.propertyBuilderContainer.getProperties( name );
  }

  public List<Service> getServices() {
    return this.serviceContainer.getServices();
  }

//  public Service getService(String name) {
//    return this.serviceContainer.getServiceByName( name );
//  }

  public Service findReferencableServiceByName( String name ) {
//    return this.catalogWideServiceTracker.getServiceByGloballyUniqueName( name );
    return null;
  }


  @Override
  public ThreddsCatalogIssueContainer getCatalogIssues() {
    return this.threddsCatalogIssueContainer;
  }
}
