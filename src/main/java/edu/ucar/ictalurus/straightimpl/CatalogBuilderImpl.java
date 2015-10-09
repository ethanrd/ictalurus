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
import edu.ucar.ictalurus.ServiceType;
import edu.ucar.ictalurus.builder.BuilderIssue;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.CatalogBuilder;
import edu.ucar.ictalurus.builder.ServiceBuilder;
import edu.ucar.ictalurus.util.PropertyBuilderContainer;

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
class CatalogBuilderImpl implements CatalogBuilder
{
  private String name;
  private String docBaseUri;
  private String version;
  private DateType expires;
  private DateType lastModified;

  private ServiceBuilderContainer serviceBuilderContainer;
//  private CatalogWideServiceBuilderTracker catalogWideServiceBuilderTracker;
//
//  private DatasetNodeContainer datasetContainer;
//
  private PropertyBuilderContainer propertyBuilderContainer;

  private BuilderIssues builderIssues;
  private Buildable isBuildable;

  CatalogBuilderImpl( String name, String docBaseUri, String version, DateType expires, DateType lastModified ) {
    this.docBaseUri = docBaseUri != null ? docBaseUri : "";
    this. name = name != null ? name : "";
    this.version = version != null ? version : "";
    this.expires = expires != null ? expires : new DateType();
    this.lastModified = lastModified != null ? lastModified : new DateType();

//    this.catalogWideServiceBuilderTracker = new CatalogWideServiceBuilderTracker();
//    this.serviceBuilderContainer = new ServiceBuilderContainer( catalogWideServiceBuilderTracker);
//
//    //this.datasetContainer = new DatasetNodeContainer( null );
    this.propertyBuilderContainer = null;
    this.isBuildable = Buildable.DONT_KNOW;
  }

//  DatasetNodeContainer getDatasetNodeContainer() {
//    return this.datasetContainer;
//  }

