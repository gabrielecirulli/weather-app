package forecastwindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import yahooweather.YahooWeather;

class LocationWeatherInformationPanel extends JPanel {

    JLabel temperature = new JLabel();
    private DecimalFormat temperatureFormat = new DecimalFormat( "#.# °C" );;

    public LocationWeatherInformationPanel() {
	temperature.setFont( temperature.getFont().deriveFont( 30f ) ); // Bigger font size
	

	this.setLayout( new GridBagLayout() );
	GridBagConstraints constraints = new GridBagConstraints();

	constraints.gridx = 0;
	constraints.gridy = 0;
	this.add( temperature, constraints );
    }

    void showWeatherInformation( YahooWeather weather ) {
	temperature.setText( temperatureFormat.format( weather.
		getTemperature() ) );
    }
}
