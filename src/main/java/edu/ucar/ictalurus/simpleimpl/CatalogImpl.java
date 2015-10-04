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
package edu.ucar.ictalurus.simpleimpl;


import java.net.URI;
import java.net.URISyntaxException;

import ucar.nc2.units.DateType;
import edu.ucar.ictalurus.Catalog;
import edu.ucar.ictalurus.ThreddsCatalogIssueContainer;
import edu.ucar.ictalurus.builder.BuilderIssue;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.CatalogBuilder;
import edu.ucar.ictalurus.util.ThreddsCatalogIssuesImpl;

/**
 * _more_
 *
 * @author edavis
 * @since 4.0
 */
class CatalogImpl implements Catalog, CatalogBuilder
{
  private String name;
  private String docBaseUriAsString;
  private URI docBaseUri;
  private String version;
  private DateType expires;
  private DateType lastModified;

//  private final ServiceContainer serviceContainer;
//  private final GlobalServiceContainer globalServiceContainer;
//
//  private final DatasetNodeContainer datasetContainer;
//
//  private final PropertyContainer propertyContainer;

  private ThreddsCatalogIssueContainer threddsCatalogIssueContainer;

  private BuilderIssues builderIssues;
  private Buildable isBuildable;

  private boolean isBuilt = false;

  CatalogImpl( String name, String docBaseUri, String version,
               DateType expires, DateType lastModified )
  {
    this. name = name != null ? name : "";
    this.docBaseUriAsString = docBaseUri != null ? docBaseUri : "";
    this.docBaseUri = null;
    this.version = version != null ? version : "";
    this.expires = expires != null ? expires : new DateType();
    this.lastModified = lastModified != null ? lastModified : new DateType();

    this.isBuildable = Buildable.DONT_KNOW;
  }

//  DatasetNodeContainer getDatasetNodeContainer()
//  {
//    return this.datasetContainer;
//  }

  public void setName( String name )
  {
    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
    this.isBuildable = Buildable.DONT_KNOW;

    this.name = name != null ? name : "";
  }

  public String getName() {
    return this.name;
  }

  public void setDocBaseUri( String docBaseUri ) {
    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
    this.isBuildable = Buildable.DONT_KNOW;
    this.docBaseUriAsString = docBaseUri != null ? docBaseUri : "";
  }

  public String getDocBaseUriAsString() {
    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
    return this.docBaseUriAsString;
  }

  public URI getDocBaseUri() {
    if ( ! isBuilt ) throw new IllegalStateException( "CatalogBuilder does not support this method." );
    return this.docBaseUri;
  }

  public void setVersion( String version )
  {
    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
    this.isBuildable = Buildable.DONT_KNOW;
    this.version = version != null ? version : "";
  }

  public String getVersion() {
    return this.version;
  }

  public void setExpires( DateType expires )
  {
    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
    this.isBuildable = Buildable.DONT_KNOW;
    this.expires = expires != null ? expires : new DateType ();
  }

  public DateType getExpires()
  {
    return this.expires;
  }

  public void setLastModified( DateType lastModified )
  {
    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
    this.isBuildable = Buildable.DONT_KNOW;
    this.lastModified = lastModified != null ? lastModified : new DateType();
  }

