package edu.ucar.ictalurus.xml.parser;

import edu.ucar.ictalurus.builder.ThreddsBuilderFactory;
import edu.ucar.ictalurus.xml.parser.stax.StaxThreddsXmlParser;

/**
 * _MORE_
 *
 * @author edavis
 */
public class ThreddsXmlParserFactory  {

  private ThreddsBuilderFactory threddsBuilderFactory = null;

  public static ThreddsXmlParserFactory newInstance() {
    return new ThreddsXmlParserFactory();
  }
  private ThreddsXmlParserFactory() {}

  public void setCatalogBuilderImpl( ThreddsBuilderFactory threddsBuilderFactory) {
    this.threddsBuilderFactory = threddsBuilderFactory;
  }

  public ThreddsXmlParser getParser() {
    return StaxThreddsXmlParser.newInstance(
        this.threddsBuilderFactory != null
            ? this.threddsBuilderFactory
            : new edu.ucar.ictalurus.straightimpl.ThreddsBuilderFactoryImpl() );
  }
}
