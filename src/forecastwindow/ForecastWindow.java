package forecastwindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import weatherapp.Information;
import weatherapp.Utility;

public class ForecastWindow extends JFrame {
    
    private final LocationSelectorPanel locationSelector;
    private final LocationWeatherPanel locationWeather;
    private final LocationLoadingPanel locationLoading;
    private final int baseWOEID = Information.getBaseWOEID();
    
    public ForecastWindow() throws ParserConfigurationException, SAXException,
	    IOException, XPathExpressionException, Exception {
	super( "Forecast" );
	this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	//this.setResizable( false );

	this.locationSelector = new LocationSelectorPanel( baseWOEID );
	
	this.locationWeather = new LocationWeatherPanel( baseWOEID );
	
	this.locationLoading = new LocationLoadingPanel();
	
	this.setLayout( new GridBagLayout() );
	
	GridBagConstraints constraints = new GridBagConstraints();
	
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.anchor = GridBagConstraints.PAGE_START;
	this.add( locationSelector, constraints );
	
	Utility.resetConstraints( constraints );
	constraints.weightx = 1;
	constraints.weighty = 1;
	constraints.gridy = 1;
	constraints.fill = GridBagConstraints.BOTH;
	constraints.gridheight = GridBagConstraints.REMAINDER;
	this.add( locationWeather, constraints );
	
	this.add( locationLoading, constraints ); // Add loading panel with same constraints
	locationLoading.setVisible( false ); // Hide loading panel

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
	/*this.locationLoading.setPreferredSize( this.locationWeather.getSize() );
	this.locationWeather.setVisible( false );
	this.locationLoading.setVisible( true );*/
	
	this.validate();
	this.pack();
    }
}
