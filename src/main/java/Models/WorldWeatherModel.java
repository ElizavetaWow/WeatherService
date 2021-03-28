package Models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldWeatherModel implements SourceModel {
    private Town town;
    private String temperature;
    private String wind;
    private String humidity;
    private String pressure;
    private final String URL = "https://world-weather.ru/pogoda/russia/";
    private boolean status;

    public WorldWeatherModel(Town town) {
        this.town = town;
        getData();
    }

    public String parseData(String town) {
        String locURL = URL + town + "/";
        try {
            Document html = Jsoup.connect(locURL).get();
            temperature = html.body().getElementById("weather-now-number").text();
            List<String> argslist = html.body().getElementById("weather-now-description").getElementsByTag("dd").eachText();
            wind = argslist.get(2);
            humidity = argslist.get(3);
            pressure = argslist.get(1);
            return "";
        } catch (IOException e) {
            return "Сервис не доступен";
        }
    }

    @Override
    public void getData() {
        ArrayList<String> answers = new ArrayList<>();
        answers.add(parseData(town.getTownTr1()));
        answers.add(parseData(town.getTownTr2()));
        answers.add(parseData(town.getTownTr3()));
        status = answers.contains("");
    }

    @Override
    public String toString() {
        if (status) {
            return "World Weather сообщает, что сейчас в городе " + town.getTown() + '\n' +
                    "Температура: " + temperature + '\n' +
                    "Ветер: " + wind + '\n' +
                    "Влажность: " + humidity + '\n' +
                    "Давление: " + pressure + '\n';
        }
        else{
            return "Сервис "+ URL +" не даёт ответ";
        }
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWind() {
        return wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public Town getTown() {
        return town;
    }

    public String getURL() {
        return URL;
    }
}
