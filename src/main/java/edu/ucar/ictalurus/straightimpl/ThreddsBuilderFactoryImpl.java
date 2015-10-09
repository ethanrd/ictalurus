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
import edu.ucar.ictalurus.Service;
import edu.ucar.ictalurus.ServiceType;
import edu.ucar.ictalurus.builder.CatalogBuilder;
import edu.ucar.ictalurus.builder.ServiceBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilderFactory;

/**
 * _more_
 *
 * @author edavis
 * @since 4.0
 */
public class ThreddsBuilderFactoryImpl implements ThreddsBuilderFactory
{
  public ThreddsBuilderFactoryImpl() {};

  public CatalogBuilder newCatalogBuilder( String name, String docBaseUriAsString, String version,
                                           DateType expires, DateType lastModified ) {
    return new CatalogBuilderImpl( name, docBaseUriAsString, version, expires, lastModified );
  }

  public CatalogBuilder newCatalogBuilder( Catalog catalog )
  {
    // ToDo
    throw new UnsupportedOperationException( "Not yet implemented.");
//    if ( catalog instanceof CatalogImpl )
//    {
//      CatalogBuilder cb = (CatalogBuilder) catalog;
//      cb.unfinish();
//      return cb;
//    }
//    throw new IllegalArgumentException( "Given catalog not correct implementation for this ThreddsBuilderFactory.");
  }

  @Override
  public ServiceBuilder newServiceBuilder( String name, ServiceType type, String baseUriAsString ) {
    return new ServiceBuilderImpl( name, type, baseUriAsString );
  }

  @Override
  public ServiceBuilder newServiceBuilder( Service service ) {
    // ToDo
    throw new UnsupportedOperationException( "Not yet implemented." );
  }



//  @Override
//  public AccessBuilder newAccessBuilder( String serviceBuilderName, String urlPath ) {
//    return new AccessBuilderImpl( serviceBuilderName, urlPath );
//  }

//  public DatasetBuilder newDatasetBuilder( String name ) {
//    return new DatasetImpl( name, null, null );
//  }
//
//  public DatasetBuilder newDatasetBuilder( Dataset dataset ) {
//    // ToDo
//    throw new UnsupportedOperationException( "Not yet implemented." );
//  }
//
//  public CatalogRefBuilder newCatalogRefBuilder( String name, String referenceUriAsString ) {
//    return new CatalogRefImpl( name, referenceUriAsString, null, null );
//  }
//
//  public CatalogRefBuilder newCatalogRefBuilder( CatalogRef catRef ) {
//    // ToDo
//    throw new UnsupportedOperationException( "Not yet implemented." );
//  }

//  public MetadataBuilder newMetadataBuilder() {
//    return new MetadataImpl();
//  }

//  public ThreddsMetadataBuilder newThreddsMetadataBuilder()
//  {
//    return new ThreddsMetadataBuilderImpl();
//  }
}