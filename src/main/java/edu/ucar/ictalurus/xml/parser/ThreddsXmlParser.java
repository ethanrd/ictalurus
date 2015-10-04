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
package edu.ucar.ictalurus.xml.parser;

import edu.ucar.ictalurus.Catalog;
import edu.ucar.ictalurus.builder.CatalogBuilder;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;

/**
 * Provide methods for parsing THREDDS catalog XML documents and
 * generating Catalog, CatalogBuilder, Dataset, DatasetBuilder,
 * Metadata, or MetadataBuilder object.
 *
 * @author edavis
 * @since 4.0
 */
public interface ThreddsXmlParser
{
  public Catalog parse( String docLocationUri ) throws URISyntaxException, ThreddsXmlParserException;
  public Catalog parse( File file, String docBaseUri ) throws ThreddsXmlParserException;
  public Catalog parse( Reader reader, String docBaseUri ) throws ThreddsXmlParserException;
  public Catalog parse( InputStream is, String docBaseUri ) throws ThreddsXmlParserException;

  public CatalogBuilder parseIntoBuilder( String docLocationUri ) throws URISyntaxException, ThreddsXmlParserException;
  public CatalogBuilder parseIntoBuilder( File file, String docBaseUri ) throws ThreddsXmlParserException;
  public CatalogBuilder parseIntoBuilder( Reader reader, String docBaseUri ) throws ThreddsXmlParserException;
  public CatalogBuilder parseIntoBuilder( InputStream is, String docBaseUri ) throws ThreddsXmlParserException;

//  public Dataset parseDataset( String docLocationUri ) throws URISyntaxException, ThreddsXmlParserException;
//  public Dataset parseDataset( File file, String docBaseUri ) throws ThreddsXmlParserException;
//  public Dataset parseDataset( Reader reader, String docBaseUri ) throws ThreddsXmlParserException;
//  public Dataset parseDataset( InputStream is, String docBaseUri ) throws ThreddsXmlParserException;
//
//  public DatasetBuilder parseDatasetIntoBuilder( String docLocationUri ) throws URISyntaxException, ThreddsXmlParserException;
//  public DatasetBuilder parseDatasetIntoBuilder( File file, String docBaseUri ) throws ThreddsXmlParserException;
//  public DatasetBuilder parseDatasetIntoBuilder( Reader reader, String docBaseUri ) throws ThreddsXmlParserException;
//  public DatasetBuilder parseDatasetIntoBuilder( InputStream is, String docBaseUri ) throws ThreddsXmlParserException;
//
//  public Metadata parseMetadata( String docLocationUri ) throws URISyntaxException, ThreddsXmlParserException;
//  public Metadata parseMetadata( File file, String docBaseUri ) throws ThreddsXmlParserException;
//  public Metadata parseMetadata( Reader reader, String docBaseUri ) throws ThreddsXmlParserException;
//  public Metadata parseMetadata( InputStream is, String docBaseUri ) throws ThreddsXmlParserException;
//
//  public MetadataBuilder parseMetadataIntoBuilder( String docLocationUri ) throws URISyntaxException, ThreddsXmlParserException;
//  public MetadataBuilder parseMetadataIntoBuilder( File file, String docBaseUri ) throws ThreddsXmlParserException;
//  public MetadataBuilder parseMetadataIntoBuilder( Reader reader, String docBaseUri ) throws ThreddsXmlParserException;
//  public MetadataBuilder parseMetadataIntoBuilder( InputStream is, String docBaseUri ) throws ThreddsXmlParserException;
}