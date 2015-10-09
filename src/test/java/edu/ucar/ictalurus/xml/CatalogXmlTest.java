package edu.ucar.ictalurus.xml;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import edu.ucar.ictalurus.Catalog;
import edu.ucar.ictalurus.Property;
import edu.ucar.ictalurus.builder.BuilderIssues;
import edu.ucar.ictalurus.builder.CatalogBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilder;
import edu.ucar.ictalurus.builder.ThreddsBuilderFactory;
import edu.ucar.ictalurus.testutil.CatalogTestUtils;
import edu.ucar.ictalurus.util.ThreddsCompareUtils;
import edu.ucar.ictalurus.xml.parser.ThreddsXmlParser;
import edu.ucar.ictalurus.xml.parser.ThreddsXmlParserException;
import edu.ucar.ictalurus.xml.parser.ThreddsXmlParserFactory;
import edu.ucar.ictalurus.xml.testutil.CatalogXmlAsStringUtils;


import ucar.nc2.units.DateType;
import edu.ucar.ictalurus.xml.writer.ThreddsXmlWriter;
import edu.ucar.ictalurus.xml.writer.ThreddsXmlWriterException;
import edu.ucar.ictalurus.xml.writer.ThreddsXmlWriterFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import static org.junit.Assert.*;

/**
 * _more_
 *
 * @author edavis
 * @since 4.0
 */
@RunWith(Parameterized.class)
public class CatalogXmlTest
{
  private ThreddsBuilderFactory threddsBuilderFactory;

