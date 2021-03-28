package Models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailModel implements SourceModel {
    private Town town;
    private String temperature;
    private String wind;
    private String humidity;
    private String pressure;
    private final String URL = "https://pogoda.mail.ru/prognoz/";
    private boolean status;

    public MailModel(Town town){
        this.town = town;
        this.status = false;
        getData();
    }

    private String parseData(String town) {
        String locURL = URL + town + "/";
        try {
            Document html = Jsoup.connect(locURL).get();
            List<String> argslist = html.body().getElementsByClass("information__content__additional_second").
                    first().getElementsByTag("span").eachText();
            temperature = html.body().getElementsByClass("information__content__temperature").text();
            wind = argslist.get(5).split(" ")[0] + " м/с";
            humidity = argslist.get(3).split(" ")[0];
            pressure = argslist.get(0).split(" ")[0] + " мм рт. ст.";
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
            return "Погода Mail.ru сообщает, что сейчас в городе " + town.getTown() + '\n' +
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
}
