package weatherapp;

import forecastwindow.ForecastWindow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public class Main {

    public static void main( String[] args ) throws SAXException, IOException,
	    ParserConfigurationException, XPathExpressionException, Exception {

	Utility.performSetup();

	// Starting the window
	SwingUtilities.invokeLater( new Runnable() {
	    public void run() {
		try {
		    ForecastWindow forecastWindow = new ForecastWindow();
		} catch ( ParserConfigurationException ex ) {
		    Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
		} catch ( SAXException ex ) {
		    Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
		} catch ( IOException ex ) {
		    Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
		} catch ( XPathExpressionException ex ) {
		    Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
		} catch ( Exception ex ) {
		    Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
		}
	    }
	} );
    }
}