  public DateType getLastModified() {
    return this.lastModified;
  }

//  public ServiceBuilder addService( String name, ServiceType type, String baseUri )
//  {
//    if ( this.isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//
//    return this.serviceContainer.addService( name, type, baseUri );
//  }
//
//  public boolean removeService( ServiceBuilder serviceBuilder )
//  {
//    if ( this.isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//    if ( serviceBuilder == null )
//      return false;
//
//    return this.serviceContainer.removeService( (ServiceImpl) serviceBuilder );
//  }
//
//  public List<Service> getServices()
//  {
//    if ( !isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped its CatalogBuilder without build() being called." );
//    return this.serviceContainer.getServices();
//  }
//
//  public Service getServiceByName( String name )
//  {
//    if ( !isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped its CatalogBuilder without being build()-ed." );
//    return this.serviceContainer.getServiceByName( name );
//  }
//
//  public Service findServiceByNameGlobally( String name )
//  {
//    if ( !isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped its CatalogBuilder without being build()-ed." );
//    return this.globalServiceContainer.getServiceByGloballyUniqueName( name );
//  }
//
//  public List<ServiceBuilder> getServiceBuilders()
//  {
//    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.serviceContainer.getServiceBuilders();
//  }
//
//  public ServiceBuilder getServiceBuilderByName( String name )
//  {
//    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.serviceContainer.getServiceBuilderByName( name );
//  }
//
//  public ServiceBuilder findServiceBuilderByNameGlobally( String name )
//  {
//    if ( isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.globalServiceContainer.getServiceByGloballyUniqueName( name );
//  }
//
//  public void addProperty( String name, String value )
//  {
//    if ( this.isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//    this.propertyContainer.addProperty( name, value );
//  }
//
//  public boolean removeProperty( String name )
//  {
//    if ( this.isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//
//    return this.propertyContainer.removeProperty( name );
//  }
//
//  public List<String> getPropertyNames()
//  {
//    if ( this.isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.propertyContainer.getPropertyNames();
//  }
//
//  public String getPropertyValue( String name )
//  {
//    if ( this.isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.propertyContainer.getPropertyValue( name );
//  }
//
//  public List<Property> getProperties()
//  {
//    if ( !this.isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped from its CatalogBuilder before build() was called." );
//    return this.propertyContainer.getProperties();
//  }
//
//  public Property getPropertyByName( String name )
//  {
//    if ( !this.isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped from its CatalogBuilder before build() was called." );
//    return this.propertyContainer.getPropertyByName( name );
//  }
//
//  public DatasetBuilder addDataset( String name )
//  {
//    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
//    DatasetImpl di = new DatasetImpl( name, this, null );
//    this.datasetContainer.addDatasetNode( di );
//    return di;
//  }
//
//  public CatalogRefBuilder addCatalogRef( String name, String reference )
//  {
//    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
//    CatalogRefImpl crb = new CatalogRefImpl( name, reference, this, null );
//    this.datasetContainer.addDatasetNode( crb );
//    return crb;
//  }
//
//  public boolean removeDataset( DatasetNodeBuilder builder )
//  {
//    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
//    if ( builder == null )
//      throw new IllegalArgumentException( "DatasetNodeBuilder may not be null.");
//
//    return this.datasetContainer.removeDatasetNode( (DatasetNodeImpl) builder );
//  }
//
//  public List<DatasetNode> getDatasets()
//  {
//    if ( !isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped its CatalogBuilder without being build()-ed." );
//    return this.datasetContainer.getDatasets();
//  }
//
//  public DatasetNode getDatasetById( String id )
//  {
//    if ( !isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped its CatalogBuilder without being build()-ed." );
//    return this.datasetContainer.getDatasetById( id );
//  }
//
//  public DatasetNode findDatasetByIdGlobally( String id )
//  {
//    if ( !isBuilt )
//      throw new IllegalStateException( "This Catalog has escaped its CatalogBuilder without being build()-ed." );
//    return this.datasetContainer.getDatasetNodeByGloballyUniqueId( id );
//  }
//
//  public List<DatasetNodeBuilder> getDatasetNodeBuilders()
//  {
//    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.datasetContainer.getDatasetNodeBuilders();
//  }
//
//  public DatasetNodeBuilder getDatasetNodeBuilderById( String id )
//  {
//    if ( isBuilt ) throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.datasetContainer.getDatasetNodeBuilderById( id);
//  }
//
//  public DatasetNodeBuilder findDatasetNodeBuilderByIdGlobally( String id )
//  {
//    if ( isBuilt )
//      throw new IllegalStateException( "This CatalogBuilder has been built." );
//    return this.datasetContainer.getDatasetNodeByGloballyUniqueId( id );
//  }

  public Buildable isBuildable() {
    return this.isBuildable;
  }

  public BuilderIssues checkForIssues()
  {
    if ( this.isBuilt )
      throw new IllegalStateException( "This CatalogBuilder has been built." );

    builderIssues = new BuilderIssues();

    if ( this.name == null || this.name.isEmpty() )
      builderIssues.addIssue( BuilderIssue.Severity.WARNING, "Catalog name is null or empty.", this);
    if ( this.version == null || this.version.isEmpty() )
      builderIssues.addIssue( BuilderIssue.Severity.WARNING, "Catalog version is null or empty.", this);
    if ( this.expires == null || this.expires.isBlank() )
      builderIssues.addIssue( BuilderIssue.Severity.WARNING, "Catalog expires date is null or blank.", this);
    if ( this.lastModified == null || this.lastModified.isBlank() )
      builderIssues.addIssue( BuilderIssue.Severity.WARNING, "Catalog lastModified date is null or blank.", this);

    // Check that the baseUri is a valid URI.
    if ( ! this.docBaseUriAsString.isEmpty()) {
      try {
        new URI( this.docBaseUriAsString);
      } catch (URISyntaxException e) {
        builderIssues.addIssue( new BuilderIssue( BuilderIssue.Severity.ERROR, "The baseUri [" + this.docBaseUriAsString + "] of this Service [" + this.name + "] must be a valid URI.", this));
      }
    }

    // Check subordinates.
//    builderIssues.addAllIssues( this.globalServiceContainer.checkForIssues());
//    builderIssues.addAllIssues( this.serviceContainer.checkForIssues());
//    builderIssues.addAllIssues( this.datasetContainer.checkForIssues());
//    builderIssues.addAllIssues( this.propertyContainer.checkForIssues());

    if ( builderIssues.isValid())
      this.isBuildable = Buildable.YES;
    else
      this.isBuildable = Buildable.NO;

    return builderIssues;
  }

  public Catalog build() throws IllegalStateException
  {
    if ( this.isBuilt )
      throw new IllegalStateException( "CatalogBuilder has been built." );
    if ( this.isBuildable != Buildable.YES )
      throw new IllegalStateException( "CatalogBuilder not buildable.");

    URI tmpUri = null;
    try {
      tmpUri = new URI( this.docBaseUriAsString);
    } catch (URISyntaxException e) {
      throw new IllegalStateException( String.format( "Document Base URI [%s] not valid URI.", this.docBaseUriAsString) );
    }
    this.docBaseUri = tmpUri;

//    this.serviceContainer.build();
//    this.datasetContainer.build();
//    this.propertyContainer.build();

    this.threddsCatalogIssueContainer = new ThreddsCatalogIssuesImpl( builderIssues);

    this.isBuilt = true;
    //this.isBuildable = Buildable.NO; // TODO Probably not needed.
    return this;
  }

  public ThreddsCatalogIssueContainer getCatalogIssues() {
    if (! this.isBuilt) throw new IllegalStateException( "CatalogBuilder not built." );
    return this.threddsCatalogIssueContainer;
  }
}
