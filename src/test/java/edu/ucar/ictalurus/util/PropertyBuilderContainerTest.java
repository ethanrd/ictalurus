package edu.ucar.ictalurus.util;

import org.junit.Test;
import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.ThreddsBuilder;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * _MORE_
 *
 * @author edavis
 */
public class PropertyBuilderContainerTest {

  @Test
  public void checkNewContainer() {
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();
    assertTrue( pbc.isEmpty());
    assertEquals( 0, pbc.size() );
    pbc.addProperty( "name1", "value1" );
    pbc.addProperty( "name2", "value2" );
    pbc.addProperty( "name1", "val1" );
    assertEquals( 3, pbc.size() );
    List<Property> properties = pbc.getProperties();
    assertEquals( 3, properties.size() );
  }

  @Test
  public void checkGetAllProperties()
  {
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();

    // Add three properties to container.
    String name1 = "name1";
    String value1 = "value1";
    pbc.addProperty( name1, value1 );
    String name2 = "name2";
    String value2 = "value2";
    pbc.addProperty( name2, value2 );
    String name3 = "name3";
    String value3 = "value3";
    pbc.addProperty( name3, value3 );

    List<Property> propList = pbc.getProperties();
    assertEquals( 3, propList.size() );
    assertEquals( name1, propList.get( 0 ).getName() );
    assertEquals( value1, propList.get( 0 ).getValue() );
    assertEquals( name2, propList.get( 1 ).getName() );
    assertEquals( value2, propList.get( 1 ).getValue() );
    assertEquals( name3, propList.get( 2 ).getName() );
    assertEquals( value3, propList.get( 2 ).getValue() );
  }

  @Test
  public void checkGetEachPropertyByName()
  {
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();

    // Add three properties to container.
    String name1 = "name1";
    String value1 = "value1";
    pbc.addProperty( name1, value1 );
    String name2 = "name2";
    String value2 = "value2";
    pbc.addProperty( name2, value2 );
    String name3 = "name3";
    String value3 = "value3";
    pbc.addProperty( name3, value3 );

    Property property = pbc.getProperty( name1 );
    assertEquals( name1, property.getName() );
    assertEquals( value1, property.getValue() );
    property = pbc.getProperty( name2 );
    assertEquals( name2, property.getName() );
    assertEquals( value2, property.getValue() );
    property = pbc.getProperty( name3 );
    assertEquals( name3, property.getName() );
    assertEquals( value3, property.getValue() );

    List<String> nameList = pbc.getPropertyNames();
    assertEquals( 3, nameList.size() );
    assertEquals( name1, nameList.get( 0 ) );
    assertEquals( name2, nameList.get( 1 ) );
    assertEquals( name3, nameList.get( 2 ) );
  }

  @Test
  public void checkGetPropertyNames()
  {
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();

    // Add three properties to container.
    String name1 = "name1";
    String value1 = "value1";
    pbc.addProperty( name1, value1 );
    String name2 = "name2";
    String value2 = "value2";
    pbc.addProperty( name2, value2 );
    String name3 = "name3";
    String value3 = "value3";
    pbc.addProperty( name3, value3 );

    List<String> nameList = pbc.getPropertyNames();
    assertEquals( 3, nameList.size() );
    assertEquals( name1, nameList.get( 0 ) );
    assertEquals( name2, nameList.get( 1 ) );
    assertEquals( name3, nameList.get( 2 ) );
  }

