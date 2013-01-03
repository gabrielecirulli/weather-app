package yahooweather;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import weatherapp.DocumentInfoExtractor;
import weatherapp.Information;

public class YahooLocationLoader {

    // Formats
    private static final String locationAPIBaseURL =
	    "http://where.yahooapis.com/v1/";
    private static final String locationFormat = locationAPIBaseURL +
	    "place/%d?appid=%s";
    private static final String subLocationFormat = locationAPIBaseURL +
	    "place/%d/children;count=10000?appid=%s";
    private static final String parentLocationFormat = locationAPIBaseURL +
	    "place/%d/parent?appid=%s";
    private static final String placeXPathFormat =
	    "//place[(placeTypeName/@code = 8) or (placeTypeName/@code = 9) or (placeTypeName/@code = 10) or (placeTypeName/@code = 12)]/%s";
    private static final String appID = Information.getLongAppID();
    
    public static String fetchLocationName( int WOEID ) throws
	    ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {
	String locationURL = String.format( locationFormat, WOEID, appID );

	DocumentInfoExtractor documentInfoExtractor =
		new DocumentInfoExtractor( locationURL );

	return documentInfoExtractor.getSingleItem( "//name" );
    }

    public static Map<Integer, String> fetchSubLocations( int WOEID )
	    throws XPathExpressionException, Exception {

	String subLocationURL = String.
		format( subLocationFormat, WOEID, appID );
	System.out.println( subLocationURL );
	DocumentInfoExtractor documentInfoExtractor =
		new DocumentInfoExtractor( subLocationURL );

	HashMap<Integer, String> result = new HashMap<Integer, String>();

	String WOEIDXPath = String.format( placeXPathFormat, "woeid" );
	String[] WOEIDMatched = documentInfoExtractor.getAllItems( WOEIDXPath );
	final int WOEIDLength = WOEIDMatched.length;

	String locationNameXPath = String.
		format( placeXPathFormat, "name" );
	String[] locations = documentInfoExtractor.getAllItems(
		locationNameXPath );

	final int locationsLength = locations.length;

	if ( locationsLength != WOEIDLength ) {
	    throw new Exception( "Incoherent results" );
	}

	for ( int i = 0; i < WOEIDLength; i++ ) {
	    int currentWOEID = Integer.parseInt( WOEIDMatched[i] );

	    result.put( currentWOEID, locations[i] );

	    System.out.println( currentWOEID + ": " + locations[i] );
	}

	return result;
    }

    public static int fetchParentLocation( int WOEID ) throws
	    ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {
	String parentLocationURL = String.format( parentLocationFormat, WOEID, appID );
	System.out.println( parentLocationURL );

	try {
	    DocumentInfoExtractor documentInfoExtractor = new DocumentInfoExtractor( parentLocationURL );
	    return Integer.parseInt( documentInfoExtractor.getSingleItem( "//woeid" ) );
	} catch ( FileNotFoundException e ) {
	    return 0;
	}
    }
}
