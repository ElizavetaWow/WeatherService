package Models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class Town {
    private String townTr1;
    private String townTr2 = null;
    private String townTr3 = null;
    private String town;
    private HashMap<String, String> towns = new HashMap<>();


    public Town(String town){
        towns.putAll(Map.of("москва", "moskva", "санкт-петербург","sankt_peterburg"));
        boolean status = updateTown(town);
    }

    public String translation(){
        String URL = "https://www.homeenglish.ru/Othercityrussia.htm";
        try {
            Document html = Jsoup.connect(URL).get();
            List<String> townslist = html.body().getElementsByClass("wp-table").first().getElementsByTag("tr").eachText();
            for(String line: townslist){
                line = line.toLowerCase();
                if (line.contains(town.toLowerCase())){
                    return line.replace(town.toLowerCase() + " ", "").replace(" ", "-");
                }
            }
        } catch (IOException e) {
            return town;
        }
        return town;
    }

    public boolean updateTown(String town){
        this.town = town;
        townTr1 = translation();
        townTr2 = towns.getOrDefault(town.toLowerCase(), null);
        townTr3 = townTr1.replace("-", "_");
        return !town.equals(townTr1) || townTr2 != null;
    }

    public String getTown() {
        return town;
    }

    public String getTownTr1() {
        return townTr1;
    }

    public String getTownTr2() {
        return townTr2;
    }

    public String getTownTr3() {
        return townTr3;
    }
}
