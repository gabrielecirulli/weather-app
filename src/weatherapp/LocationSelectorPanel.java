package weatherapp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.rmi.CORBA.Util;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.xml.xpath.XPathExpressionException;

class LocationSelectorPanel extends JPanel implements ItemListener {
    
    private final JComboBox locationSelector;
    private Map<Integer, String> locations;
    private ForecastWindow parentWindow;
    private final JButton downButton;
    private final JButton upButton;
    private final Dimension buttonIconDimension = new Dimension( 16, 16 );
    private final Dimension buttonDimension = new Dimension( 30, 30 );
    private final JLabel currentArea;
    
    public LocationSelectorPanel( int baseWOEID ) throws
	    XPathExpressionException, Exception {
	
	this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
	
	this.currentArea = new JLabel();
	this.currentArea.setForeground( new Color( 250, 250, 250 ) );
	
	this.locationSelector = new JComboBox();
	this.locationSelector.addItemListener( this );
	
	this.downButton = this.prepareIconButton( "downArrow.png" );
	this.upButton = this.prepareIconButton( "upArrow.png" );
	
	this.setBackground( new Color( 60, 60, 60 ) );
	
	
	Utility.addComponents( this,
		new Component[]{
		    this.currentArea,
		    Box.createRigidArea( new Dimension( 5, 1 ) ),
		    this.locationSelector,
		    new Box.Filler( new Dimension( 10, 1 ), new Dimension( 10,
		    1 ), new Dimension( Short.MAX_VALUE, 1 ) ),
		    this.downButton,
		    this.upButton
		} );
	
	this.setBorder( new EmptyBorder( new Insets( 4, 10, 4, 10 ) ) );
	
	this.populateByWOEID( baseWOEID );
    }
    
    private void populateByWOEID( int WOEID ) throws XPathExpressionException,
	    Exception {
	this.locations = YahooLocationLoader.fetchSubLocations( WOEID,
		YahooLocationLoader.PROVINCE );
	this.locationSelector.
		setModel( new DefaultComboBoxModel( this.locations.values().
		toArray() ) );
	
	this.currentArea.
		setText( YahooLocationLoader.fetchLocationName( WOEID ) );
    }
    
    public void itemStateChanged( ItemEvent e ) {
	if ( e.getSource() == this.locationSelector ) {
	    System.out.println( "CHNAGED" );
	    
	    String selectedItem = ( String ) locationSelector.getSelectedItem();
	    
	    System.out.println( "SEL: " + selectedItem );
	    
	    if ( this.parentWindow == null ) {
		this.parentWindow = ( ForecastWindow ) SwingUtilities.
			getWindowAncestor( this );
	    }
	    
	    Set<Entry<Integer, String>> entrySet = this.locations.entrySet();
	    
	    for ( Entry<Integer, String> locationInfo : entrySet ) {
		System.out.println( locationInfo.getKey() + " " + locationInfo.
			getValue() );
		if ( locationInfo.getValue().equals( selectedItem ) ) {
		    try {
			this.parentWindow.showLocation( locationInfo.getKey() );
		    } catch ( Exception ex ) {
			Logger.
				getLogger( LocationSelectorPanel.class.getName() ).
				log( Level.SEVERE, null, ex );
		    }
		    return;
		}
	    }
	}
    }
    
    private JButton prepareIconButton( String name ) {
	JButton button = new JButton( Utility.prepareScaledIcon( name,
		buttonIconDimension ) );
	button.setPreferredSize( new Dimension( buttonDimension ) );
	return button;
    }
}