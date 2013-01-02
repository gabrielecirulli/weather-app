package yahooweather;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import weatherapp.DocumentInfoExtractor;

public class YahooForecastLoader {

    private static final String forecastFormat =
	    "http://weather.yahooapis.com/forecastrss?w=%d&u=c";

    public static YahooWeather fetchForecast( int WOEID ) throws SAXException,
	    IOException, XPathExpressionException, ParserConfigurationException {
	String forecastURL = String.format( forecastFormat, WOEID );

	System.out.println( forecastURL );

	DocumentInfoExtractor documentInfoExtractor =
		new DocumentInfoExtractor( forecastURL );

	YahooWeather result = new YahooWeather();

	result.setDate( documentInfoExtractor.getSingleItem( "//pubDate" ) );

	result.setConditionCode( Integer.parseInt( documentInfoExtractor.
		getSingleItem(
		"//condition/@code" ) ) );

	result.setDescription(
		documentInfoExtractor.getSingleItem( "//condition/@text" ) );

	result.setHumidity( Double.parseDouble( documentInfoExtractor.
		getSingleItem(
		"//atmosphere/@humidity", DocumentInfoExtractor.NUMERIC ) ) /
		100 );

	result.setLocation( String.format( "%s, %s", documentInfoExtractor.
		getSingleItem(
		"//location/@city" ),
		documentInfoExtractor.getSingleItem(
		"//location/@country" ) ) );

	result.setPressure( Double.parseDouble( documentInfoExtractor.
		getSingleItem(
		"//atmosphere/@pressure", DocumentInfoExtractor.NUMERIC ) ) );

	result.setPressureDirection( Integer.parseInt( documentInfoExtractor.
		getSingleItem(
		"//atmosphere/@rising" ) ) );

	result.setSunriseTime( documentInfoExtractor.getSingleItem(
		"//astronomy/@sunrise" ) );

	result.setSunsetTime(
		documentInfoExtractor.getSingleItem( "//astronomy/@sunset" ) );

	result.setTemperature( Double.parseDouble( documentInfoExtractor.
		getSingleItem(
		"//condition/@temp", DocumentInfoExtractor.NUMERIC ) ) );

	result.setWindDirection( Double.parseDouble( documentInfoExtractor.
		getSingleItem(
		"//wind/@direction", DocumentInfoExtractor.NUMERIC ) ) );

	result.setWindSpeed( Double.parseDouble( documentInfoExtractor.
		getSingleItem(
		"//wind/@speed", DocumentInfoExtractor.NUMERIC ) ) );

	return result;
    }
}
