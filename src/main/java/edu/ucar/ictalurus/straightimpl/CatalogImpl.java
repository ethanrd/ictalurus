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

import ucar.nc2.units.DateType;
import edu.ucar.ictalurus.Catalog;
import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.ThreddsCatalogIssueContainer;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.util.PropertyBuilderContainer;
import edu.ucar.ictalurus.util.ThreddsCatalogIssuesImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * _more_
 *
 * @author edavis
 * @since 4.0
 */
class CatalogImpl implements Catalog
{
  private final String name;
  private final URI docBaseUri;
  private final String version;
  private final DateType expires; // DateType instances are mutable
  private final DateType lastModified;

//  private final ServiceContainer serviceContainer;
//  private final CatalogWideServiceTracker catalogWideServiceTracker;
//
//  //private final DatasetNodeContainer datasetNodeContainer;

  private final PropertyBuilderContainer propertyBuilderContainer;

  private final ThreddsCatalogIssueContainer threddsCatalogIssueContainer;


  CatalogImpl( String name, String docBaseUri, String version, DateType expires, DateType lastModified,
               PropertyBuilderContainer propertyBuilderContainer, //ServiceBuilderContainer serviceBuilderContainer,
//               CatalogWideServiceBuilderTracker catalogWideServiceBuilderTracker,
               BuilderIssues builderIssues
  )
  {
//    if ( serviceBuilderContainer.isBuildable() != ThreddsBuilder.Buildable.YES )
//      throw new IllegalArgumentException( "Failed to build Catalog, ServiceBuilderContainer is not buildable.");
//    if ( catalogWideServiceBuilderTracker.isBuildable() != ThreddsBuilder.Buildable.YES)
//      throw new IllegalArgumentException( "Failed to build Catalog, CatalogWideServiceBuilderTracker is not buildable.");
//    if ( datasetNodeBuilderContainer.isBuildable() != ThreddsBuilder.Buildable.YES )
//      throw new IllegalArgumentException( "Failed to build Catalog, DatasetNodeBuilderContainer is not buildable.");


    this.name = name == null ? "" : name;
    try {
      this.docBaseUri = new URI( docBaseUri == null ? "" : docBaseUri);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException( String.format( "Failed to build Catalog, document base URI [%s] must be valid URI.", docBaseUri));
    }
    this.version = version == null ? "" : version;
    this.expires = expires;
    this.lastModified = lastModified;

    this.propertyBuilderContainer = propertyBuilderContainer;
//    this.serviceContainer = serviceBuilderContainer.build();
//    this.catalogWideServiceTracker = catalogWideServiceBuilderTracker.build();

    //this.datasetNodeContainer = datasetNodeBuilderContainer.build();

    this.threddsCatalogIssueContainer = new ThreddsCatalogIssuesImpl( builderIssues);
  }

//  DatasetNodeContainer getDatasetNodeContainer() {
//    return this.datasetNodeContainer;
//  }

  public String getName() {
    return this.name;
  }

  public URI getDocBaseUri() {
    return this.docBaseUri;
  }

  public String getVersion() {
    return this.version;
  }

  public DateType getExpires() {
    return this.expires;
  }

  public DateType getLastModified() {
    return this.lastModified;
  }

//  public List<Service> getServices() {
//    return this.serviceContainer.getServices();
//  }
//
//  public Service getServiceByName( String name ) {
//    return this.serviceContainer.getServiceByName( name );
//  }
//
//  public Service findServiceByNameGlobally( String name ) {
//    return this.catalogWideServiceTracker.getServiceByGloballyUniqueName( name );
//  }

  @Override
  public List<Property> getProperties() {
    return this.propertyBuilderContainer.getProperties();
  }

  @Override
  public List<String> getPropertyNames() {
    return this.propertyBuilderContainer.getPropertyNames();
  }

  @Override
  public List<Property> getProperties( String name ) {
    return null;
  }

  @Override
  public Property getProperty( String name ) {
    return this.propertyBuilderContainer.getProperty( name );
  }

//  public List<DatasetNode> getDatasets() {
//    return null; //this.datasetNodeContainer.getDatasets();
//  }
//
//  public DatasetNode getDatasetById( String id ) {
//    return null; //this.datasetNodeContainer.getDatasetById( id );
//  }
//
//  public DatasetNode findDatasetByIdGlobally( String id ) {
//    return null; //this.datasetNodeContainer.getDatasetNodeByGloballyUniqueId( id );
//  }

  @Override
  public ThreddsCatalogIssueContainer getCatalogIssues() {
    return this.threddsCatalogIssueContainer;
  }
}
