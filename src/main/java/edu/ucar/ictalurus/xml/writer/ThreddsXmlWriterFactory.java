package edu.ucar.ictalurus.xml.writer;

import edu.ucar.ictalurus.xml.writer.stax.StaxThreddsXmlWriter;

/**
 * _more_
 *
 * @author edavis
 */
public class ThreddsXmlWriterFactory
{
  private org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger( ThreddsXmlWriterFactory.class );

  public static ThreddsXmlWriterFactory newInstance() {
    return new ThreddsXmlWriterFactory();
  }

  private ThreddsXmlWriterFactory() {}

  public ThreddsXmlWriter createThreddsXmlWriter() {
    return new StaxThreddsXmlWriter();
  }

}
