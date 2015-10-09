package edu.ucar.ictalurus.builder;

import edu.ucar.ictalurus.ServiceType;

import java.util.List;

/**
 * _MORE_
 *
 * @author edavis
 */
public interface ServiceBuilderContainer {

  /**
   * Add a new Service object with the given name, type, and base url to this
   * Service returning a ServiceBuilder object to allow full construction and
   * modification of the new Service.
   *
   * @param name the name of the new Service object.
   * @param type the type of the new Service object.
   * @param baseUri the base URI of the new Service object.
   * @return a ServiceBuilder for further construction and modification of the new Service.
   *
   * @throws IllegalArgumentException if the name, type, or base URI are null.
   * @throws IllegalStateException this ServiceBuilder has already been finished or the top container of this ServiceBuilder already contains a ServiceBuilder with the given name.
   */
  public ServiceBuilder addService( String name, ServiceType type, String baseUri );
  public boolean removeService( ServiceBuilder serviceBuilder );

  public List<ServiceBuilder> getServiceBuilders();

  //  public ServiceBuilder getServiceBuilderByName( String name );

  public ServiceBuilder findReferencableServiceBuilderByName( String name );

}
