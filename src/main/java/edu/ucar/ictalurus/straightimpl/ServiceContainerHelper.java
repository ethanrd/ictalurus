/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package edu.ucar.ictalurus.straightimpl;

import edu.ucar.ictalurus.Service;
import edu.ucar.ictalurus.builder.ServiceBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilder;

import java.util.*;

/**
 * Helper class for those classes that contain services: CatalogImpl and ServiceImpl.
 *
 * @author edavis
 */
public class ServiceContainerHelper
{
  private final List<Service> services;
  //private final CatalogWideServiceTracker catalogWideServiceTracker;

//  ServiceContainerHelper( List<ServiceBuilder> serviceBuilderList ) {
//    this(serviceBuilderList, new CatalogWideServiceTracker());
//  }

  ServiceContainerHelper( List<ServiceBuilder> serviceBuilderList )//, CatalogWideServiceBuilderTracker cwsbt)
  {
    if ( serviceBuilderList == null || serviceBuilderList.isEmpty() ) {
      this.services = null;
      //this.catalogWideServiceTracker = null;
    }
    else {
      List<Service> tmpServiceList = new ArrayList<Service>();
      Map<String,Service> referencableServicesMap = new HashMap<String, Service>();
      for ( ServiceBuilder curServiceBuilder : serviceBuilderList) {
        if ( curServiceBuilder.isBuildable() != ThreddsBuilder.Buildable.YES ) {
          throw new IllegalArgumentException( "Can't construct ServiceContainerHelper with ServiceBuilders [" + curServiceBuilder.getName() + "] that are not buildable.");
        }
        tmpServiceList.add( curServiceBuilder.build());
      }
      this.services = tmpServiceList;
    }
  }

//  ServiceContainerHelper( List<ServiceBuilder> serviceBuilderList, CatalogWideServiceBuilderTracker cwsbt,
//                    CatalogWideServiceTracker catalogWideServiceTracker ) {
//
//  }

  boolean isEmpty() {
    return this.services == null || this.services.isEmpty();
  }

  int size() {
    if ( this.services == null )
      return 0;
    return this.services.size();
  }

  List<Service> getServices() {
    if ( this.services == null )
      return Collections.emptyList();
    return Collections.unmodifiableList( new ArrayList<Service>( this.services ) );
  }

  public Service findReferencableServiceByName( String serviceName) {
    //return this.catalogWideServiceTracker.getReferenceableService( serviceName );
    for (Service curSB : this.services ) {
      if ( curSB.getName().equals( serviceName ) )
        return curSB;
    }
    return null;
  }

}