package edu.ucar.ictalurus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.ServiceBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilderFactory;
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
 * _MORE_
 *
 * @author edavis
 */
@RunWith(Parameterized.class)
public class ServiceTest {
  private ThreddsBuilderFactory threddsBuilderFactory;

  private String catName = "Catalog Name";
  private String docBaseUri = "http://server/thredds/catalog.html";
  private String version = "1.6";


  public ServiceTest( ThreddsBuilderFactory threddsBuilderFactory ) {
    this.threddsBuilderFactory = threddsBuilderFactory;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> threddsBuilderFactoryClasses() {
    return ThreddsBuilderFactoryUtils.threddsBuilderFactoryClasses();
  }

  private ServiceBuilder setupFor_checkBasicServiceBuilderCreation() {

    ServiceBuilder serviceBuilder =
        this.threddsBuilderFactory.newServiceBuilder( "all", ServiceType.COMPOUND, "" );
    assertNotNull( serviceBuilder );

    BuilderIssues builderIssues = serviceBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertEquals( ThreddsBuilder.Buildable.YES, serviceBuilder.isBuildable());

    return serviceBuilder;
  }

  private ServiceBuilder setupFor_checkProperties() {
    ServiceBuilder serviceBuilder = setupFor_checkBasicServiceBuilderCreation();

    serviceBuilder.addProperty( "name1", "value1" );
    serviceBuilder.addProperty( "name2", "value2" );
    serviceBuilder.addProperty( "name3", "value3" );

    BuilderIssues builderIssues = serviceBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertEquals( ThreddsBuilder.Buildable.YES, serviceBuilder.isBuildable());

    return serviceBuilder;
  }

  private ServiceBuilder setupFor_checkServices() {
    ServiceBuilder serviceBuilder = setupFor_checkBasicServiceBuilderCreation();

    serviceBuilder.addService( "odap", ServiceType.OPENDAP, "/thredds/docsC/" );
    serviceBuilder.addService( "wcs", ServiceType.WCS, "/thredds/wcs/" );
    serviceBuilder.addService( "wms", ServiceType.WMS, "/thredds/wms/" );

    BuilderIssues builderIssues = serviceBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertEquals( ThreddsBuilder.Buildable.YES, serviceBuilder.isBuildable());

    return serviceBuilder;
  }


  @Test
  public void checkBasicCatalogCreation()
      throws URISyntaxException
  {
    Service service = setupFor_checkBasicServiceBuilderCreation().build();

    assertEquals( "all", service.getName() );
    assertEquals( ServiceType.COMPOUND, service.getType() );
    assertEquals( "", service.getBaseUri() );
    assertEquals( "", service.getDescription() );
    assertEquals( "", service.getSuffix() );
  }

  @Test
  public void checkProperties() {
    Service serviceRoot = setupFor_checkProperties().build();

    // Test Service.getProperties()
    List<Property> properties = serviceRoot.getProperties();
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

    // Test Service.getPropertyNames()
    List<String> propertyNames = serviceRoot.getPropertyNames();
    assertEquals( property1.getName(), propertyNames.get( 0 ) );
    assertEquals( property2.getName(), propertyNames.get( 1 ) );
    assertEquals( property3.getName(), propertyNames.get( 2 ) );

    // Test Service.getProperties( String name)
    List<Property> propertiesWithName1 = serviceRoot.getProperties( "name1" );
    assertEquals( 1, propertiesWithName1.size() );
    Formatter compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareProperties( property1, propertiesWithName1.get( 0 ), compareLog ) );
    compareLog.close();

    List<Property> propertiesWithName2 = serviceRoot.getProperties( "name2" );
    assertEquals( 1, propertiesWithName2.size() );
    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareProperties( property2, propertiesWithName2.get( 0 ), compareLog));
    compareLog.close();

    List<Property> propertiesWithName3 = serviceRoot.getProperties( "name3" );
    assertEquals( 1, propertiesWithName3.size() );
    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareProperties( property3, propertiesWithName3.get( 0 ), compareLog));
    compareLog.close();

    // Test serviceRoot.getProperty( String name)
    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareProperties( property1, serviceRoot.getProperty( "name1" ), compareLog));
    compareLog.close();

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareProperties( property2, serviceRoot.getProperty( "name2" ), compareLog));
    compareLog.close();

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareProperties( property3, serviceRoot.getProperty( "name3" ), compareLog));
    compareLog.close();
  }

  @Test
  public void checkServices()
      throws URISyntaxException
  {
    Service serviceRoot = setupFor_checkServices().build();

    List<Service> serviceList = serviceRoot.getServices();
    assertEquals( 3, serviceList.size() );
    Service service1 = serviceList.get( 0 );
    assertEquals( "odap", service1.getName());
    assertEquals( ServiceType.OPENDAP, service1.getType());
    assertEquals( new URI( "/thredds/docsC/"), service1.getBaseUri());
    assertEquals( "", service1.getDescription() );
    assertEquals( "", service1.getSuffix() );

    Formatter compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareServices( service1, serviceRoot.findReferencableServiceByName( "odap" ), compareLog ) );
    compareLog.close();

    Service service2 = serviceList.get( 1 );
    assertEquals( "wcs", service2.getName());
    assertEquals( ServiceType.WCS, service2.getType());
    assertEquals( new URI( "/thredds/wcs/"), service2.getBaseUri());
    assertEquals( "", service2.getDescription() );
    assertEquals( "", service2.getSuffix() );

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareServices( service2, serviceRoot.findReferencableServiceByName( "wcs" ), compareLog));
    compareLog.close();

    Service service3 = serviceList.get( 2 );
    assertEquals( "wms", service3.getName() );
    assertEquals( ServiceType.WMS, service3.getType());
    assertEquals( new URI( "/thredds/wms/"), service3.getBaseUri());
    assertEquals( "", service3.getDescription() );
    assertEquals( "", service3.getSuffix() );

    compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareServices( service3, serviceRoot.findReferencableServiceByName( "wms" ), compareLog ) );
    compareLog.close();
  }
}
