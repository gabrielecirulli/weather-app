package forecastwindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

class LocationWeatherPanel extends JPanel {
    
    JLabel locationName = new JLabel();
    JLabel weatherCondition = new JLabel();
    LocationWeatherInformationPanel informationPanel =
	    new LocationWeatherInformationPanel();
    
    public LocationWeatherPanel( int baseWOEID ) throws
	    ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {
	
	this.setBackground( new Color( 240, 240, 240 ) );
	
	this.locationName.setFont( this.locationName.getFont().deriveFont( 20f ).deriveFont( Font.BOLD ) );
	
	this.setLayout( new GridBagLayout() );
	
	GridBagConstraints constraints = new GridBagConstraints();
	
	Utility.resetConstraints( constraints );
	constraints.gridx = 0;
	constraints.gridy = 0;
	constraints.ipady = 20;
	this.add( locationName, constraints );
	
	Utility.resetConstraints( constraints );
	constraints.gridy = 1;
	this.add( weatherCondition, constraints );
	
	Utility.resetConstraints( constraints );
	constraints.gridy = 2;
	constraints.insets = new Insets( 10, 10, 10, 10 );
	this.add( informationPanel, constraints );
	
	showLocationWeather( baseWOEID );
    }
    
    public final void showLocationWeather( int WOEID ) throws
	    ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {
	System.out.println( "Show location for: " + WOEID );
	locationName.setText( YahooLocationLoader.fetchLocationName( WOEID ) );
	
	YahooWeather weather = YahooForecastLoader.fetchForecast( WOEID );
	
	ImageIcon weatherIcon = Utility.prepareScaledIcon( String.format( "weather/%02d.png", weather.getConditionCode() ), new Dimension( 150, 150) );
	weatherCondition.setIcon( weatherIcon );
	
	informationPanel.showWeatherInformation( weather );
    }
}
