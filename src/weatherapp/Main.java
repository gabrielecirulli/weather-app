package weatherapp;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public class Main {

    public static void main ( String[] args ) throws SAXException, IOException,
            ParserConfigurationException, XPathExpressionException, Exception {
        
	Utility.performSetup();

        // Starting the window
        ForecastWindow forecastWin = new ForecastWindow();
    }
}