  public void setName( String name ) {
    this.name = name;
    this.isBuildable = Buildable.DONT_KNOW;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String getDocBaseUriAsString() {
    return this.docBaseUri;
  }

  public void setDocBaseUri( String docBaseUri ) {
    this.docBaseUri = docBaseUri != null ? docBaseUri : "";
    this.isBuildable = Buildable.DONT_KNOW;
  }

  public void setVersion( String version ) {
    this.version = version;
    this.isBuildable = Buildable.DONT_KNOW;
  }

  public String getVersion() {
    return this.version;
  }

  public void setExpires( DateType expires ) {
    this.expires = expires;
    this.isBuildable = Buildable.DONT_KNOW;
  }

  public DateType getExpires() {
    return this.expires;
  }

  public void setLastModified( DateType lastModified ) {
    this.lastModified = lastModified;
    this.isBuildable = Buildable.DONT_KNOW;
  }

  public DateType getLastModified() {
    return this.lastModified;
  }

  public ServiceBuilder addService( String name, ServiceType type, String baseUri ) {
    if ( this.serviceBuilderContainer == null )
      this.serviceBuilderContainer = new ServiceBuilderContainer();
    this.isBuildable = Buildable.DONT_KNOW;
    ServiceBuilderImpl serviceBuilder = new ServiceBuilderImpl( name, type, baseUri );
    this.serviceBuilderContainer.addService( serviceBuilder );
    return serviceBuilder;
  }

  public boolean removeService( ServiceBuilder serviceBuilder ) {
    if ( serviceBuilder == null )
      return false;

    this.isBuildable = Buildable.DONT_KNOW;
    return this.serviceBuilderContainer.removeService( serviceBuilder );
  }

  public List<ServiceBuilder> getServiceBuilders() {
    return this.serviceBuilderContainer.getServices();
  }

  @Override
  public ServiceBuilder findReferencableServiceBuilderByName( String name ) {
    return this.serviceBuilderContainer.findReferencableServiceBuilderByName( name );
  }

  public void addProperty( String name, String value ) {
    this.isBuildable = Buildable.DONT_KNOW;
    if ( this.propertyBuilderContainer == null ) {
      this.propertyBuilderContainer = new PropertyBuilderContainer();
      this.propertyBuilderContainer.setContainingBuilder( this );

    }
    this.propertyBuilderContainer.addProperty(name, value);
  }

  public boolean removeProperty( Property name ) {
    this.isBuildable = Buildable.DONT_KNOW;
    if ( this.propertyBuilderContainer == null )
      return false;
    return this.propertyBuilderContainer.removeProperty( name );
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

  public Property getProperty( String name ) {
    if ( this.propertyBuilderContainer == null )
      return null;
    return this.propertyBuilderContainer.getProperty( name );
  }

  public List<Property> getProperties( String name ) {
    if ( this.propertyBuilderContainer == null )
      return Collections.emptyList();
    return this.propertyBuilderContainer.getProperties( name );
  }

//  public DatasetBuilder addDataset( String name ) {
//    this.isBuildable = Buildable.DONT_KNOW;
////    DatasetImpl di = new DatasetImpl( name, this, null );
////    this.datasetContainer.addDatasetNode( di );
////    return di;
//    return null;
//  }
//
//  public CatalogRefBuilder addCatalogRef( String name, String reference ) {
//    this.isBuildable = Buildable.DONT_KNOW;
////    CatalogRefImpl crb = new CatalogRefImpl( name, reference, this, null );
////    this.datasetContainer.addDatasetNode( crb );
////    return crb;
//    return null;
//  }
//
//  public boolean removeDataset( DatasetNodeBuilder builder ) {
//    if ( builder == null )
//      throw new IllegalArgumentException( "DatasetNodeBuilder may not be null.");
//
//    this.isBuildable = Buildable.DONT_KNOW;
//    return this.datasetContainer.removeDatasetNode( (DatasetNodeImpl) builder );
//  }
//
//  public List<DatasetNode> getDatasets() {
//    return this.datasetContainer.getDatasets();
//  }
//
//  public DatasetNode getDatasetById( String id ) {
//    return this.datasetContainer.getDatasetById( id );
//  }
//
//  public DatasetNode findDatasetByIdGlobally( String id ) {
//    return this.datasetContainer.getDatasetNodeByGloballyUniqueId( id );
//  }
//
//  public List<DatasetNodeBuilder> getDatasetNodeBuilders() {
//    return this.datasetContainer.getDatasetNodeBuilders();
//  }
//
//  public DatasetNodeBuilder getDatasetNodeBuilderById( String id ) {
//    return this.datasetContainer.getDatasetNodeBuilderById( id);
//  }
//
//  public DatasetNodeBuilder findDatasetNodeBuilderByIdGlobally( String id ) {
//    return this.datasetContainer.getDatasetNodeByGloballyUniqueId( id );
//  }

  public Buildable isBuildable() {
    return this.isBuildable;
  }

  public BuilderIssues checkForIssues()
  {
    builderIssues = new BuilderIssues();

    // Check that the baseUri is a valid URI.
    URI tstDocBaseUri = null;
    try {
      tstDocBaseUri = new URI( this.docBaseUri == null ? "" : this.docBaseUri );
    } catch (URISyntaxException e) {
      builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, String.format( "The document base URI [%s] must be a valid URI.", this.docBaseUri), this));
    }
    if ( tstDocBaseUri == null || ! tstDocBaseUri.isAbsolute())
      builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.WARNING, String.format( "The document base URI [%s} is not absolute.", this.docBaseUri), this));

    // Check subordinates.
    builderIssues.addAllIssues( this.serviceBuilderContainer.checkForIssues());
    if ( this.propertyBuilderContainer != null )
      builderIssues.addAllIssues( this.propertyBuilderContainer.checkForIssues() );
//    builderIssues.addAllIssues( this.catalogWideServiceBuilderTracker.checkForIssues());

    if ( builderIssues.isValid())
      this.isBuildable = Buildable.YES;
    else
      this.isBuildable = Buildable.NO;

    return builderIssues;
  }

  public Catalog build() throws IllegalStateException
  {
    if ( this.isBuildable != Buildable.YES )
      throw new IllegalStateException( "CatalogBuilder not buildable.");

    return new CatalogImpl( this.name, this.docBaseUri, this.version,
        this.expires, this.lastModified,
        this.propertyBuilderContainer, this.serviceBuilderContainer,
//        this.catalogWideServiceBuilderTracker,
        this.builderIssues );
  }
}
