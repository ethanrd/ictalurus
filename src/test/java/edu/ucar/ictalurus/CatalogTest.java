package edu.ucar.ictalurus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ucar.nc2.units.DateType;
import edu.ucar.ictalurus.builder.*;
import edu.ucar.ictalurus.testutil.ThreddsBuilderFactoryUtils;
import edu.ucar.ictalurus.util.ThreddsCompareUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * _more_
 *
 * @author edavis
 */
@RunWith(Parameterized.class)
public class CatalogTest
{
  private ThreddsBuilderFactory threddsBuilderFactory;

  private String catName = "Catalog Name";
  private String docBaseUri = "http://server/thredds/catalog.html";
  private String version = "1.6";
  private DateType expires = new DateType( true, null);
  private DateType lastModified = new DateType( true, null);


  public CatalogTest( ThreddsBuilderFactory threddsBuilderFactory ) {
    this.threddsBuilderFactory = threddsBuilderFactory;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> threddsBuilderFactoryClasses() {
    return ThreddsBuilderFactoryUtils.threddsBuilderFactoryClasses();
  }

  private CatalogBuilder setupFor_checkBasicCatalogBuilderCreation() {

    CatalogBuilder catalogBuilder =
            this.threddsBuilderFactory.newCatalogBuilder( catName, docBaseUri, version, expires, lastModified );
    assertNotNull( catalogBuilder );

    BuilderIssues builderIssues = catalogBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertEquals( ThreddsBuilder.Buildable.YES, catalogBuilder.isBuildable());

    return catalogBuilder;
  }

  private CatalogBuilder setupFor_checkProperties() {
    CatalogBuilder catalogBuilder = setupFor_checkBasicCatalogBuilderCreation();

    catalogBuilder.addProperty( "name1", "value1" );
    catalogBuilder.addProperty( "name2", "value2" );
    catalogBuilder.addProperty( "name3", "value3" );

    BuilderIssues builderIssues = catalogBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertEquals( ThreddsBuilder.Buildable.YES, catalogBuilder.isBuildable());

    return catalogBuilder;
  }

  private CatalogBuilder setupFor_checkServices() {
    CatalogBuilder catalogBuilder = setupFor_checkBasicCatalogBuilderCreation();

    catalogBuilder.addService( "odap", ServiceType.OPENDAP, "/thredds/docsC/" );
    catalogBuilder.addService( "wcs", ServiceType.WCS, "/thredds/wcs/" );
    catalogBuilder.addService( "wms", ServiceType.WMS, "/thredds/wms/" );

    BuilderIssues builderIssues = catalogBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertEquals( ThreddsBuilder.Buildable.YES, catalogBuilder.isBuildable());

    return catalogBuilder;
  }

  @Test
  public void checkBasicCatalogCreation()
          throws URISyntaxException
  {
    Catalog catalog = setupFor_checkBasicCatalogBuilderCreation().build();

    assertEquals( catName, catalog.getName());
    assertEquals( new URI( docBaseUri ), catalog.getDocBaseUri() );
    assertEquals( version, catalog.getVersion() );
    assertEquals( expires, catalog.getExpires() );
    assertEquals( lastModified, catalog.getLastModified() );
  }

  @Test
  public void checkProperties() {
    Catalog catalog = setupFor_checkProperties().build();

    // Test Catalog.getProperties()
    List<Property> properties = catalog.getProperties();
    assertEquals( 3, properties.size() );

    Property property1 = properties.get( 0 );
    assertEquals( "name1", property1.getName() );
    assertEquals( "value1", property1.getValue() );

    Property property2 = properties.get( 1 );
    assertEquals( "name2", property2.getName() );
    assertEquals( "value2", property2.getValue() );

    Property property3 = properties.get( 2 );
    assertEquals( "name3", property3.getName() );
    assertEquals( "value3", property3.getValue() );

    // Test Catalog.getPropertyNames()
    List<String> propertyNames = catalog.getPropertyNames();
    assertEquals( property1.getName(), propertyNames.get( 0 ) );
    assertEquals( property2.getName(), propertyNames.get( 1 ) );
    assertEquals( property3.getName(), propertyNames.get( 2 ) );

    // Test Catalog.getProperties( String name)
    List<Property> propertiesWithName1 = catalog.getProperties( "name1" );
    assertEquals( 1, propertiesWithName1.size() );
    Formatter compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareProperties( property1, propertiesWithName1.get( 0 ), compareLog ) );
    compareLog.close();
//    assertEquals( property1, propertiesWithName1.get( 0 ) );

    List<Property> propertiesWithName2 = catalog.getProperties( "name2" );
    assertEquals( 1, propertiesWithName2.size() );
    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareProperties( property2, propertiesWithName2.get( 0 ), compareLog));
    compareLog.close();
//    assertEquals( property2, propertiesWithName2.get( 0 ) );

    List<Property> propertiesWithName3 = catalog.getProperties( "name3" );
    assertEquals( 1, propertiesWithName3.size() );
    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareProperties( property3, propertiesWithName3.get( 0 ), compareLog));
    compareLog.close();
//    assertEquals( property3, propertiesWithName3.get( 0 ) );

    // Test Catalog.getProperty( String name)
    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareProperties( property1, catalog.getProperty( "name1" ), compareLog));
    compareLog.close();

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareProperties( property2, catalog.getProperty( "name2" ), compareLog));
    compareLog.close();

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareProperties( property3, catalog.getProperty( "name3" ), compareLog));
    compareLog.close();
  }

  @Test
  public void checkServices()
          throws URISyntaxException
  {
    Catalog catalog = setupFor_checkServices().build();

    List<Service> serviceList = catalog.getServices();
    assertEquals( 3, serviceList.size() );
    Service service1 = serviceList.get( 0 );
    assertEquals( "odap", service1.getName());
    assertEquals( ServiceType.OPENDAP, service1.getType());
    assertEquals( new URI( "/thredds/docsC/"), service1.getBaseUri());
    assertEquals( "", service1.getDescription() );
    assertEquals( "", service1.getSuffix() );

    Formatter compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareServices( service1, catalog.findReferencableServiceByName( "odap" ), compareLog ));
    compareLog.close();

    Service service2 = serviceList.get( 1 );
    assertEquals( "wcs", service2.getName());
    assertEquals( ServiceType.WCS, service2.getType());
    assertEquals( new URI( "/thredds/wcs/"), service2.getBaseUri());
    assertEquals( "", service2.getDescription() );
    assertEquals( "", service2.getSuffix() );

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareServices( service2, catalog.findReferencableServiceByName( "wcs" ), compareLog));
    compareLog.close();


    Service service3 = serviceList.get( 2 );
    assertEquals( "wms", service3.getName());
    assertEquals( ServiceType.WMS, service3.getType());
    assertEquals( new URI( "/thredds/wms/"), service3.getBaseUri());
    assertEquals( "", service3.getDescription() );
    assertEquals( "", service3.getSuffix() );

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
                ThreddsCompareUtils.compareServices( service3, catalog.findReferencableServiceByName( "wms" ), compareLog));
    compareLog.close();
  }
}
