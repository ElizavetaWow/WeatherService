package Models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class YandexModel implements SourceModel {
    private Town town;
    private String temperature;
    private String wind;
    private String humidity;
    private String pressure;
    private final String URL = "https://yandex.ru/pogoda/";
    private boolean status;

    public YandexModel(Town town) {
        this.town = town;
        getData();
    }


    public String parseData(String town) {
        String locURL = URL + town + "/";
        try {
            Document html = Jsoup.connect(locURL).get();
            temperature = html.body().getElementsByClass("fact__temp").first()
                    .getElementsByClass("temp__value").text() + "°";
            wind = html.body().getElementsByClass("fact__wind-speed").first()
                    .getElementsByClass("term__value").text();
            wind = wind.replaceFirst(",", ".");
            humidity = html.body().getElementsByClass("fact__humidity").first()
                    .getElementsByClass("term__value").text();
            pressure = html.body().getElementsByClass("fact__pressure").first()
                    .getElementsByClass("term__value").text();
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
            return "Яндекс.Погода сообщает, что сейчас в городе " + town.getTown() + '\n' +
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

    public String getURL() {
        return URL;
    }

    public Town getTown() {
        return town;
    }
}
