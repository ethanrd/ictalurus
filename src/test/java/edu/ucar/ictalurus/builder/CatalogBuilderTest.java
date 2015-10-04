package edu.ucar.ictalurus.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ucar.nc2.units.DateType;

import java.util.Arrays;
import java.util.Collection;

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
      Object[][] classes = {
          {new edu.ucar.ictalurus.straightimpl.ThreddsBuilderFactoryImpl()},
          {new edu.ucar.ictalurus.simpleimpl.ThreddsBuilderFactoryImpl()}
      };
      return Arrays.asList( classes );
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

  }
