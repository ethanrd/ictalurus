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
package edu.ucar.ictalurus.builder;

import ucar.nc2.units.DateType;
import edu.ucar.ictalurus.Catalog;
import edu.ucar.ictalurus.Property;

import java.util.List;

/**
 * _more_
 *
 * @author edavis
 * @since 4.0
 */
public interface CatalogBuilder extends ThreddsBuilder, ServiceBuilderContainer
{
  public String getName();
  public void setName( String name );

  public String getDocBaseUriAsString();
  public void setDocBaseUri( String docBaseUri );

  public String getVersion();
  public void setVersion( String version );

  public DateType getExpires();
  public void setExpires( DateType expires );

  public DateType getLastModified();
  public void setLastModified( DateType lastModified );

//  public DatasetBuilder addDataset( String name );
//  public CatalogRefBuilder addCatalogRef( String name, String uriReference );
//  public boolean removeDataset( DatasetNodeBuilder datasetNodeBuilder );
//
//  public List<DatasetNodeBuilder> getDatasetNodeBuilders();
//  public DatasetNodeBuilder getDatasetNodeBuilderById( String id );
//  public DatasetNodeBuilder findDatasetNodeBuilderByIdGlobally( String id );

  public void addProperty( String name, String value );
  public boolean removeProperty( Property property );
  public List<Property> getProperties();
  public List<String> getPropertyNames();
  public List<Property> getProperties( String name );
  public Property getProperty( String name );

  /**
   * Generate the resulting Catalog.
   *
   * @return the resulting Catalog object.  
   * @throws IllegalStateException if any Catalog invariants are violated.
   */
  public Catalog build() throws IllegalStateException;

}
