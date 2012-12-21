package weatherapp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	//this.setResizable( false );

	this.locationSelector = new LocationSelectorPanel( baseWOEID );

	this.locationWeather = new LocationWeatherPanel( baseWOEID );

	this.setLayout( new GridBagLayout() );

	GridBagConstraints constraints = new GridBagConstraints();
	
	constraints.weightx = 0;
	constraints.weighty = 0;
	constraints.gridx = 0;
	constraints.gridy = 0;
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.anchor = GridBagConstraints.PAGE_START;
	this.add( locationSelector, constraints );

	constraints.weightx = 1;
	constraints.weighty = 1;
	constraints.gridx = 0;
	constraints.gridy = 1;
	constraints.fill = GridBagConstraints.BOTH;
	constraints.gridheight = GridBagConstraints.REMAINDER;
	this.add( locationWeather, constraints );

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
