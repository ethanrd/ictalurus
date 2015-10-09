package edu.ucar.ictalurus.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.ServiceType;
import edu.ucar.ictalurus.testutil.ThreddsBuilderFactoryUtils;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * _MORE_
 *
 * @author edavis
 */
@RunWith(Parameterized.class)
public class ServiceBuilderTest {
  private ThreddsBuilderFactory threddsBuilderFactory;

  public ServiceBuilderTest( ThreddsBuilderFactory threddsBuilderFactory ) {
    this.threddsBuilderFactory = threddsBuilderFactory;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> threddsBuilderFactoryClasses() {
    return ThreddsBuilderFactoryUtils.threddsBuilderFactoryClasses();
  }

  @Test
  public void checkBasicServiceBuilderCreation() {
    String name = "name";
    ServiceType dapServiceType = ServiceType.OPENDAP;
    String baseUri = "/thredds/dodsC/";
    ServiceBuilder serviceBuilder = this.threddsBuilderFactory.newServiceBuilder( name, dapServiceType, baseUri );
    assertNotNull( serviceBuilder );

    assertEquals( name, serviceBuilder.getName() );
    assertEquals( dapServiceType, serviceBuilder.getType() );
    assertEquals( baseUri, serviceBuilder.getBaseUriAsString() );
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, serviceBuilder.isBuildable() );

    BuilderIssues builderIssues = serviceBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid() );
    assertEquals( ThreddsBuilder.Buildable.YES, serviceBuilder.isBuildable() );

    String wcsServiceName = "wcs";
    ServiceType wcsServiceType = ServiceType.WCS;
    String wcsServiceBaseUri = "/thredds/wcs/";
    String wcsServiceDescrip = "good descrip";
    String wcsServiceSuffix = ".wcs";

    serviceBuilder.setName( wcsServiceName );
    serviceBuilder.setType( wcsServiceType );
    serviceBuilder.setBaseUriAsString( wcsServiceBaseUri );
    serviceBuilder.setDescription( wcsServiceDescrip );
    serviceBuilder.setSuffix( wcsServiceSuffix );
    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, serviceBuilder.isBuildable() );

    builderIssues = serviceBuilder.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid());
    assertEquals( ThreddsBuilder.Buildable.YES, serviceBuilder.isBuildable());

    assertEquals( wcsServiceName, serviceBuilder.getName() );
    assertEquals( wcsServiceType, serviceBuilder.getType() );
    assertEquals( wcsServiceBaseUri, serviceBuilder.getBaseUriAsString() );
    assertEquals( wcsServiceDescrip, serviceBuilder.getDescription() );
    assertEquals( wcsServiceSuffix, serviceBuilder.getSuffix() );
  }

  @Test
  public void checkProperties() {
    ServiceBuilder serviceBuilder =
        this.threddsBuilderFactory.newServiceBuilder( "dap", ServiceType.OPENDAP, "/thredds/dap/" );
    assertNotNull( serviceBuilder );

    serviceBuilder.addProperty( "name1", "value1" );
    serviceBuilder.addProperty( "name2", "value2" );
    serviceBuilder.addProperty( "name3", "value3" );
    List<Property> properties = serviceBuilder.getProperties();
    assertEquals( 3, properties.size() );
    Property property = properties.get( 0 );
    assertEquals( "name1", property.getName() );
    assertEquals( "value1", property.getValue() );
    List<Property> propsName = serviceBuilder.getProperties( "name1" );
    assertEquals( 1, propsName.size() );
    assertEquals( property, propsName.get( 0 ) );

    property = properties.get( 1 );
    assertEquals( "name2", property.getName() );
    assertEquals( "value2", property.getValue() );
    propsName = serviceBuilder.getProperties( "name2" );
    assertEquals( 1, propsName.size() );
    assertEquals( property, propsName.get( 0 ) );

    property = properties.get( 2 );
    assertEquals( "name3", property.getName() );
    assertEquals( "value3", property.getValue() );
    propsName = serviceBuilder.getProperties( "name3" );
    assertEquals( 1, propsName.size() );
    assertEquals( property, propsName.get( 0 ) );

    assertTrue( serviceBuilder.removeProperty( properties.get( 1) ));
    properties = serviceBuilder.getProperties();
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
    ServiceBuilder serviceBuilderRoot =
        this.threddsBuilderFactory.newServiceBuilder( "all", ServiceType.COMPOUND, "" );
    assertNotNull( serviceBuilderRoot );

    serviceBuilderRoot.addService( "odap", ServiceType.OPENDAP, "/thredds/docsC/" );
    serviceBuilderRoot.addService( "wcs", ServiceType.WCS, "/thredds/wcs/" );
    serviceBuilderRoot.addService( "wms", ServiceType.WMS, "/thredds/wms/" );
    List<ServiceBuilder> serviceBuilderList = serviceBuilderRoot.getServiceBuilders();
    assertEquals( 3, serviceBuilderList.size() );
    ServiceBuilder serviceBuilder = serviceBuilderList.get( 0 );
    assertEquals( "odap", serviceBuilder.getName());
    assertEquals( ServiceType.OPENDAP, serviceBuilder.getType());
    assertEquals( "/thredds/docsC/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );
    ServiceBuilder namedServiceBuilder = serviceBuilderRoot.findReferencableServiceBuilderByName( "odap" );
    assertTrue( serviceBuilder == namedServiceBuilder );

    serviceBuilder = serviceBuilderList.get( 1 );
    assertEquals( "wcs", serviceBuilder.getName());
    assertEquals( ServiceType.WCS, serviceBuilder.getType());
    assertEquals( "/thredds/wcs/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );
    namedServiceBuilder = serviceBuilderRoot.findReferencableServiceBuilderByName( "wcs" );
    assertTrue( serviceBuilder == namedServiceBuilder );

    serviceBuilder = serviceBuilderList.get( 2 );
    assertEquals( "wms", serviceBuilder.getName());
    assertEquals( ServiceType.WMS, serviceBuilder.getType());
    assertEquals( "/thredds/wms/", serviceBuilder.getBaseUriAsString());
    assertEquals( "", serviceBuilder.getDescription() );
    assertEquals( "", serviceBuilder.getSuffix() );
    namedServiceBuilder = serviceBuilderRoot.findReferencableServiceBuilderByName( "wms" );
    assertTrue( serviceBuilder == namedServiceBuilder );

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, serviceBuilderRoot.isBuildable() );
    BuilderIssues builderIssues = serviceBuilderRoot.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid());
    assertEquals( ThreddsBuilder.Buildable.YES, serviceBuilderRoot.isBuildable());

    serviceBuilderRoot.removeService( serviceBuilderList.get( 1) );

    serviceBuilderList = serviceBuilderRoot.getServiceBuilders();
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

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, serviceBuilderRoot.isBuildable() );

    builderIssues = serviceBuilderRoot.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid());
    assertEquals( ThreddsBuilder.Buildable.YES, serviceBuilderRoot.isBuildable());

