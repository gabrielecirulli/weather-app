package forecastwindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import weatherapp.Utility;
import yahooweather.YahooLocationLoader;

public class LocationSelectorPanel extends JPanel implements ActionListener {

    int WOEID;
    private final JComboBox locationSelector;
    private Map<Integer, String> locations;
    private ForecastWindow parentWindow;
    private final JButton upButton;
    private final Dimension buttonIconDimension = new Dimension( 16, 16 );
    private final Dimension buttonDimension = new Dimension( 30, 30 );
    private final JLabel currentLocation;

    public LocationSelectorPanel() throws
	    XPathExpressionException, Exception {

	this.currentLocation = new JLabel( "Loading..." );
	this.currentLocation.setForeground( new Color( 250, 250, 250 ) );

	this.locationSelector = new JComboBox();
	this.locationSelector.addActionListener( this );

	this.upButton = this.prepareIconButton( "upArrow.png" );
	this.upButton.addActionListener( this );

	this.setBackground( new Color( 60, 60, 60 ) );

	this.setLayout( new GridBagLayout() );
	GridBagConstraints constraints = new GridBagConstraints();

	constraints.weightx = 0;
	constraints.gridx = 0;
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.anchor = GridBagConstraints.WEST;
	this.add( this.currentLocation, constraints );

	Utility.resetConstraints( constraints );
	constraints.weightx = 1;
	constraints.gridx = 1;
	constraints.anchor = GridBagConstraints.WEST;
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.gridx = 1;
	constraints.insets = new Insets( 0, 10, 0, 0 );
	this.add( this.locationSelector, constraints );

	Utility.resetConstraints( constraints );
	constraints.weightx = 0;
	constraints.gridx = 2;
	constraints.anchor = GridBagConstraints.EAST;
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.insets = new Insets( 0, 10, 0, 0 );
	this.add( this.upButton, constraints );

	this.setBorder( new EmptyBorder( new Insets( 4, 10, 4, 10 ) ) );

	final LocationSelectorPanel classReference = this;
	SwingUtilities.invokeLater( new Runnable() {
	    LocationSelectorPanel outsideClass = classReference;

	    public void run() {
		if ( outsideClass.parentWindow == null ) {
		    outsideClass.parentWindow = ( ForecastWindow ) SwingUtilities.getWindowAncestor( outsideClass );
		}
	    }
	} );

	//this.populateByWOEID( baseWOEID );
    }

    private void populateByWOEID( int WOEID ) throws XPathExpressionException,
	    Exception {
	this.WOEID = WOEID;

	this.lockElements();

	this.runFetchSubLocations( WOEID );

	this.runFetchLocationName( WOEID );
    }

    private void runFetchSubLocations( int WOEID ) {

	final int locationWOEID = WOEID;
	final LocationSelectorPanel classReference = this;
	Thread subLocationThread;
	subLocationThread = new Thread( new Runnable() {
	    int WOEID = locationWOEID;
	    LocationSelectorPanel outerClass = classReference;

	    public void run() {
		try {
		    Map<Integer, String> locations = YahooLocationLoader.fetchSubLocations( WOEID );
		    outerClass.finalizeFetchSubLocations( locations );
		} catch ( Exception ex ) {
		    Logger.getLogger( LocationSelectorPanel.class.getName() ).log( Level.SEVERE, null, ex );
		}
	    }
	} );

	subLocationThread.start();
    }

    private void finalizeFetchSubLocations( Map<Integer, String> locations ) {
	this.locations = locations;

	Object[] locationNames = this.locations.values().toArray();
	this.upButton.setEnabled( true );
	if ( locationNames.length != 0 ) {
	    this.locationSelector.setEnabled( true );
	    this.locationSelector.setModel( new DefaultComboBoxModel( this.locations.values().toArray() ) );
	} else {
	    this.locationSelector.setEnabled( false );
	    this.locationSelector.setModel( new DefaultComboBoxModel( new Object[]{
			"No locations" } ) );
	}

	if ( this.parentWindow != null ) {
	    this.parentWindow.signalLoadingComplete( ForecastWindow.LOADING_SUB_LOCATIONS );
	}
    }

