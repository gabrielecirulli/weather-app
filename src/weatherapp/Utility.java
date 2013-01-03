package weatherapp;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Utility {
    
    public static URL loadResource( String resourceName ) {
	return ClassLoader.getSystemResource( resourceName );
    }
    
    public static ImageIcon prepareScaledIcon( String name, int width,
	    int height ) {
	ImageIcon originalIcon = new ImageIcon( loadResource( name ) );
	
	return new ImageIcon( originalIcon.getImage().getScaledInstance( width,
		height, Image.SCALE_SMOOTH ) );
	
    }
    
    public static ImageIcon prepareScaledIcon( String name, Dimension dimension ) {
	return prepareScaledIcon( name, dimension.width, dimension.height );
    }
    
    public static void performSetup() throws ClassNotFoundException,
	    InstantiationException, IllegalAccessException,
	    UnsupportedLookAndFeelException {
	// Locale
	Locale.setDefault( Locale.ENGLISH );

	// OS X menubars
	if ( System.getProperty( "os.name" ).equals( "Mac OS X" ) ) {
	    System.setProperty( "apple.laf.useScreenMenuBar", "true" );
	    System.setProperty( "com.apple.mrj.application.apple.menu.about.name", Information.getAppName() );
	}

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

	// Set all resources
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
    
    public static void resetConstraints( GridBagConstraints constraints ) {
	// Reset to default constraints
	constraints.gridx = GridBagConstraints.RELATIVE;
	constraints.gridy = GridBagConstraints.RELATIVE;
	constraints.gridwidth = 1;
	constraints.gridheight = 1;
	constraints.weightx = 0;
	constraints.weighty = 0;
	constraints.anchor = GridBagConstraints.CENTER;
	constraints.fill = GridBagConstraints.NONE;
	constraints.insets = new Insets( 0, 0, 0, 0 );
	constraints.ipadx = 0;
	constraints.ipady = 0;
    }
}