  public CatalogXmlTest( ThreddsBuilderFactory threddsBuilderFactory ) {
    this.threddsBuilderFactory = threddsBuilderFactory;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> threddsBuilderFactoryClasses() {
    Object[][] classes = {
        {new edu.ucar.ictalurus.straightimpl.ThreddsBuilderFactoryImpl()},
        {new edu.ucar.ictalurus.simpleimpl.ThreddsBuilderFactoryImpl()}
    };
    return Arrays.asList( classes );
  }


  // Do we have all these:
    // 1) dataset with urlPath and serviceName attributes
    // 2) dataset with urlPath att and serviceName child element
    // 3) dataset with urlPath att and child metadata element with child serviceName element
    // 4) same as 3 but metadata element has inherited="true" attribute
    // 5) same as 3 but metadata element is in dataset that is parent to dataset with urlPath
    // 6) 1-5 where serviceName points to single top-level service
    // 7) 1-5 where serviceName points to compound service
    // 8) 1-5 where serviceName points to a single service contained in a compound service


    @Test
  public void parseAndWriteCatalogSkeleton()
        throws ThreddsXmlParserException, ThreddsXmlWriterException
    {
      String baseUriString = "http://cat2.CatalogXmlTest/simpleCatalog.xml";
      String name = "Cat Skeleton";
      String version = "1.0.4";
      DateType expires = new DateType( false, new Date( System.currentTimeMillis() + 60*60*1000));
      DateType lastModified = new DateType( false, new Date( System.currentTimeMillis() - 60*60*1000));

      ThreddsXmlParserFactory threddsXmlParserFactory = ThreddsXmlParserFactory.newInstance();
      threddsXmlParserFactory.setCatalogBuilderImpl( this.threddsBuilderFactory );

      actualTestFor_parseAndWriteCatalogSkeleton( threddsXmlParserFactory,
          baseUriString, name, version, expires, lastModified );
      actualTestFor_parseAndWriteCatalogSkeleton( threddsXmlParserFactory,
          baseUriString, name, version, expires, null );
      actualTestFor_parseAndWriteCatalogSkeleton( threddsXmlParserFactory,
          baseUriString, name, version, null, null );
      actualTestFor_parseAndWriteCatalogSkeleton( threddsXmlParserFactory,
          baseUriString, name, null, null, null );
      actualTestFor_parseAndWriteCatalogSkeleton( threddsXmlParserFactory,
          baseUriString, null, null, null, null );
    }

  private void actualTestFor_parseAndWriteCatalogSkeleton( ThreddsXmlParserFactory threddsXmlParserFactory,
                                                           String baseUriString, String name, String version,
                                                           DateType expires, DateType lastModified )
      throws ThreddsXmlParserException, ThreddsXmlWriterException
  {
    String xml = CatalogXmlAsStringUtils.getCatalog( name, version, expires, lastModified);

    ThreddsXmlParser cp = threddsXmlParserFactory.getParser();
    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
                                                   baseUriString );
    assertNotNull( catBuilder );

    CatalogTestUtils.assertCatalogAsExpected( catBuilder, baseUriString, name, version, expires, lastModified );

    BuilderIssues builderIssues = catBuilder.checkForIssues();
    assertEquals( ThreddsBuilder.Buildable.YES, catBuilder.isBuildable() );
    assertTrue( builderIssues.isValid() );
    Catalog catalog = catBuilder.build();

    ThreddsXmlWriter txw = ThreddsXmlWriterFactory.newInstance().createThreddsXmlWriter();

    StringWriter catWriter = new StringWriter();
    txw.writeCatalog( catalog, catWriter );

    CatalogBuilder catBuilder2 = cp.parseIntoBuilder( new StringReader( catWriter.toString()), baseUriString );
    BuilderIssues builderIssues2 = catBuilder2.checkForIssues();
    assertEquals( ThreddsBuilder.Buildable.YES, catBuilder2.isBuildable() );
    assertTrue( builderIssues2.isValid() );
    Catalog catalog2 = catBuilder2.build();

    Formatter compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareCatalogs( catalog, catalog2, compareLog ));
    //ThreddsCompareUtils.compareCatalogBuilders( catalog, catalog2 );
  }

  @Test
  public void checkParseAndWriteCatalogWithProperties()
      throws ThreddsXmlParserException, ThreddsXmlWriterException
  {
    String baseUriString = "http://cat2.CatalogXmlTest/simpleCatalog.xml";
    String name = "Cat Skeleton";
    String version = "1.0.4";
    DateType expires = new DateType( false, new Date( System.currentTimeMillis() + 60*60*1000));
    DateType lastModified = new DateType( false, new Date( System.currentTimeMillis() - 60*60*1000));

    ThreddsXmlParserFactory threddsXmlParserFactory = ThreddsXmlParserFactory.newInstance();
    threddsXmlParserFactory.setCatalogBuilderImpl( this.threddsBuilderFactory );

    List<String> propNames = new ArrayList<String>();
    List<String> propValues = new ArrayList<String>();
    propNames.add( "name1");
    propValues.add( "val1");
    propNames.add( "name2");
    propValues.add( "val2");
    propNames.add( "name3");
    propValues.add( "val3");
    propNames.add( "name2");
    propValues.add( "val2.1");
    propNames.add( "name4");
    propValues.add( "val4");
    String xml = CatalogXmlAsStringUtils.getCatalogWithProperties( name, version, expires, lastModified, propNames, propValues );

    ThreddsXmlParser cp = threddsXmlParserFactory.getParser();
    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
        baseUriString );
    assertNotNull( catBuilder );

    CatalogTestUtils.assertCatalogAsExpected( catBuilder, baseUriString, name, version, expires, lastModified );

    List<Property> properties = catBuilder.getProperties();
    assertNotNull( properties );
    assertFalse( properties.isEmpty() );
    assertEquals( 5, properties.size() );
    Property property = properties.get( 0 );
    assertEquals( "name1", property.getName());
    assertEquals( "val1", property.getValue());
    property = properties.get( 1 );
    assertEquals( "name2", property.getName());
    assertEquals( "val2", property.getValue());
    property = properties.get( 2 );
    assertEquals( "name3", property.getName());
    assertEquals( "val3", property.getValue());
    property = properties.get( 3 );
    assertEquals( "name2", property.getName());
    assertEquals( "val2.1", property.getValue() );
    property = properties.get( 4 );
    assertEquals( "name4", property.getName() );
    assertEquals( "val4", property.getValue() );

    properties = catBuilder.getProperties( "name1");
    assertEquals( 1, properties.size() );
    properties = catBuilder.getProperties( "name2");
    assertEquals( 2, properties.size() );
    properties = catBuilder.getProperties( "name3");
    assertEquals( 1, properties.size() );
    properties = catBuilder.getProperties( "name4");
    assertEquals( 1, properties.size() );


    BuilderIssues builderIssues = catBuilder.checkForIssues();
    assertEquals( ThreddsBuilder.Buildable.YES, catBuilder.isBuildable() );
    assertTrue( builderIssues.isValid() );
    Catalog catalog = catBuilder.build();

    ThreddsXmlWriter txw = ThreddsXmlWriterFactory.newInstance().createThreddsXmlWriter();

    StringWriter catWriter = new StringWriter();
    txw.writeCatalog( catalog, catWriter );

    CatalogBuilder catBuilder2 = cp.parseIntoBuilder( new StringReader( catWriter.toString()), baseUriString );
    BuilderIssues builderIssues2 = catBuilder2.checkForIssues();
    assertEquals( ThreddsBuilder.Buildable.YES, catBuilder2.isBuildable() );
    assertTrue( builderIssues2.isValid() );
    Catalog catalog2 = catBuilder2.build();

    Formatter compareLog = new Formatter();
    assertTrue( compareLog.toString(),
        ThreddsCompareUtils.compareCatalogs( catalog, catalog2, compareLog ));
    //ThreddsCompareUtils.compareCatalogBuilders( catalog, catalog2 );

  }