    private void runFetchLocationName( int WOEID ) {

	final int locationWOEID = WOEID;
	final LocationSelectorPanel classReference = this;
	Thread locationNameThread;
	locationNameThread = new Thread( new Runnable() {
	    int WOEID = locationWOEID;
	    LocationSelectorPanel outerClass = classReference;

	    public void run() {
		try {
		    String locationName = YahooLocationLoader.fetchLocationName( WOEID );
		    outerClass.finalizeFetchLocationName( locationName );
		} catch ( Exception ex ) {
		    Logger.getLogger( LocationSelectorPanel.class.getName() ).log( Level.SEVERE, null, ex );
		}
	    }
	} );

	locationNameThread.start();
    }

    private void finalizeFetchLocationName( String locationName ) {
	this.currentLocation.setText( locationName );

	if ( this.parentWindow != null ) {
	    this.parentWindow.signalLoadingComplete( ForecastWindow.LOADING_LOCATION_NAME );
	}
    }

    public void actionPerformed( ActionEvent event ) {
	if ( event.getSource() == this.upButton ) {

	    this.runFetchParentLocation( this.WOEID );

	} else if ( event.getSource() == this.locationSelector ) {

	    String selectedItem = ( String ) locationSelector.getSelectedItem();

	    Set<Entry<Integer, String>> entrySet = this.locations.entrySet();

	    for ( Entry<Integer, String> locationInfo : entrySet ) {
		System.out.println( locationInfo.getKey() + " " + locationInfo.
			getValue() );
		if ( locationInfo.getValue().equals( selectedItem ) ) {
		    try {
			this.showLocation( locationInfo.getKey(), true );
		    } catch ( Exception ex ) {
			System.out.println( "Show location error" );
			Logger.getLogger( LocationSelectorPanel.class.getName() ).log( Level.SEVERE, null, ex );
		    }
		    return;
		}
	    }
	}
    }

    private void runFetchParentLocation( int WOEID ) {


	final int locationWOEID = WOEID;
	final LocationSelectorPanel classReference = this;
	Thread parentLocationThread;
	parentLocationThread = new Thread( new Runnable() {
	    int WOEID = locationWOEID;
	    LocationSelectorPanel outerClass = classReference;

	    public void run() {
		try {
		    int parentLocationWOEID = YahooLocationLoader.fetchParentLocation( this.WOEID );
		    outerClass.finalizeFetchParentLocation( parentLocationWOEID );
		} catch ( Exception ex ) {
		    Logger.getLogger( LocationSelectorPanel.class.getName() ).log( Level.SEVERE, null, ex );
		}
	    }
	} );

	parentLocationThread.start();
    }

    private void finalizeFetchParentLocation( int parentLocationWOEID ) throws
	    Exception {
	if ( parentLocationWOEID != 0 ) {
	    this.upButton.setEnabled( true );
	    this.showLocation( parentLocationWOEID, true );
	} else {
	    this.upButton.setEnabled( false );
	}
    }

    public void showLocation( int WOEID, boolean propagateToForecastWindow )
	    throws Exception {
	this.populateByWOEID( WOEID );

	if ( propagateToForecastWindow ) {
	    this.parentWindow.showLocation( WOEID, false );
	}
    }

    private JButton prepareIconButton( String name ) {
	JButton button = new JButton( Utility.prepareScaledIcon( name,
		buttonIconDimension ) );
	button.setPreferredSize( new Dimension( buttonDimension ) );
	return button;
    }

    private void lockElements() {
	this.locationSelector.setEnabled( false );
	this.locationSelector.setModel( new DefaultComboBoxModel( new Object[]{
		    "Loading..." } ) );

	this.currentLocation.setText( "Loading..." );
	
	this.upButton.setEnabled( false );
    }
}
