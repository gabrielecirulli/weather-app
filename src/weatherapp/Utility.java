/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Class Utility
 *
 */
package weatherapp;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Utility {

    static ImageIcon prepareScaledIcon( String name, int width, int height ) {
	ImageIcon originalIcon = new ImageIcon( ClassLoader.getSystemResource(
		name ) );

	return new ImageIcon( originalIcon.getImage().getScaledInstance( width,
		height, Image.SCALE_SMOOTH ) );

    }

    static ImageIcon prepareScaledIcon( String name, Dimension dimension ) {
	return prepareScaledIcon( name, dimension.width, dimension.height );
    }

    static void addComponents( Container destination,
	    Component[] components ) {
	for ( Component component : components ) {
	    destination.add( component );
	}
    }

    static void performSetup() throws ClassNotFoundException,
	    InstantiationException, IllegalAccessException,
	    UnsupportedLookAndFeelException {
	// Look and feel
	UIManager.setLookAndFeel( "com.seaglasslookandfeel.SeaGlassLookAndFeel" );
	
	
	
	
	// Fonts
	String[] allFonts =
		GraphicsEnvironment.getLocalGraphicsEnvironment().
		getAvailableFontFamilyNames();

	String selectedFont = null;
	for ( String fontName : allFonts ) {
	    if ( fontName.equals( "Helvetica Neue" ) ) {
		selectedFont = "Helvetica Neue";
		break;
	    } else if ( fontName.equals( "Helvetica" ) ) {
		selectedFont = "Helvetica";
		break;
	    } else if ( fontName.equals( "Arial" ) ) {
		selectedFont = "Arial";
		break;
	    }
	}
	if ( selectedFont == null ) {
	    selectedFont = "SansSerif";
	}

	Font uiFont = new Font( selectedFont, Font.PLAIN, 13 );
	UIManager.put( "Button.font", uiFont );
	UIManager.put( "ToggleButton.font", uiFont );
	UIManager.put( "RadioButton.font", uiFont );
	UIManager.put( "CheckBox.font", uiFont );
	UIManager.put( "ColorChooser.font", uiFont );
	UIManager.put( "ComboBox.font", uiFont );
	UIManager.put( "Label.font", uiFont );
	UIManager.put( "List.font", uiFont );
	UIManager.put( "MenuBar.font", uiFont );
	UIManager.put( "MenuItem.font", uiFont );
	UIManager.put( "RadioButtonMenuItem.font", uiFont );
	UIManager.put( "CheckBoxMenuItem.font", uiFont );
	UIManager.put( "Menu.font", uiFont );
	UIManager.put( "PopupMenu.font", uiFont );
	UIManager.put( "OptionPane.font", uiFont );
	UIManager.put( "Panel.font", uiFont );
	UIManager.put( "ProgressBar.font", uiFont );
	UIManager.put( "ScrollPane.font", uiFont );
	UIManager.put( "Viewport.font", uiFont );
	UIManager.put( "TabbedPane.font", uiFont );
	UIManager.put( "Table.font", uiFont );
	UIManager.put( "TableHeader.font", uiFont );
	UIManager.put( "TextField.font", uiFont );
	UIManager.put( "PasswordField.font", uiFont );
	UIManager.put( "TextArea.font", uiFont );
	UIManager.put( "TextPane.font", uiFont );
	UIManager.put( "EditorPane.font", uiFont );
	UIManager.put( "TitledBorder.font", uiFont );
	UIManager.put( "ToolBar.font", uiFont );
	UIManager.put( "ToolTip.font", uiFont );
	UIManager.put( "Tree.font", uiFont );
    }
}