//  @Test
//  public void parseCatalogWithService()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String baseUriString = "http://cat2.CatalogXmlTest/CatalogWithService.xml";
//    URI docBaseUri = new URI( baseUriString );
//    DateType expires = new DateType( false, new Date( System.currentTimeMillis() + 60*60*1000));
//    String xml = CatalogXmlAsStringUtils.getCatalogWithService( expires);
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertCatalogWithServiceAsExpected( catBuilder, docBaseUri, expires);
//  }
//
//  @Test
//  public void parseCatalogWithCompoundService()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String baseUriString = "http://cat2.CatalogXmlTest/CatalogWithCompoundService.xml";
//    URI docBaseUri = new URI( baseUriString );
//    DateType expires = new DateType( false, new Date( System.currentTimeMillis() + 60*60*1000));
//    String xml = CatalogXmlAsStringUtils.getCatalogWithCompoundService( expires);
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertCatalogWithCompoundServiceAsExpected( catBuilder, docBaseUri, expires);
//  }
//
//    @Test
//    public void parseContainerDatasetWithMetadataServicename()
//            throws URISyntaxException,
//                   ThreddsXmlParserException
//    {
//        String docBaseUriString = "http://cat2.ParseContainerDatasetTest/MetadataServiceName.xml";
//        URI docBaseUri = new URI( docBaseUriString);
//        String catXml = CatalogXmlAsStringUtils.wrapThreddsXmlInCatalogDatasetMetadata( "<serviceName>odap</serviceName>\n" );
//
//        CatalogBuilder catBuilder = CatalogXmlUtils.parseCatalogIntoBuilder(cp.parseIntoBuilder(new StringReader(catXml), docBaseUri));
//        assertNotNull( catBuilder );
//    }
//
//    @Test
//  public void parseAccessibleDatasetWithRawServiceName()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlAsStringUtils.getCatalogWithSingleAccessDatasetWithRawServiceName();
//    String baseUriString = "http://cat2.ParseAccessibleDatasetTest/RawServiceName.xml";
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertCatalogHasSingleAccessDataset( catBuilder, docBaseUri );
//  }
//
//  @Test
//  public void parseAccessibleDatasetWithMetadataServiceName()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlAsStringUtils.getCatalogWithSingleAccessDatasetWithMetadataServiceName();
//    String baseUriString = "http://cat2.ParseAccessibleDatasetTest/MetadataServiceName.xml";
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertCatalogHasSingleAccessDataset( catBuilder, docBaseUri );
//  }
//
//  @Test
//  public void parseAccessibleDatasetWithInheritedMetadataServiceName()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlUtils.getCatalogWithSingleAccessDatasetWithInheritedMetadataServiceName();
//    String baseUriString = "http://cat2.ParseAccessibleDatasetTest/InheritedMetadataServiceName.xml";
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertCatalogHasSingleAccessDataset( catBuilder, docBaseUri );
//  }
//
//  @Test
//  public void parseAccessibleDatasetOldStyle()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlAsStringUtils.getCatalogWithSingleAccessDatasetOldStyle();
//    String baseUriString = "http://cat2.ParseAccessibleDatasetTest/OldStyleAccess.xml";
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertCatalogHasSingleAccessDataset( catBuilder, docBaseUri );
//  }
//
//  @Test
//  public void parseNestedDatasetWithRawServiceName()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlUtils.getNestedDatasetWithRawServiceName();
//    String baseUriString = "http://cat2.ParseNestedDatasetTest/RawServiceNameNotAccessible.xml";
//      System.out.println( "Catalog ["+baseUriString+"]:\n" + xml );
//
//
//      ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertNestedDatasetIsNotAccessible( catBuilder, docBaseUri );
//  }
//
//  @Test
//  public void parseNestedDatasetWithMetadataServiceName()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlUtils.getNestedDatasetWithMetadataServiceName();
//    String baseUriString = "http://cat2.ParseNestedDatasetTest/MetadataServiceNameNotAccessible.xml";
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertNestedDatasetIsNotAccessible( catBuilder, docBaseUri );
//  }
//
//  @Test
//  public void parseNestedDatasetWithUninheritedMetadataServiceName()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlUtils.getNestedDatasetWithUninheritedMetadataServiceName();
//    String baseUriString = "http://cat2.ParseNestedDatasetTest/UninheritedMetadataServiceNameNotAccessible.xml";
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertNestedDatasetIsNotAccessible( catBuilder, docBaseUri );
//  }
//
//  @Test
//  public void parseNestedDatasetWithInheritedMetadataServiceName()
//          throws URISyntaxException,
//                 XMLStreamException,
//                 ThreddsXmlParserException
//  {
//    String xml = CatalogXmlUtils.getNestedDatasetWithInheritedMetadataServiceName();
//    String baseUriString = "http://cat2.ParseNestedDatasetTest/InheritedMetadataServiceNameNotAccessible.xml";
//
//    ThreddsXmlParser cp = ThreddsXmlParserFactory.newInstance().getParser();
//    URI docBaseUri = new URI( baseUriString );
//    CatalogBuilder catBuilder = cp.parseIntoBuilder( new StringReader( xml ),
//                                                     docBaseUri );
//    assertNotNull( catBuilder );
//
//    CatalogXmlUtils.assertNestedDatasetIsAccessible( catBuilder, docBaseUri );
//  }
}