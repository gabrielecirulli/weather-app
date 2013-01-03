package forecastwindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.xml.xpath.XPathExpressionException;
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
    
    public LocationSelectorPanel( int baseWOEID ) throws
	    XPathExpressionException, Exception {
	
	this.currentLocation = new JLabel();
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
	
	this.populateByWOEID( baseWOEID );
    }
    
    private void populateByWOEID( int WOEID ) throws XPathExpressionException,
	    Exception {
	this.WOEID = WOEID;
	this.locations = YahooLocationLoader.fetchSubLocations( WOEID,
		YahooLocationLoader.PROVINCE );
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
	
	this.currentLocation.setText( YahooLocationLoader.fetchLocationName( WOEID ) );
    }
    
    public void actionPerformed( ActionEvent event ) {
	if ( event.getSource() == this.upButton ) {
	    try {
		int parentLocation = YahooLocationLoader.fetchParentLocation( this.WOEID );
		if ( parentLocation != 0 ) {
		    this.upButton.setEnabled( true );
		    this.showLocation( parentLocation );
		} else {
		    this.upButton.setEnabled( false );
		}
	    } catch ( XPathExpressionException ex ) {
		Logger.getLogger( LocationSelectorPanel.class.getName() ).
			log( Level.SEVERE, null, ex );
	    } catch ( Exception ex ) {
		Logger.getLogger( LocationSelectorPanel.class.getName() ).
			log( Level.SEVERE, null, ex );
	    }
	} else if ( event.getSource() == this.locationSelector ) {
	    
	    String selectedItem = ( String ) locationSelector.getSelectedItem();
	    
	    Set<Entry<Integer, String>> entrySet = this.locations.entrySet();
	    
	    for ( Entry<Integer, String> locationInfo : entrySet ) {
		System.out.println( locationInfo.getKey() + " " + locationInfo.
			getValue() );
		if ( locationInfo.getValue().equals( selectedItem ) ) {
		    try {
			this.showLocation( locationInfo.getKey() );
		    } catch ( Exception ex ) {
			System.out.println( "Show location error" );
			Logger.getLogger( LocationSelectorPanel.class.getName() ).log( Level.SEVERE, null, ex );
		    }
		    return;
		}
	    }
	}
    }
    
    private void showLocation( int WOEID ) throws Exception {
	this.populateByWOEID( WOEID );
	
	if ( this.parentWindow == null ) {
	    this.parentWindow = ( ForecastWindow ) SwingUtilities.
		    getWindowAncestor( this );
	}
	
	this.parentWindow.showLocation( WOEID );
    }
    
    private JButton prepareIconButton( String name ) {
	JButton button = new JButton( Utility.prepareScaledIcon( name,
		buttonIconDimension ) );
	button.setPreferredSize( new Dimension( buttonDimension ) );
	return button;
    }
}
