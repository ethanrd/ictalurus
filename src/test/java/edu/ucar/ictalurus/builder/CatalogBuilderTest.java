package edu.ucar.ictalurus.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ucar.nc2.units.DateType;
import edu.ucar.ictalurus.Property;

import java.util.Arrays;
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

  }