//
//
//    serviceBuilder.addProperty( "name1", "value1" );
//    serviceBuilder.addProperty( "name2", "value2" );
//    serviceBuilder.addProperty( "name3", "value3" );
//    List<Property> properties = serviceBuilder.getProperties();
//    assertEquals( 3, properties.size() );
//    Property property = properties.get( 0 );
//    assertEquals( "name1", property.getName() );
//    assertEquals( "value1", property.getValue() );
//    List<Property> propsName = serviceBuilder.getProperties( "name1" );
//    assertEquals( 1, propsName.size() );
//    assertEquals( property, propsName.get( 0 ) );
//
//    property = properties.get( 1 );
//    assertEquals( "name2", property.getName() );
//    assertEquals( "value2", property.getValue() );
//    propsName = serviceBuilder.getProperties( "name2" );
//    assertEquals( 1, propsName.size() );
//    assertEquals( property, propsName.get( 0 ) );
//
//    property = properties.get( 2 );
//    assertEquals( "name3", property.getName() );
//    assertEquals( "value3", property.getValue() );
//    propsName = serviceBuilder.getProperties( "name3" );
//    assertEquals( 1, propsName.size() );
//    assertEquals( property, propsName.get( 0 ) );
//
//    assertTrue( serviceBuilder.removeProperty( properties.get( 1) ));
//    properties = serviceBuilder.getProperties();
//    assertEquals( 2, properties.size() );
//    property = properties.get( 0 );
//    assertEquals( "name1", property.getName() );
//    assertEquals( "value1", property.getValue() );
//    property = properties.get( 1 );
//    assertEquals( "name3", property.getName() );
//    assertEquals( "value3", property.getValue() );
  }

  //===========================
  @Test
  public void checkConformance_NonCompoundServiceNotValidWithEmptyBaseUri() {
    ServiceBuilder serviceBuilder =
        this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "");
    BuilderIssues issues = serviceBuilder.checkForIssues();
    assertFalse( issues.isEmpty());
    assertEquals( "Don't have exactly zero WARN issue.", 1, issues.getNumWarningIssues());
    assertEquals( "Don't have exactly one ERROR issue.", 0, issues.getNumErrorIssues());
    assertEquals( "Don't have exactly zero FATAL issue.", 0, issues.getNumFatalIssues());
    assertTrue( issues.isValid() );
  }

  // TODO all the tests below
