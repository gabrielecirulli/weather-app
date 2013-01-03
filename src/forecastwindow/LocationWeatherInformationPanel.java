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

    private JLabel description = new JLabel();
    private JLabel temperature = new JLabel();
    private DecimalFormat temperatureFormat = new DecimalFormat( "#.# °C" );
    private JLabel humidity = new JLabel();
    private DecimalFormat humidityFormat = new DecimalFormat( "Humidity: #.#%" );
    private JLabel pressure = new JLabel();
    private DecimalFormat pressureFormat = new DecimalFormat( "#.## mbar" );
    private JLabel wind = new JLabel();
    private DecimalFormat windDirection = new DecimalFormat( "#.#°" );
    private DecimalFormat windSpeed = new DecimalFormat( "#.# km/h" );
    private JLabel sunrise = new JLabel();
    private JLabel sunset = new JLabel();

    public LocationWeatherInformationPanel() {
	this.temperature.setFont( this.temperature.getFont().deriveFont( 30f ) ); // Bigger font size
	this.description.setFont( this.description.getFont().deriveFont( 14f ).deriveFont( Font.BOLD ) ); // Bold

	this.setLayout( new GridBagLayout() );
	GridBagConstraints constraints = new GridBagConstraints();

	constraints.gridwidth = 2;
	constraints.insets = new Insets( 10, 0, 5, 0 );
	this.add( description, constraints );

	Utility.resetConstraints( constraints );
	constraints.gridy = 2;
	constraints.anchor = GridBagConstraints.CENTER;
	this.add( temperature, constraints );

	Utility.resetConstraints( constraints );
	constraints.gridx = 1;
	constraints.gridy = 2;
	constraints.insets = new Insets( 0, 20, 0, 0 );
	this.add( humidity, constraints );

	Utility.resetConstraints( constraints );
	constraints.gridy = 3;
	constraints.gridwidth = 2;
	constraints.insets = new Insets( 10, 0, 0, 0 );
	this.add( pressure, constraints );

	Utility.resetConstraints( constraints );
	constraints.gridy = 4;
	constraints.gridwidth = 2;
	constraints.insets = new Insets( 10, 0, 0, 0 );
	this.add( wind, constraints );

	Utility.resetConstraints( constraints );
	constraints.gridy = 5;
	constraints.insets = new Insets( 10, 0, 0, 0 );
	this.add( sunrise, constraints );

	constraints.gridx = 1;
	this.add( sunset, constraints );
    }

    void showWeatherInformation( YahooWeather weather ) {
	description.setText( weather.getDescription() );
	temperature.setText( temperatureFormat.format( weather.
		getTemperature() ) );
	humidity.setText( humidityFormat.format( weather.getHumidity() ) );
	pressure.setText( String.format( "Pressure: %s (%s)", pressureFormat.format( weather.getPressure() ), ( weather.getPressureDirection() ==
		0 ? "stationary" : ( weather.getPressureDirection() == 1
		? "increasing" : "decreasing" ) ) ) );
	wind.setText( String.format( "Wind: %s at %s", windDirection.format( weather.getWindDirection() ), windSpeed.format( weather.getWindSpeed() ) ) );
	sunrise.setText( String.format( "Sunrise: %s", weather.getSunriseTime() ) );
	sunset.setText( String.format( "Sunset: %s", weather.getSunsetTime() ) );
    }
}
