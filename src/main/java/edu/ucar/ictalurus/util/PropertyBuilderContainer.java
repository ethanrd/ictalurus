package edu.ucar.ictalurus.util;

import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.ThreddsCatalogIssueContainer;
import edu.ucar.ictalurus.builder.BuilderIssue;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.ThreddsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for those classes that contain properties (both Nodes and Builders):
 * <ul>
 *   <li>ServiceBuilder, DatasetNodeBuilder, and CatalogBuilder</li>
 *   <li>Service, DatasetNode, and Catalog</li>
 * </ul>
 *
 * @author edavis
 */
public class PropertyBuilderContainer // implements ThreddsBuilder
{
  private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( getClass() );

  /** List of all contained Property-s */
  private List<Property> propertiesList;
  /** Maps all unique property names to the first Property in list with that name. */
  private Map<String, Property> propertiesMap;

  private ThreddsBuilder containingBuilder;
  private ThreddsBuilder.Buildable isBuildable;
  private BuilderIssues builderIssues;

  public PropertyBuilderContainer() {
    this.isBuildable = ThreddsBuilder.Buildable.YES;
    this.propertiesList = null;
    this.propertiesMap = null;
  }

  public void setContainingBuilder( ThreddsBuilder containingBuilder) {
    this.containingBuilder = containingBuilder;
  }

  /**
   * Add a Property with the given name and value to this container.
   *
   * @param name the name of the Property to add.
   * @param value the value of the Property to add.
   */
  public void addProperty( String name, String value ) {
    this.isBuildable = ThreddsBuilder.Buildable.DONT_KNOW;
    Property property = new PropertyImpl( name, value );

    if ( this.propertiesList == null ) {
      this.propertiesList = new ArrayList<Property>();
      this.propertiesMap = new LinkedHashMap<String, Property>();
    }

    this.propertiesList.add( property);
    if ( ! this.containsProperty( name )) {
      Property previousValue = this.propertiesMap.put( name, property );
      assert null == previousValue;
    }
  }

  /**
   * Return a list of all contained Property-s.
   * @return a list of all contained Property-s.
   */
  public List<Property> getProperties() {
    if ( this.propertiesMap == null || this.propertiesList.isEmpty() )
      return Collections.emptyList();

    return Collections.unmodifiableList( this.propertiesList );
  }

  /**
   * Return a list of all the unique property names.
   * @return a list of all the unique property names.
   */
  public List<String> getPropertyNames() {
    if ( this.propertiesMap == null )
      return Collections.emptyList();
    return Collections.unmodifiableList( new ArrayList<String>( this.propertiesMap.keySet() ) );
  }

  /**
   * Return a list of all contained Property-s with the given name.
   * @param name the property name
   * @return a list of all contained Property-s with the given name.
   */
  public List<Property> getProperties( String name) {
    if ( name == null || name.isEmpty() || this.propertiesList == null
        || ! this.containsProperty( name ) ) {
      return Collections.emptyList();
    }

    List<Property> matchingProps = new ArrayList<Property>();
    for ( Property curProp : this.propertiesList ) {
      if ( curProp.getName().equals( name )) {
        matchingProps.add( curProp );
      }
    }
    return matchingProps;
  }

  /**
   * Return the first Property with the given name.
   * @param name the property name
   * @return the first Property wih the given name or null if no Property with that name exists.
   */
  public Property getProperty( String name ) {
    if ( name == null || this.propertiesList == null )
      return null;

    return this.propertiesMap.get( name );
  }

  /**
   * Remove the given Property from this container.
   *
   * @param property the Property to be removed.
   * @return true if a Property was removed, otherwise false.
   */
  public boolean removeProperty( Property property )
  {
    if ( property == null || this.propertiesList == null
        || this.propertiesList.isEmpty() )
      return false;

    boolean noDuplicatePropertyNames
        = this.propertiesList.size() == this.propertiesMap.size();

    if ( ! this.propertiesList.remove( property ) ) {
      return false;
    }
    if ( this.isBuildable == ThreddsBuilder.Buildable.NO)
      this.isBuildable = ThreddsBuilder.Buildable.DONT_KNOW;

    Property firstPropertyWithSameName = this.propertiesMap.get( property.getName() );
    if ( firstPropertyWithSameName == property ) {
      if ( ! noDuplicatePropertyNames ) {
        List<Property> properties = this.getProperties( property.getName() );
        Property oldFirstProperty = this.propertiesMap.put( property.getName(), properties.get( 0 ) );
        assert oldFirstProperty == property;
      } else {
        Property oldFirstProperty = this.propertiesMap.remove( property.getName() );
        assert oldFirstProperty == property;
      }
    }
    return true;
  }

  boolean containsProperty( String name ) {
    if ( name == null || name.isEmpty() || this.propertiesList == null )
      return false;

    return this.propertiesMap.get( name ) != null;
  }

  boolean isEmpty() {
    if ( this.propertiesList == null )
      return true;
    return this.propertiesList.isEmpty();
  }

  int size() {
    if ( this.propertiesList == null )
      return 0;
    return this.propertiesList.size();
  }

  public ThreddsCatalogIssueContainer getCatalogIssues() {
    if ( this.isBuildable != ThreddsBuilder.Buildable.YES) {
      this.checkForIssues();
    }
    return new ThreddsCatalogIssuesImpl( this.builderIssues );
  }

  public BuilderIssues checkForIssues()
  {
    builderIssues = new BuilderIssues();
    if ( this.propertiesList != null ) {
      for ( Property curProperty : this.propertiesList ) {
        if ( curProperty.getName().isEmpty() ) {
          builderIssues.addIssue( BuilderIssue.Severity.ERROR, String.format("Property has empty name [value=%s].", curProperty.getValue()), this.containingBuilder );
        }
        if (curProperty.getValue().isEmpty() ) {
          builderIssues.addIssue( BuilderIssue.Severity.WARNING, String.format("Property has empty value [name=%s].", curProperty.getName()), this.containingBuilder );
        }
      }
    }
    if ( builderIssues.isValid()) {
      this.isBuildable = ThreddsBuilder.Buildable.YES;
    } else {
      this.isBuildable = ThreddsBuilder.Buildable.NO;
    }
    return builderIssues;
  }

  public ThreddsBuilder.Buildable isBuildable() {
    return this.isBuildable;
  }
}
