package edu.ucar.ictalurus.util;

import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.Service;
import edu.ucar.ictalurus.builder.CatalogBuilder;
import edu.ucar.ictalurus.Catalog;

import java.util.Formatter;
import java.util.List;

/**
 * _MORE_
 *
 * @author edavis
 */
public class ThreddsCompareUtils {

  public static boolean compareCatalogBuilders( CatalogBuilder orginalCB, CatalogBuilder altCB, Formatter compareLog) {
    if ( compareLog == null )
      throw new IllegalArgumentException( "Must supply a compareLog." );

    ThreddsCompareUtils threddsCompareUtils = new ThreddsCompareUtils( compareLog);
    return threddsCompareUtils.doCompareCatalogBuilders( orginalCB, altCB );
  }

  public static boolean compareCatalogs( Catalog orginalCat, Catalog altCat, Formatter compareLog ) {
    if ( compareLog == null )
      throw new IllegalArgumentException( "Must supply a compareLog." );

    ThreddsCompareUtils threddsCompareUtils = new ThreddsCompareUtils( compareLog);
    return threddsCompareUtils.doCompareCatalogs( orginalCat, altCat );
  }

  public static boolean compareProperties( Property originalProperty, Property altProperty, Formatter compareLog ) {
    if ( compareLog == null )
      throw new IllegalArgumentException( "Must supply a compareLog." );

    ThreddsCompareUtils threddsCompareUtils = new ThreddsCompareUtils( compareLog);
    return threddsCompareUtils.doCompareProperties( originalProperty, altProperty );
  }

  public static boolean compareServices( Service originalService, Service altService, Formatter compareLog) {
    if ( compareLog == null )
      throw new IllegalArgumentException( "Must supply a compareLog." );

    ThreddsCompareUtils threddsCompareUtils = new ThreddsCompareUtils( compareLog);
    return threddsCompareUtils.doCompareServices( originalService, altService );
  }

  private Formatter comparisonLog;
  private boolean showCompare = false;
  private ThreddsCompareUtils( Formatter compareLog) {
    this.comparisonLog = compareLog;
  }

  private boolean doCompareCatalogBuilders( CatalogBuilder originalCB, CatalogBuilder altCB) {
    if ( originalCB == null || altCB == null )
      throw new IllegalArgumentException( "Catalogs to compare may not be null." );

    boolean ok = true;
    if ( ! originalCB.getDocBaseUriAsString().equals( originalCB.getDocBaseUriAsString() )) {
      this.comparisonLog.format( "Catalog DocBase URIs not the same: first [%s], second [%s]",
          originalCB.getDocBaseUriAsString(), altCB.getDocBaseUriAsString());
      ok = false;
    }
    if ( ! originalCB.getName().equals( altCB.getName() )) {
      this.comparisonLog.format( "Catalog names not the same; first [%s], second [%s]",
          originalCB.getName(), altCB.getName());
      ok = false;
    }
    if ( ! originalCB.getVersion().equals( altCB.getVersion() )) {
      this.comparisonLog.format( "Catalog versions not the same; first [%s], second [%s]",
          originalCB.getVersion(), altCB.getVersion());
      ok = false;
    }
    if ( ! originalCB.getExpires().equals( altCB.getExpires() )) {
      this.comparisonLog.format( "Catalog expires dates not the same; first [%s], second [%s]",
          originalCB.getExpires(), altCB.getExpires());
      ok = false;
    }
    if ( ! originalCB.getLastModified().equals( altCB.getLastModified() )) {
      this.comparisonLog.format( "Catalog lastMod dates not the same; first [%s], second [%s]",
          originalCB.getLastModified(), altCB.getLastModified());
      ok = false;
    }

    // for ( Property curProperty : originalCB.getProperties() ) {
    //   ok &= doCompareProperties(... );
    // }

    return ok;
  }

  private boolean doCompareCatalogs( Catalog originalCat, Catalog altCat ) {
    if ( originalCat == null || altCat == null )
      throw new IllegalArgumentException( "Catalogs to compare may not be null." );

    boolean ok = true;
    if ( ! originalCat.getDocBaseUri().equals( originalCat.getDocBaseUri() )) {
      this.comparisonLog.format( "Catalog DocBase URIs not the same: first [%s], second [%s]",
          originalCat.getDocBaseUri(), altCat.getDocBaseUri() );
      ok = false;
    }
    if ( ! originalCat.getName().equals( altCat.getName() )) {
      this.comparisonLog.format( "Catalog names not the same; first [%s], second [%s]",
          originalCat.getName(), altCat.getName());
      ok = false;
    }
    if ( ! originalCat.getVersion().equals( altCat.getVersion() )) {
      this.comparisonLog.format( "Catalog versions not the same; first [%s], second [%s]",
          originalCat.getVersion(), altCat.getVersion());
      ok = false;
    }
    if ( ! originalCat.getExpires().equals( altCat.getExpires() )) {
      this.comparisonLog.format( "Catalog expires dates not the same; first [%s], second [%s]",
          originalCat.getExpires(), altCat.getExpires());
      ok = false;
    }
    if ( ! originalCat.getLastModified().equals( altCat.getLastModified() )) {
      this.comparisonLog.format( "Catalog lastMod dates not the same; first [%s], second [%s]",
          originalCat.getLastModified(), altCat.getLastModified());
      ok = false;
    }

    List<Property> originalProperties = originalCat.getProperties();
    List<Property> altProperties = altCat.getProperties();
    ok &= doCompareListOfProperties( originalProperties, altProperties );

    List<Service> originalServices = originalCat.getServices();
    List<Service> altServices = altCat.getServices();
    ok &= doCompareListOfServices( originalServices, altServices );

    return ok;
  }

