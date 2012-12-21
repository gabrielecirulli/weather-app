package weatherapp;

import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

class ForecastWindow extends JFrame {
    
    private final LocationSelectorPanel locationSelector;
    private final LocationWeatherPanel locationWeather;
    private final int baseWOEID = Information.getBaseWOEID();
    
    public ForecastWindow() throws ParserConfigurationException, SAXException,
	    IOException, XPathExpressionException, Exception {
	super( "Forecast" );
	this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	this.setResizable( false );
	
	this.locationSelector = new LocationSelectorPanel(baseWOEID);
	
	this.locationWeather = new LocationWeatherPanel(baseWOEID);
	
	this.setLayout( new BoxLayout( this.getContentPane(),
		BoxLayout.Y_AXIS ) );
	
	this.add( locationSelector );
	this.add( locationWeather );
		
	this.pack();
	this.setVisible( true );
    }
    
    public void showLocation( int WOEID ) throws Exception {
	System.out.println( "Show location" );
	System.out.println( WOEID );
	
	if ( WOEID == 0 ) {
	    throw new Exception( "WOEID == 0" );
	}
	
	this.locationWeather.showLocationWeather( WOEID );
	
    }
}
