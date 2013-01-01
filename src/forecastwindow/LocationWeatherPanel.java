package forecastwindow;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import weatherapp.Utility;
import yahooweather.YahooForecastLoader;
import yahooweather.YahooLocationLoader;
import yahooweather.YahooWeather;

/**
 *
 * @author 5ia
 */
class LocationWeatherPanel extends JPanel {

    JLabel locationName = new JLabel();
    JLabel weatherCondition = new JLabel();
    LocationWeatherInformationPanel informationPanel =
	    new LocationWeatherInformationPanel();

    public LocationWeatherPanel( int baseWOEID ) throws
	    ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {

	this.setBackground( new Color( 240, 240, 240 ) );

	this.setLayout( new GridBagLayout() );

	GridBagConstraints constraints = new GridBagConstraints();

	constraints.gridx = 0;
	constraints.gridy = 0;
	constraints.ipady = 20;
	this.add( locationName, constraints );

	constraints.gridy = 1;
	constraints.ipady = 0;
	this.add( weatherCondition, constraints );
	
	constraints.gridy = 2;
	constraints.ipady = 20;
	this.add( informationPanel, constraints);

	showLocationWeather( baseWOEID );
    }

    public final void showLocationWeather( int WOEID ) throws
	    ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {
	System.out.println( "Show location for: " + WOEID );
	locationName.setText( YahooLocationLoader.fetchLocationName( WOEID ) );

	YahooWeather weather = YahooForecastLoader.fetchForecast( WOEID );

	String weatherImagePath = String.format( "weather/%02d.png", 1 );
	weatherCondition.setIcon( new ImageIcon( Utility.loadResource(
		weatherImagePath ) ) );
	
	informationPanel.showWeatherInformation(weather);
    }
}