  private boolean doCompareProperties( Property originalProperty, Property altProperty ) {
    if ( originalProperty == null || altProperty == null )
      throw new IllegalArgumentException( "Properties to compare may not be null." );

    boolean ok = true;
    if ( ! originalProperty.getName().equals( altProperty.getName() )) {
      this.comparisonLog.format( "Property names not the same: first [%s], second [%s]",
          originalProperty.getName(), altProperty.getName() );
      ok = false;
    }
    if ( ! originalProperty.getValue().equals( altProperty.getValue() )) {
      this.comparisonLog.format( "Property values not the same; first [%s], second [%s]",
          originalProperty.getValue(), altProperty.getValue());
      ok = false;
    }

    return ok;
  }

  private boolean doCompareListOfProperties( List<Property> originalProperties, List<Property> altProperties ) {
    if ( originalProperties == null || altProperties == null )
      throw new IllegalArgumentException( "Lists of Properties to compare may not be null." );

    boolean ok = true;
    if ( originalProperties.size() != altProperties.size() ) {
      this.comparisonLog.format( "Catalog properties not the same size: first[%s], second [%s]",
                                 originalProperties.size(), altProperties.size());
      ok = false;
    } else {
      for ( int i = 0; i < originalProperties.size(); i++ ) {
        Property curOrigProp = originalProperties.get( i );
        Property curAltProp = altProperties.get( i );
        ok &= doCompareProperties( curOrigProp, curAltProp );
      }
    }

    return ok;
  }

  private boolean doCompareServices( Service originalService, Service altService) {
    if (originalService == null || altService == null )
      throw new IllegalArgumentException( "Services to compare may not be null." );

    boolean ok = true;
    if ( ! originalService.getName().equals( altService.getName() )) {
      this.comparisonLog.format( "Service names not the same: first [%s], second [%s]", originalService.getName(), altService.getName() );
      ok = false;
    }
    if ( ! originalService.getType().equals( altService.getType() )) {
      this.comparisonLog.format( "Service types not the same: first [%s], second [%s]", originalService.getType(), altService.getType() );
      ok = false;
    }
    if ( ! originalService.getBaseUri().equals( altService.getBaseUri() )) {
      this.comparisonLog.format( "Service baseUri-s not the same: first [%s], second [%s]", originalService.getBaseUri(), altService.getBaseUri() );
      ok = false;
    }
    if ( ! originalService.getDescription().equals( altService.getDescription() )) {
      this.comparisonLog.format( "Service descriptions not the same: first [%s], second [%s]", originalService.getDescription(), altService.getDescription() );
      ok = false;
    }
    if ( ! originalService.getSuffix().equals( altService.getSuffix() )) {
      this.comparisonLog.format( "Service suffixes not the same: first [%s], second [%s]", originalService.getSuffix(), altService.getSuffix() );
      ok = false;
    }

    List<Property> originalProperties = originalService.getProperties();
    List<Property> altProperties = altService.getProperties();
    if ( ! doCompareListOfProperties( originalProperties, altProperties ) ) {
      ok = false;
    }
    ok &= doCompareListOfProperties( originalProperties, altProperties );

    if ( ! originalService.getSuffix().equals( altService.getSuffix() )) {
      this.comparisonLog.format( "Service suffixes not the same: first [%s], second [%s]", originalService.getSuffix(), altService.getSuffix() );
      ok = false;
    }

    return ok;
  }

  private boolean doCompareListOfServices( List<Service> originalServices, List<Service> altServices ) {
    if ( originalServices == null || altServices == null )
      throw new IllegalArgumentException( "Lists of Services to compare may not be null." );

    boolean ok = true;
    if ( originalServices.size() != altServices.size() ) {
      this.comparisonLog.format( "Catalogs have different number of services: first[%s], second [%s]",
                                 originalServices.size(), altServices.size());
      ok = false;
    } else {
      for ( int i = 0; i < originalServices.size(); i++ ) {
        Service curOriginalService = originalServices.get( i );
        Service curAltService = altServices.get( i );
        ok &= doCompareServices( curOriginalService, curAltService );
      }
    }

    return ok;
  }
}
