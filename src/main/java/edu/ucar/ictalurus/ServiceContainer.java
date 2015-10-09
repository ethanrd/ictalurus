package edu.ucar.ictalurus;

import java.util.List;

/**
 * _MORE_
 *
 * @author edavis
 */
public interface ServiceContainer {
  /**
   * Return the List of Service Objects nested in this service. Nested
   * services are only allowed when this service has a "Compound" ServiceType.
   *
   * @return the List of Service Objects nested in this service, may be an empty list but not null.
   */
  public List<Service> getServices();

  //  public Service getService( String name );

  public Service findReferencableServiceByName( String name );
}
