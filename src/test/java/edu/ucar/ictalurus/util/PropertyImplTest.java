package edu.ucar.ictalurus.util;

import org.junit.Test;
import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.builder.ThreddsBuilderFactory;

import static org.junit.Assert.assertEquals;

/**
 * _MORE_
 *
 * @author edavis
 */
public class PropertyImplTest {

  @Test
  public void checkCtor() {
    Property property = new PropertyImpl( "name", null );
    assertEquals( "name", property.getName());
    assertEquals( "", property.getValue());

    property = new PropertyImpl( "name", "" );
    assertEquals( "name", property.getName());
    assertEquals( "", property.getValue());

    property = new PropertyImpl( "name", "value" );
    assertEquals( "name", property.getName());
    assertEquals( "value", property.getValue());

    property = new PropertyImpl( "", "value" );
    assertEquals( "", property.getName());
    assertEquals( "value", property.getValue());

    property = new PropertyImpl( null, "value" );
    assertEquals( "", property.getName());
    assertEquals( "value", property.getValue());
  }
}
