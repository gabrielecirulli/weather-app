package weatherapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

class YahooLocationLoader {

    // Formats
    private static final String locationAPIBaseURL =
	    "http://where.yahooapis.com/v1/";
    private static final String locationFormat = locationAPIBaseURL +
	    "place/%d?appid=%s";
    private static final String subLocationFormat = locationAPIBaseURL +
	    "place/%d/children?appid=%s";
    private static final String placeXPathFormat =
	    "//place[placeTypeName/@code = '%d']/%s";
    private static final String appID = Information.getLongAppID();
    // Type codes
    public static final int PROVINCE = 9;

    public static String fetchLocationName( int WOEID ) throws
	    ParserConfigurationException, SAXException, IOException, XPathExpressionException {
	String locationURL = String.format( locationFormat, WOEID, appID );

	DocumentParser documentParser = new DocumentParser( locationURL );

	return documentParser.getSingleItem( "//name" );
    }

    public static Map<Integer, String> fetchSubLocations( int WOEID, int type )
	    throws XPathExpressionException, Exception {

	String subLocationURL = String.
		format( subLocationFormat, WOEID, appID );
	System.out.println( subLocationURL );
	DocumentParser documentParser = new DocumentParser( subLocationURL );

	HashMap<Integer, String> result = new HashMap<Integer, String>();

	String WOEIDXPath = String.format( placeXPathFormat, type, "woeid" );
	String[] WOEIDMatched = documentParser.getAllItems( WOEIDXPath );
	final int WOEIDLength = WOEIDMatched.length;

	String locationNameXPath = String.
		format( placeXPathFormat, type, "name" );
	String[] locations = documentParser.getAllItems( locationNameXPath );

	final int locationsLength = locations.length;

	if ( locationsLength != WOEIDLength ) {
	    throw new Exception( "Risultati incoerenti trovati" );
	}

	System.out.println( "Vatrious asuhsuhs" );

	for ( int i = 0; i < WOEIDLength; i++ ) {
	    int currentWOEID = Integer.parseInt( WOEIDMatched[i] );

	    result.put( currentWOEID, locations[i] );

	    System.out.println( currentWOEID + ": " + locations[i] );
	}

	//result.put( WOEID, subLocationURL )


	//result.

	return result;
    }
}
