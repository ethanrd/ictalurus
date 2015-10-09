package edu.ucar.ictalurus.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ucar.nc2.units.DateType;
import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.ServiceType;
import edu.ucar.ictalurus.testutil.ThreddsBuilderFactoryUtils;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * _MORE_
 *
 * @author edavis
 */
@RunWith(Parameterized.class)
public class CatalogBuilderTest {
  private ThreddsBuilderFactory threddsBuilderFactory;

  public CatalogBuilderTest( ThreddsBuilderFactory threddsBuilderFactory ) {
    this.threddsBuilderFactory = threddsBuilderFactory;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> threddsBuilderFactoryClasses() {
    return ThreddsBuilderFactoryUtils.threddsBuilderFactoryClasses();
  }

    @Test
    public void checkBasicCatalogBuilderCreation() {
      String docBaseUri = "http://mine/thredds/catalog.html";
      CatalogBuilder catalogBuilder =
          this.threddsBuilderFactory.newCatalogBuilder( "cat", docBaseUri, "1.0", null, null );
      assertNotNull( catalogBuilder );

      assertEquals( "cat", catalogBuilder.getName() );
      assertEquals( docBaseUri, catalogBuilder.getDocBaseUriAsString() );
      assertEquals( "1.0", catalogBuilder.getVersion() );
      assertTrue( catalogBuilder.getExpires().isBlank() );
      assertTrue( catalogBuilder.getLastModified().isBlank() );

      assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, catalogBuilder.isBuildable() );

      BuilderIssues builderIssues = catalogBuilder.checkForIssues();
      assertNotNull( builderIssues );
      assertTrue( builderIssues.isValid());
      assertEquals( ThreddsBuilder.Buildable.YES, catalogBuilder.isBuildable());


      catalogBuilder.setName( "cat2" );
      catalogBuilder.setDocBaseUri( "hhh" );
      catalogBuilder.setVersion( "1.1" );
      catalogBuilder.setExpires( new DateType( true, null) );
      catalogBuilder.setLastModified( new DateType( true, null) );
      assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, catalogBuilder.isBuildable() );

