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

import static org.junit.Assert.*;

/**
 * _MORE_
 *
 * @author edavis
 */
@RunWith(Parameterized.class)
public class CatalogWideServiceBuilderTrackerTest {
  private ThreddsBuilderFactory threddsBuilderFactory;

  public CatalogWideServiceBuilderTrackerTest( ThreddsBuilderFactory threddsBuilderFactory ) {
    this.threddsBuilderFactory = threddsBuilderFactory;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> threddsBuilderFactoryClasses() {
    return ThreddsBuilderFactoryUtils.threddsBuilderFactoryClasses();
  }

  @Test
  public void checkMultipleAdditionsOfSameServiceOK() {
    CatalogWideServiceBuilderTracker cwsbt = new CatalogWideServiceBuilderTracker();
    ServiceBuilder odap1 = this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC/");
    cwsbt.addService( odap1);
    cwsbt.addService( odap1);
    assertEquals( 1, cwsbt.numberOfReferenceableServices());
  }

  @Test
  public void checkRemoveReferencableServiceWithDuplicateNamePromotesDuplicateNamedService() {
    CatalogWideServiceBuilderTracker cwsbt = new CatalogWideServiceBuilderTracker();
    ServiceBuilder odap1 = this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC/");
    ServiceBuilder odap2 = this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC2/");
    ServiceBuilder wcs = this.threddsBuilderFactory.newServiceBuilder( "wcs", ServiceType.WCS, "/thredds/wcs/");

    cwsbt.addService( odap1);
    cwsbt.addService( odap2);
    cwsbt.addService( wcs);

    assertEquals( 2, cwsbt.numberOfReferenceableServices());
    assertEquals( 1, cwsbt.numberOfNonReferencableServicess());
    assertEquals( odap1, cwsbt.getReferenceableService( "odap"));
    assertEquals( wcs, cwsbt.getReferenceableService( "wcs"));

    assertTrue( cwsbt.removeService( odap1));
    assertEquals( 2, cwsbt.numberOfReferenceableServices());
    assertEquals( 0, cwsbt.numberOfNonReferencableServicess());
    assertEquals( odap2, cwsbt.getReferenceableService( "odap"));
    assertEquals( wcs, cwsbt.getReferenceableService( "wcs"));
  }

  @Test
  public void check() {
    CatalogWideServiceBuilderTracker cwsbt = new CatalogWideServiceBuilderTracker();
    assertNotNull( cwsbt);
    assertTrue( cwsbt.isEmpty() );
    assertEquals( 0, cwsbt.numberOfReferenceableServices() );
    assertEquals( 0, cwsbt.numberOfNonReferencableServicess() );

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, cwsbt.isBuildable());
    BuilderIssues builderIssues = cwsbt.checkForIssues();
    assertTrue( builderIssues.isValid() );
    assertEquals( ThreddsBuilder.Buildable.YES, cwsbt.isBuildable());

    ServiceBuilder odap1 = this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC/");
    ServiceBuilder odap2 = this.threddsBuilderFactory.newServiceBuilder( "odap", ServiceType.OPENDAP, "/thredds/dodsC2/");
    ServiceBuilder wcs = this.threddsBuilderFactory.newServiceBuilder( "wcs", ServiceType.WCS, "/thredds/wcs/");

    cwsbt.addService( odap1);
    cwsbt.addService( odap2);
    cwsbt.addService( wcs);

    assertFalse( cwsbt.isEmpty());
    assertEquals( 2, cwsbt.numberOfReferenceableServices());
    assertEquals( 1, cwsbt.numberOfNonReferencableServicess());
    assertEquals( odap1, cwsbt.getReferenceableService( "odap"));
    assertEquals( wcs, cwsbt.getReferenceableService( "wcs"));

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, cwsbt.isBuildable());
    builderIssues = cwsbt.checkForIssues();
    assertTrue( builderIssues.isValid() );
    assertEquals( ThreddsBuilder.Buildable.YES, cwsbt.isBuildable());

    assertTrue( cwsbt.removeService( odap1));
    assertEquals( 2, cwsbt.numberOfReferenceableServices());
    assertEquals( 0, cwsbt.numberOfNonReferencableServicess());
    assertEquals( odap2, cwsbt.getReferenceableService( "odap"));
    assertEquals( wcs, cwsbt.getReferenceableService( "wcs"));

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, cwsbt.isBuildable());
    builderIssues = cwsbt.checkForIssues();
    assertTrue( builderIssues.isValid() );
    assertEquals( ThreddsBuilder.Buildable.YES, cwsbt.isBuildable());

    // TODO ServiceBuilder-s controls build() of Tracker-s
//    CatalogWideServiceTracker serviceTracker = cwsbt.build();
//    assertNotNull( serviceTracker );
//    assertFalse( serviceTracker.isEmpty() );
//    assertEquals( 2, serviceTracker.numberOfReferenceableServices() );
//    assertEquals( 0, serviceTracker.numberOfNonReferenceableServices() );
//    assertEquals( odap2, serviceTracker.getReferenceableService( "odap"));
//    assertEquals( wcs, serviceTracker.getReferenceableService( "wcs"));
  }
}