  @Test
  public void checkGetPropertiesByName()
  {
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();

    // Add three properties to container.
    String name1 = "name1";
    String value1 = "value1";
    pbc.addProperty( name1, value1 );
    String name2 = "name2";
    String value2 = "value2";
    pbc.addProperty( name2, value2 );
    String name3 = "name3";
    String value3 = "value3";
    pbc.addProperty( name3, value3 );

    String value1_1 = "value1.1";
    pbc.addProperty( name1, value1_1 );
    String value2_1 = "value2.1";
    pbc.addProperty( name2, value2_1 );
    String value3_1 = "value3.1";
    pbc.addProperty( name3, value3_1 );

    assertEquals( 6, pbc.size() );

    List<Property> propList = pbc.getProperties( name1 );
    assertEquals( 2, propList.size() );
    Property property = propList.get( 0 );
    assertEquals( name1, property.getName() );
    assertEquals( value1, property.getValue() );
    property = propList.get( 1 );
    assertEquals( name1, property.getName() );
    assertEquals( value1_1, property.getValue() );

    propList = pbc.getProperties( name2 );
    assertEquals( 2, propList.size() );
    property = propList.get( 0 );
    assertEquals( name2, property.getName() );
    assertEquals( value2, property.getValue() );
    property = propList.get( 1 );
    assertEquals( name2, property.getName() );
    assertEquals( value2_1, property.getValue() );

    propList = pbc.getProperties( name3 );
    assertEquals( 2, propList.size() );
    property = propList.get( 0 );
    assertEquals( name3, property.getName() );
    assertEquals( value3, property.getValue() );
    property = propList.get( 1 );
    assertEquals( name3, property.getName() );
    assertEquals( value3_1, property.getValue() );
  }

  @Test
  public void checkGetPropertyByName()
  {
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();

    // Add three properties to container.
    String name1 = "name1";
    String value1 = "value1";
    pbc.addProperty( name1, value1 );
    String name2 = "name2";
    String value2 = "value2";
    pbc.addProperty( name2, value2 );
    String name3 = "name3";
    String value3 = "value3";
    pbc.addProperty( name3, value3 );

    String value1_1 = "value1.1";
    pbc.addProperty( name1, value1_1 );
    String value2_1 = "value2.1";
    pbc.addProperty( name2, value2_1 );
    String value3_1 = "value3.1";
    pbc.addProperty( name3, value3_1 );

    assertEquals( 6, pbc.size() );

    Property property = pbc.getProperty( name1 );
    assertEquals( name1, property.getName() );
    assertEquals( value1, property.getValue() );

    property = pbc.getProperty( name2 );
    assertEquals( name2, property.getName() );
    assertEquals( value2, property.getValue() );

    property = pbc.getProperty( name3 );
    assertEquals( name3, property.getName() );
    assertEquals( value3, property.getValue() );
  }

  @Test
  public void checkSomeStuff() {
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();

    // Add three properties to container.
    String name1 = "name1";
    String value1 = "value1";
    pbc.addProperty( name1, value1 );
    String name2 = "name2";
    String value2 = "value2";
    pbc.addProperty( name2, value2 );
    String name3 = "name3";
    String value3 = "value3";
    pbc.addProperty( name3, value3 );

    String value1_1 = "value1.1";
    pbc.addProperty( name1, value1_1 );
    String value2_1 = "value2.1";
    pbc.addProperty( name2, value2_1 );
    String value3_1 = "value3.1";
    pbc.addProperty( name3, value3_1 );

    assertEquals( 6, pbc.size() );

    // Test that an unknown property is not found (returns null).
    assertNull( pbc.getProperty( "unknownName" ));

    // Test removing property.
    Property prop = pbc.getProperty( name1 );
    assertNotNull( prop );
    assertTrue( pbc.removeProperty( prop ) );
    assertEquals( 5, pbc.size());
    Property prop2 = pbc.getProperty( name1 );
    assertNotNull( prop2 );
    assertNotEquals( prop, prop2 );
  }

  @Test
  public void checkBuilderIssues() {
    MyThreddsBuilder myThreddsBuilder = new MyThreddsBuilder();
    PropertyBuilderContainer pbc = new PropertyBuilderContainer();
    pbc.setContainingBuilder( myThreddsBuilder );

    pbc.addProperty( "name", "value" );
    pbc.addProperty( "", "val" );
    pbc.addProperty( "name2", "" );
    pbc.addProperty( "name2", null );
    pbc.addProperty( null, "val2" );

    assertEquals( ThreddsBuilder.Buildable.DONT_KNOW, pbc.isBuildable() );
    BuilderIssues builderIssues = pbc.checkForIssues();
    assertEquals( ThreddsBuilder.Buildable.YES, pbc.isBuildable() );
  }

  static class MyThreddsBuilder implements ThreddsBuilder {
    MyThreddsBuilder() {}
    @Override
    public Buildable isBuildable() {
      return Buildable.YES;
    }

    @Override
    public BuilderIssues checkForIssues() {
      return new BuilderIssues();
    }

    @Override
    public Object build() throws IllegalStateException {
      return null;
    }
  }
}
