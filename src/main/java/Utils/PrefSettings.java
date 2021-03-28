package Utils;

import java.util.prefs.Preferences;

public class PrefSettings {
    private static Preferences pref =
            Preferences.userNodeForPackage(PrefSettings.class);

    public PrefSettings() {
        int sourceNum = getSourceNum();
        String town = getTown();
    }

    public void setSourceNum(int number) {
        pref.putInt("sourceNum", number);
    }

    public int getSourceNum() {
        return pref.getInt("sourceNum", 1);
    }

    public void setTown(String town) {
        pref.put("town", town);
    }

    public String getTown() {
        return pref.get("town", "Москва");
    }
}