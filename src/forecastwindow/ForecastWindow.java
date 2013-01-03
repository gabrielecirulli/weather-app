package forecastwindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import weatherapp.Information;
import weatherapp.Utility;

public class ForecastWindow extends JFrame implements ActionListener {

    private final LocationSelectorPanel locationSelector;
    private final LocationWeatherPanel locationWeather;
    private final LocationLoadingPanel locationLoading;
    private final int baseWOEID = Information.getBaseWOEID();
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem closeItem;
    public static final int LOADING_SUB_LOCATIONS = 0;
    public static final int LOADING_LOCATION_NAME = 1;
    public static final int LOADING_LOCATION_WEATHER = 2;
    public static final int CALLER_SELF = 3;
    public static final int CALLER_SELECTOR = 4;

    public ForecastWindow() throws ParserConfigurationException, SAXException,
	    IOException, XPathExpressionException, Exception {
	super( Information.getAppName() );
	this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

	this.locationSelector = new LocationSelectorPanel();
	this.locationWeather = new LocationWeatherPanel();

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

	// Menu bar for OS X only
	this.setUpMenu();

	this.setLocationRelativeTo( null );

	this.setVisible( true );

	this.showLocation( baseWOEID, true );
    }

    public void showLocation( int WOEID, boolean propagateToLocationSelector )
	    throws Exception {
	if ( propagateToLocationSelector ) {
	    this.locationSelector.showLocation( WOEID, false );
	}
	System.out.println( "Show location" );
	System.out.println( WOEID );

	if ( WOEID == 0 ) {
	    throw new Exception( "WOEID == 0" );
	}

	this.locationWeather.showLocationWeather( WOEID );
	this.locationLoading.setPreferredSize( this.locationWeather.getSize() );
	this.locationWeather.setVisible( false );
	this.locationLoading.setVisible( true );

	this.validate();
	this.pack();

    }

    public void actionPerformed( ActionEvent event ) {
	if ( event.getSource() == this.closeItem ) {
	    this.dispatchEvent( new WindowEvent( this, WindowEvent.WINDOW_CLOSING ) );
	}
    }

    private void setUpMenu() {
	if ( System.getProperty( "os.name" ).equals( "Mac OS X" ) ) {
	    this.menuBar = new JMenuBar();
	    this.fileMenu = new JMenu( "File" );
	    this.menuBar.add( fileMenu );

	    this.closeItem = new JMenuItem( "Close Window" );
	    this.closeItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ) );
	    this.closeItem.addActionListener( this );
	    this.fileMenu.add( this.closeItem );

	    this.setJMenuBar( this.menuBar );
	}
    }

    void signalLoadingComplete( int loadingType ) {
	if ( loadingType == ForecastWindow.LOADING_LOCATION_WEATHER ) {
	    this.locationWeather.setVisible( true );
	    this.locationLoading.setVisible( false );
	}

	final ForecastWindow classReference = this;
	SwingUtilities.invokeLater( new Runnable() {
	    ForecastWindow outerClass = classReference;

	    public void run() {
		outerClass.validate();
		outerClass.pack();
	    }
	} );

    }
;
}
