package edu.ucar.ictalurus.util;

import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.ThreddsCatalogIssueContainer;

/**
 * _more_
 *
 * @author edavis
 * @since 4.0
 */
public class PropertyImpl implements Property {
  private final String name;
  private final String value;

  PropertyImpl( String name, String value ) {
//    if ( name == null || name.isEmpty())
//      throw new IllegalArgumentException( "Name must not be null or empty.");
    this.name = name != null ? name : "";
    this.value = value != null ? value : "";
  }

  public String getName() {
    return this.name;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public boolean equals( Object o )
  {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;

    PropertyImpl property = (PropertyImpl) o;

    if ( !name.equals( property.name ) ) return false;
    if ( !value.equals( property.value ) ) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = name.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }
}
