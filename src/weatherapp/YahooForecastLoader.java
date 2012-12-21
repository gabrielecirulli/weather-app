package weatherapp;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public class YahooForecastLoader {

    private static final String forecastFormat =
	    "http://weather.yahooapis.com/forecastrss?w=%d&u=c";

    public static YahooWeather fetchForecast( int WOEID ) throws SAXException,
	    IOException, XPathExpressionException, ParserConfigurationException {
	String forecastURL = String.format( forecastFormat, WOEID );

	System.out.println( forecastURL );

	DocumentParser documentParser = new DocumentParser( forecastURL );

	YahooWeather result = new YahooWeather();

	result.setDate( documentParser.getSingleItem( "//pubDate" ) );

	result.setDescription(
		documentParser.getSingleItem( "//condition/@text" ) );

	result.setHumidity( Double.parseDouble( documentParser.getSingleItem(
		"//atmosphere/@humidity", DocumentParser.NUMERIC ) ) );

	result.setLocation( String.format( "%s, %s", documentParser.
		getSingleItem(
		"//location/@city" ),
		documentParser.getSingleItem(
		"//location/@country" ) ) );

	result.setPressure( Double.parseDouble( documentParser.getSingleItem(
		"//atmosphere/@pressure", DocumentParser.NUMERIC ) ) );

	result.setPressureDirection( Integer.parseInt( documentParser.
		getSingleItem(
		"//atmosphere/@rising" ) ) );

	result.setSunriseTime( documentParser.getSingleItem(
		"//astronomy/@sunrise" ) );

	result.setSunsetTime(
		documentParser.getSingleItem( "//astronomy/@sunset" ) );

	result.setTemperature( Double.parseDouble( documentParser.getSingleItem(
		"//condition/@temp", DocumentParser.NUMERIC ) ) );

	result.setWindDirection( Double.parseDouble( documentParser.
		getSingleItem(
		"//wind/@direction", DocumentParser.NUMERIC ) ) );

	result.setWindSpeed( Double.parseDouble( documentParser.getSingleItem(
		"//wind/@speed", DocumentParser.NUMERIC ) ) );

	return result;
    }
}
