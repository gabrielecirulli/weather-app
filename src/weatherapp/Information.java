package weatherapp;

// TODO: Move information to an XML file
public class Information {
    static final private int baseWOEID = 7153335; // Friuli Venezia Giulia
    static final String longAppID = "upwCiUvV34FxPD9yS2K6kzLUnBdNA6_kBHk7IGuI8UNi6idv13t4Pv87JHHprzXSGh4eBbaBBJEyohVDz2pUAw1J1kPNI5k";

    public static String getLongAppID() {
        return longAppID;
    }

    public static int getBaseWOEID () {
        return baseWOEID;
    }
}
