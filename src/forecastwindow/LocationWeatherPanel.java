/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forecastwindow;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 5ia
 */
class LocationWeatherPanel extends JPanel {

    
    
    public LocationWeatherPanel(int baseWOEID) {
        //this.setPreferredSize( new Dimension(300, 300) );
	
	this.setBackground( new Color(240, 240, 240) );
	
	JLabel label = new JLabel("Forecast goes here");
	
	this.add( label);
        
    }

    void showLocationWeather(int WOEID) {
        
        System.out.println("Showuld show location for " + WOEID);
    }
    
}
