import Utils.PrefSettings;
import Utils.Session;

public class Main {


    public static void main(String[] args){
        PrefSettings settings = new PrefSettings();
        Session firstSession = new Session(settings);
    }

}
