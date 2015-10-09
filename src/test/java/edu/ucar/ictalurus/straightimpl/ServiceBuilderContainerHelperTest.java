package edu.ucar.ictalurus.straightimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import edu.ucar.ictalurus.ServiceType;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.ServiceBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilderFactory;
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
public class ServiceBuilderContainerHelperTest {
    private ThreddsBuilderFactory threddsBuilderFactory;

    public ServiceBuilderContainerHelperTest( ThreddsBuilderFactory threddsBuilderFactory ) {
      this.threddsBuilderFactory = threddsBuilderFactory;
    }

  @Parameterized.Parameters
  public static Collection<Object[]> threddsBuilderFactoryClasses() {
    return ThreddsBuilderFactoryUtils.threddsBuilderFactoryClasses();
  }

  @Test
  public void checkNewEmptyContainer() {
    ServiceBuilderContainerHelper sbc = new ServiceBuilderContainerHelper();
    assertNotNull( sbc );
    assertTrue( sbc.isEmpty());
    assertEquals( 0, sbc.size() );

    assertEquals( ThreddsBuilder.Buildable.YES, sbc.isBuildable() );

    BuilderIssues builderIssues = sbc.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid() );
    assertTrue( builderIssues.isEmpty() );
  }

  @Test
  public void checkNewContainer() {
    ServiceBuilderContainerHelper sbc = new ServiceBuilderContainerHelper();
    sbc.addService( this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC/" ));
    sbc.addService( this.threddsBuilderFactory.newServiceBuilder( "wcs", ServiceType.WCS, "/thredds/wcs/" ));
    sbc.addService( this.threddsBuilderFactory.newServiceBuilder( "wms", ServiceType.WMS, "/thredds/wms/" ));
    assertFalse( sbc.isEmpty() );
    assertEquals( 3, sbc.size() );
    List<ServiceBuilder> services = sbc.getServices();
    assertEquals( 3, services.size() );

    ServiceBuilder serviceBuilder = services.get( 0 );
    assertEquals( "odap", serviceBuilder.getName() );
    assertEquals( ServiceType.OPENDAP, serviceBuilder.getType() );
    assertEquals( "/thredds/dodsC/", serviceBuilder.getBaseUriAsString());

    serviceBuilder = services.get( 1 );
    assertEquals( "wcs", serviceBuilder.getName() );
    assertEquals( ServiceType.WCS, serviceBuilder.getType() );
    assertEquals( "/thredds/wcs/", serviceBuilder.getBaseUriAsString());

    serviceBuilder = services.get( 2 );
    assertEquals( "wms", serviceBuilder.getName() );
    assertEquals( ServiceType.WMS, serviceBuilder.getType() );
    assertEquals( "/thredds/wms/", serviceBuilder.getBaseUriAsString());

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, sbc.isBuildable() );

    BuilderIssues builderIssues = sbc.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid() );
    assertTrue( builderIssues.isEmpty() );
    assertEquals( ThreddsBuilder.Buildable.YES, sbc.isBuildable() );

    sbc.addService( this.threddsBuilderFactory.newServiceBuilder( "fred", ServiceType.ADDE, "/thredds/adde/" ));
    serviceBuilder = sbc.getServices().get( 3 );

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, sbc.isBuildable() );

    builderIssues = sbc.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid() );
    assertTrue( builderIssues.isEmpty() );
    assertEquals( ThreddsBuilder.Buildable.YES, sbc.isBuildable() );

    sbc.removeService( serviceBuilder );

    // Removing a service from a container that already builds should leave that container buildable.
    assertEquals( ThreddsBuilder.Buildable.YES, sbc.isBuildable() );

    builderIssues = sbc.checkForIssues();
    assertNotNull( builderIssues );
    assertTrue( builderIssues.isValid() );
    assertTrue( builderIssues.isEmpty() );
    assertEquals( ThreddsBuilder.Buildable.YES, sbc.isBuildable() );

    // TODO test the build()
    //sbc.build();
  }

}
