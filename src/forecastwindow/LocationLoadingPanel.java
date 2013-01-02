/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Class LocationLoadingPanel
 *
 */
package forecastwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class LocationLoadingPanel extends JPanel {
    
    private JLabel loading;
    
    public LocationLoadingPanel() {
	loading = new JLabel( "Loading..." );
	loading.setFont( loading.getFont().deriveFont( 20f ) );
	loading.setHorizontalAlignment( SwingConstants.CENTER );
	this.setLayout( new BorderLayout() );
	
	this.setBackground( new Color( 240, 240, 240 ) );
	
	this.add( loading, BorderLayout.CENTER );
    }
}
