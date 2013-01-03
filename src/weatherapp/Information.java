package weatherapp;

// TODO: Move information to an XML file
public class Information {
    static final private int baseWOEID = 23424977; // United States
    static final private String longAppID = "upwCiUvV34FxPD9yS2K6kzLUnBdNA6_kBHk7IGuI8UNi6idv13t4Pv87JHHprzXSGh4eBbaBBJEyohVDz2pUAw1J1kPNI5k";
    static final private String appName = "Weather";

    public static String getLongAppID() {
        return longAppID;
    }

    public static int getBaseWOEID () {
        return baseWOEID;
    }

    public static String getAppName() {
	return appName;
    }
}
