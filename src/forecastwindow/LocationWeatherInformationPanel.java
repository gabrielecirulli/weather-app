package forecastwindow;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import weatherapp.Utility;
import yahooweather.YahooWeather;

class LocationWeatherInformationPanel extends JPanel {
    
    JLabel description = new JLabel();
    JLabel temperature = new JLabel();
    private DecimalFormat temperatureFormat = new DecimalFormat( "#.#°" );
    JLabel humidity = new JLabel();
    private DecimalFormat humidityFormat = new DecimalFormat( "Humidity: #.#%" );

    public LocationWeatherInformationPanel() {
	this.temperature.setFont( this.temperature.getFont().deriveFont( 30f ) ); // Bigger font size
	this.description.setFont( this.description.getFont().deriveFont( 14f ).deriveFont( Font.BOLD ) ); // Bold

	this.setLayout( new GridBagLayout() );
	GridBagConstraints constraints = new GridBagConstraints();

	constraints.gridwidth = 2;
	constraints.insets = new Insets( 10, 0, 5, 0);
	this.add(description, constraints);
	
	Utility.resetConstraints(constraints);
	constraints.gridy = 2;
	this.add( temperature, constraints );

	Utility.resetConstraints(constraints);
	constraints.gridx = 1;
	constraints.gridy = 2;
	constraints.insets = new Insets( 0, 20, 0, 0 );
	this.add( humidity, constraints );

    }

    void showWeatherInformation( YahooWeather weather ) {
	description.setText( weather.getDescription());	
	temperature.setText( temperatureFormat.format( weather.
		getTemperature() ) );
	humidity.setText( humidityFormat.format( weather.getHumidity() ) );
    }
}