      builderIssues = catalogBuilder.checkForIssues();
      assertNotNull( builderIssues );
      assertTrue( builderIssues.isValid());
      assertEquals( ThreddsBuilder.Buildable.YES, catalogBuilder.isBuildable());

//      catalogBuilder.setType( ServiceType.COMPOUND );
//      catalogBuilder.setBaseUriAsString( "" );
//      catalogBuilder.setSuffix( ".dsr" );
//      catalogBuilder.setDescription( "contain all services" );
//      catalogBuilder.addProperty( "name", "val" );
//      catalogBuilder.addService( "odap", ServiceType.OPENDAP, "/thredds/dodsC" );
    }

  @Test
  public void checkProperties() {
    String docBaseUri = "http://mine/thredds/catalog.html";
    CatalogBuilder catalogBuilder =
        this.threddsBuilderFactory.newCatalogBuilder( "cat", docBaseUri, "1.0", null, null );
    assertNotNull( catalogBuilder );

    catalogBuilder.addProperty( "name1", "value1" );
    catalogBuilder.addProperty( "name2", "value2" );
    catalogBuilder.addProperty( "name3", "value3" );
    List<Property> properties = catalogBuilder.getProperties();
    assertEquals( 3, properties.size() );
    Property property = properties.get( 0 );
    assertEquals( "name1", property.getName() );
    assertEquals( "value1", property.getValue() );
    List<Property> propsName = catalogBuilder.getProperties( "name1" );
    assertEquals( 1, propsName.size() );
    assertEquals( property, propsName.get( 0 ) );

    property = properties.get( 1 );
    assertEquals( "name2", property.getName() );
    assertEquals( "value2", property.getValue() );
    propsName = catalogBuilder.getProperties( "name2" );
    assertEquals( 1, propsName.size() );
    assertEquals( property, propsName.get( 0 ) );

    property = properties.get( 2 );
    assertEquals( "name3", property.getName() );
    assertEquals( "value3", property.getValue() );
    propsName = catalogBuilder.getProperties( "name3" );
    assertEquals( 1, propsName.size() );
    assertEquals( property, propsName.get( 0 ) );

    assertTrue( catalogBuilder.removeProperty( properties.get( 1) ));
    properties = catalogBuilder.getProperties();
    assertEquals( 2, properties.size() );
    property = properties.get( 0 );
    assertEquals( "name1", property.getName() );
    assertEquals( "value1", property.getValue() );
    property = properties.get( 1 );
    assertEquals( "name3", property.getName() );
    assertEquals( "value3", property.getValue() );

  }

  @Test
  public void checkServices() {
    String docBaseUri = "http://mine/thredds/catalog.html";
    CatalogBuilder catalogBuilder =
        this.threddsBuilderFactory.newCatalogBuilder( "cat", docBaseUri, "1.0", null, null );
    assertNotNull( catalogBuilder );

    catalogBuilder.addService( "odap", ServiceType.OPENDAP, "/thredds/docsC/" );
    catalogBuilder.addService( "wcs", ServiceType.WCS, "/thredds/wcs/" );
    catalogBuilder.addService( "wms", ServiceType.WMS, "/thredds/wms/" );
    List<ServiceBuilder> serviceBuilderList = catalogBuilder.getServiceBuilders();
    assertEquals( 3, serviceBuilderList.size() );
    ServiceBuilder serviceBuilder = serviceBuilderList.get( 0 );
    assertEquals( "odap", serviceBuilder.getName());
    assertEquals( ServiceType.OPENDAP, serviceBuilder.getType());
    assertEquals( "/thredds/docsC/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );
    ServiceBuilder namedServiceBuilder = catalogBuilder.findReferencableServiceBuilderByName( "odap" );
    assertTrue( serviceBuilder == namedServiceBuilder );

    serviceBuilder = serviceBuilderList.get( 1 );
    assertEquals( "wcs", serviceBuilder.getName());
    assertEquals( ServiceType.WCS, serviceBuilder.getType());
    assertEquals( "/thredds/wcs/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );
    namedServiceBuilder = catalogBuilder.findReferencableServiceBuilderByName( "wcs" );
    assertTrue( serviceBuilder == namedServiceBuilder );

    serviceBuilder = serviceBuilderList.get( 2 );
    assertEquals( "wms", serviceBuilder.getName());
    assertEquals( ServiceType.WMS, serviceBuilder.getType());
    assertEquals( "/thredds/wms/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );
    namedServiceBuilder = catalogBuilder.findReferencableServiceBuilderByName( "wms" );
    assertTrue( serviceBuilder == namedServiceBuilder );

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, catalogBuilder.isBuildable() );
    BuilderIssues builderIssues = catalogBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid());
    assertEquals( ThreddsBuilder.Buildable.YES, catalogBuilder.isBuildable());

    catalogBuilder.removeService( serviceBuilderList.get( 1) );

    serviceBuilderList = catalogBuilder.getServiceBuilders();
    assertEquals( 2, serviceBuilderList.size() );
    serviceBuilder = serviceBuilderList.get( 0 );
    assertEquals( "odap", serviceBuilder.getName());
    assertEquals( ServiceType.OPENDAP, serviceBuilder.getType());
    assertEquals( "/thredds/docsC/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );

    serviceBuilder = serviceBuilderList.get( 1 );
    assertEquals( "wms", serviceBuilder.getName());
    assertEquals( ServiceType.WMS, serviceBuilder.getType());
    assertEquals( "/thredds/wms/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, catalogBuilder.isBuildable() );

    builderIssues = catalogBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid());
    assertEquals( ThreddsBuilder.Buildable.YES, catalogBuilder.isBuildable());
  }

}
