package edu.ucar.ictalurus.xml.testutil;

import ucar.nc2.units.DateType;

/**
 * MORE
 *
 * @author edavis
 */
public class CatalogXmlAsStringUtils {
  private CatalogXmlAsStringUtils() {}

  /**
   * @deprecated Instead use {@link #getCatalog(String, String, ucar.nc2.units.DateType, ucar.nc2.units.DateType)}.
   */
  public static String getCatalog( DateType expires ) {
    return getCatalog( "", null, expires, null);
  }

  public static String getCatalog( String name, String version, DateType expires, DateType lastModified ) {
    return wrapThreddsXmlInCatalog( "", name, version, expires, lastModified );
  }

  /**
   * @deprecated Instead use {@link #wrapThreddsXmlInCatalog(String, String, String, ucar.nc2.units.DateType, ucar.nc2.units.DateType)}.
   */
  public static String wrapThreddsXmlInCatalog( String threddsXml, String name, String version, DateType expires ) {
    return wrapThreddsXmlInCatalog( threddsXml, name, version, expires, null );
  }

  public static String wrapThreddsXmlInCatalog( String threddsXml, String name, String version, DateType expires, DateType lastModified )
  {
    StringBuilder sb = new StringBuilder()
        .append( "<?xml version='1.0' encoding='UTF-8'?>\n" )
        .append( "<catalog xmlns='http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0'\n" )
        .append( "         xmlns:xlink='http://www.w3.org/1999/xlink'" );
    if ( expires != null && ! expires.isBlank() && ! expires.isPresent())
      sb.append( "\n         expires='" ).append( expires.toString() ).append( "'" );
    if ( lastModified != null && ! lastModified.isBlank() && ! lastModified.isPresent())
      sb.append( "\n         lastModified='" ).append( lastModified.toString() ).append( "'" );
    if ( name != null )
      sb.append( "\n         name='").append( name).append( "'" );
    if ( version != null )
      sb  .append( "\n         version='").append( version).append( "'");
    sb.append( ">\n" )
        .append( threddsXml )
        .append( "\n</catalog>" );

    return sb.toString();
  }

  public static String getCatalogWithService( DateType expires ) {
    return wrapThreddsXmlInCatalogWithService("", expires);
  }

  public static String wrapThreddsXmlInCatalogWithService( String threddsXml, DateType expires )
  {
    StringBuilder sb = new StringBuilder()
        .append("  <service name='odap' serviceType='OPENDAP' base='/thredds/dodsC/' />\n")
        .append(threddsXml);

    return wrapThreddsXmlInCatalog(sb.toString(), null, null, expires);
  }

  public static String getCatalogWithCompoundService( DateType expires ) {
    return wrapThreddsXmlInCatalogWithCompoundService( "", null, null, expires );
  }

  public static String wrapThreddsXmlInCatalogWithCompoundService( String threddsXml, String catName, String catVersion, DateType expires )
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <service name='all' serviceType='Compound' base=''>\n" )
        .append( "    <service name='odap' serviceType='OPENDAP' base='/thredds/dodsC/' />\n" )
        .append( "    <service name='wcs' serviceType='WCS' base='/thredds/wcs/' />\n" )
        .append( "    <service name='wms' serviceType='WMS' base='/thredds/wms/' />\n" )
        .append( "  </service>\n" )
        .append(    threddsXml );

    return wrapThreddsXmlInCatalog(  sb.toString(), catName, catVersion, expires );
  }

  public static String getCatalogWithSingleAccessDatasetWithRawServiceName()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='Test1' urlPath='test/test1.nc'>\n" )
        .append( "    <serviceName>odap</serviceName>\n" )
        .append( "  </dataset>\n" );

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String getCatalogWithSingleAccessDatasetWithMetadataServiceName()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='Test1' urlPath='test/test1.nc'>\n" )
        .append( "    <metadata>\n" )
        .append( "      <serviceName>odap</serviceName>\n" )
        .append( "    </metadata>\n" )
        .append( "  </dataset>\n" );

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String getCatalogWithSingleAccessDatasetWithInheritedMetadataServiceName()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='Test1' urlPath='test/test1.nc'>\n" )
        .append( "    <metadata inherited='true'>\n" )
        .append( "      <serviceName>odap</serviceName>\n" )
        .append( "    </metadata>\n" )
        .append( "  </dataset>\n" );

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String getCatalogWithSingleAccessDatasetOldStyle()
  {
    String sb = "<dataset name='Test1' urlPath='test/test1.nc' serviceName='odap' />\n";

    return wrapThreddsXmlInCatalogWithCompoundService( sb, null, null, null );
  }

  public static String wrapThreddsXmlInContainerDataset( String threddsXml )
  {

    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='container dataset' ID='containerDs'>\n" )
        .append(      threddsXml )
        .append( "  </dataset>\n" );

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String wrapThreddsXmlInCatalogDatasetMetadata( String threddsXml )
  {
    return wrapThreddsXmlInContainerDataset( "<metadata>" + threddsXml + "</metadata>\n" );
  }

  public static String wrapThreddsXmlInCatalogDatasetMetadataInherited( String threddsXml )
  {
    return wrapThreddsXmlInContainerDataset( "<metadata inherited='true'>" + threddsXml + "</metadata>\n" );
  }

  public static String getNestedDatasetWithRawServiceName()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='ds1'>\n")
        .append( "    <serviceName>odap</serviceName>\n")
        .append( "    <dataset name='ds2' urlPath='test/test1.nc' />\n")
        .append( "  </dataset>\n");

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String getNestedDatasetWithMetadataServiceName()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='ds1'>\n")
        .append( "    <metadata><serviceName>odap</serviceName></metadata>\n")
        .append( "    <dataset name='ds2' urlPath='test/test1.nc' />\n")
        .append( "  </dataset>\n");

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String getNestedDatasetWithUninheritedMetadataServiceName()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='ds1'>\n")
        .append( "    <metadata inherited='false'><serviceName>odap</serviceName></metadata>\n")
        .append( "    <dataset name='ds2' urlPath='test/test1.nc' />\n")
        .append( "  </dataset>\n");

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String getNestedDatasetWithInheritedMetadataServiceName()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='ds1'>\n")
        .append( "    <metadata inherited='true'><serviceName>odap</serviceName></metadata>\n")
        .append( "    <dataset name='ds2' urlPath='test/test1.nc' />\n")
        .append( "  </dataset>\n");

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

  public static String getCatalogWithNestedDatasetInheritedMetadata()
  {
    StringBuilder sb = new StringBuilder()
        .append( "  <dataset name='ds1'>\n")
        .append( "    <metadata inherited='true'><serviceName>odap</serviceName></metadata>\n")
        .append( "    <dataset name='ds2'>\n")
        .append( "      <serviceName>wcs</serviceName>\n")
        .append( "      <dataset name='Test1' urlPath='test/test1.nc' />\n" )
        .append( "      <dataset name='Test2' urlPath='test/test2.nc'>\n" )
        .append( "        <serviceName>wms</serviceName>\n" )
        .append( "      </dataset>\n" )
        .append( "    </dataset>")
        .append( "  </dataset>");

    return wrapThreddsXmlInCatalogWithCompoundService( sb.toString(), null, null, null );
  }

}