//  @Test
//  public void checkConformance_NotAbleToAddServiceToNonCompoundService() {
//    ServiceBuilder serviceBuilder =
//        this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC/" );
//
//    serviceBuilder.addService( "wms", ServiceType.WMS, "/thredds/wms/");
//
//    assertEquals(ThreddsBuilder.Buildable.DONT_KNOW, serviceBuilder.isBuildable());
//    BuilderIssues issues = serviceBuilder.checkForIssues();
//    assertFalse( "Non-compound service should not contain a service.", issues.isEmpty());
//    assertTrue( "Non-compound, containing service should be valid.", issues.isValid());
//    assertEquals( "Don't have exactly zero WARN issue.", 0, issues.getNumWarningIssues());
//    assertEquals( "Don't have exactly one ERROR issue.", 1, issues.getNumErrorIssues());
//    assertEquals( "Don't have exactly zero FATAL issue.", 0, issues.getNumFatalIssues());
//  }
//
//  @Test
//  public void checkConformance_BuildOfServiceWithProperties() {
//    ServiceBuilder serviceBuilder =
//        this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC/" );
//
//    serviceBuilder.addProperty( "name1", "value1");
//    serviceBuilder.addProperty( "name2", "value2");
//    serviceBuilder.addProperty( "name3", "value3");
//    serviceBuilder.addProperty( "name4", "value4");
//    String value1 = serviceBuilder.getPropertyValue("name1");
//    assertEquals( "Property [name1] has unexpected value [" + value1 + "].", "value1", value1);
//
//    assertEquals( "", 4, serviceBuilder.getProperties().size());
//
//    assertEquals("", ThreddsBuilder.Buildable.DONT_KNOW, serviceBuilder.isBuildable());
//    BuilderIssues issues = serviceBuilder.checkForIssues();
//    assertTrue("", issues.isEmpty());
//
//    ServiceImpl service = (ServiceImpl) serviceBuilder.build();
//    assertEquals( "", "odap", service.getName());
//  }
//
//  @Test
//  public void checkRootServiceTrackingOfServicesByName() {
//    ServiceBuilder serviceBuilder =
//        this.threddsBuilderFactory.newServiceBuilder( "all", ServiceType.COMPOUND, "");
//
//    serviceBuilder.addService( "odap", ServiceType.OPENDAP, "/thredds/dodsC/" );
//    serviceBuilder.addService( "http", ServiceType.HTTP, "/thredds/fileServer/" );
//    ServiceBuilder sBGrid = serviceBuilder.addService( "grid", ServiceType.COMPOUND, "" );
//    ServiceBuilder sBWcs = sBGrid.addService( "wcs", ServiceType.WCS, "/thredds/wcs/" );
//    ServiceBuilder sBWms = sBGrid.addService( "wms", ServiceType.WMS, "/thredds/wms/" );
//    ServiceBuilder sBWcs2 = serviceBuilder.addService( "wcs", ServiceType.ADDE, "/thredds/adde/" );
//
//    assertEquals( sBWcs, serviceBuilder.findServiceBuilderByNameGlobally( "wcs"));
//  }
//
//  @Test
//  public void checkBuildOfNestedService() {
//    ServiceBuilder serviceBuilder =
//        this.threddsBuilderFactory.newServiceBuilder( "all", ServiceType.COMPOUND, "");
//
//    serviceBuilder.addService( "odap", ServiceType.OPENDAP, "/thredds/dodsC/" );
//    serviceBuilder.addService( "http", ServiceType.HTTP, "/thredds/fileServer/" );
//    ServiceBuilder sBGrid = serviceBuilder.addService( "grid", ServiceType.COMPOUND, "" );
//    ServiceBuilder sBWcs = sBGrid.addService( "wcs", ServiceType.WCS, "/thredds/wcs/" );
//    ServiceBuilder sBWms = sBGrid.addService( "wms", ServiceType.WMS, "/thredds/wms/" );
//    ServiceBuilder sBWcs2 = serviceBuilder.addService( "wcs", ServiceType.ADDE, "/thredds/adde/" );
//
//    BuilderIssues builderIssues = serviceBuilder.checkForIssues();
//    assertFalse( builderIssues.isEmpty());
//    assertEquals( 1, builderIssues.getNumWarningIssues());
//    assertEquals( 0, builderIssues.getNumErrorIssues());
//    assertEquals( 0, builderIssues.getNumFatalIssues());
//    assertTrue( builderIssues.isValid());
//
//    assertEquals(ThreddsBuilder.Buildable.YES, serviceBuilder.isBuildable());
//    Service service = serviceBuilder.build();
//    Service wcs = service.findServiceByNameGlobally("wcs");
//    assertEquals("wcs", wcs.getName());
//    assertEquals( ServiceType.WCS, wcs.getType());
//    assertEquals( "/thredds/wcs/", wcs.getBaseUri().toString());
//  }

}
