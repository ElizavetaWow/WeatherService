package Utils;

import Models.*;

import java.util.*;


public class Session {
    private Scanner scan = new Scanner(System.in);
    private ArrayList<String> commandList = new ArrayList<>
            (Arrays.asList("справка", "выход", "источник", "прогноз", "город"));
    private ArrayList<String> sources = new ArrayList<>
            (Arrays.asList("Погода Mail.ru", "Яндекс.Погода", "World Weather"));
    private SourceModel source;
    private PrefSettings settings;
    private Town town;


    public Session(PrefSettings settings){
        this.settings = settings;
        town = new Town(settings.getTown());
        System.out.println("Добро пожаловать в консольный сервис погоды!");
        getInstructions(false);
        check(0);
    }

    public void chooseSource(){
        System.out.println("Выберите источник из списка:");
        for (int i = 0; i < sources.size(); i++){
            System.out.println((i + 1) + ". " + sources.get(i));
        }
        System.out.println("Введите номер источника");
        check(1);
        createSourceModel();
        System.out.println("Номер источника выбран");
        check(0);
    }

    public void createSourceModel(){
        switch (settings.getSourceNum()) {
            case 1:
                source = new MailModel(town);
                break;
            case 2:
                source = new YandexModel(town);
                break;
            case 3:
                source = new WorldWeatherModel(town);
                break;
        }
    }

    public void printWeather(){
        if (source == null){
            createSourceModel();
        }
        source.getData();
        System.out.println(source.toString());
        check(0);
    }

    public void chooseTown(){
        System.out.println("Введите название города по-русски");
        check(2);
        System.out.println("Город выбран");
        check(0);
    }

    public void getInstructions(Boolean type){
        System.out.println("Данный сервис показывает текущую погоду. Текущий источник "
                + sources.get(settings.getSourceNum() - 1) + ". Текущий город "
                + town.getTown() + ". Для того, чтобы: ");
        System.out.println("- выбрать источник, введите 'источник';");
        System.out.println("- получить прогноз погоды, введите 'прогноз';");
        System.out.println("- выбрать город, введите 'город';");
        System.out.println("- вызвать эту иструкцию, введите 'справка';");
        System.out.println("- выйти из сервиса, введите 'выход'.");
        if (type){
            check(0);
        }
    }

    public void check(int type){
        if (scan.hasNext()) {
            String inputLine = scan.next();
            if (type == 0) {
                if (commandList.contains(inputLine)) {
                    switch (inputLine) {
                        case "справка":
                            getInstructions(true);
                            break;
                        case "источник":
                            chooseSource();
                            break;
                        case "город":
                            chooseTown();
                            break;
                        case "выход":
                            finish();
                            break;
                        case "прогноз":
                            printWeather();
                            break;
                    }
                } else {
                    System.out.println("Введена некорректная команда. Попробуйте ещё раз");
                    check(0);
                }
            }
            else if (type == 1){
                try{
                    if (Integer.parseInt(inputLine) <= sources.size() && 0 < Integer.parseInt(inputLine)){
                        settings.setSourceNum(Integer.parseInt(inputLine));
                    }
                    else{
                        throw new Exception();
                    }
                }
                catch (Exception e){
                    System.out.println("Введен некорректный номер. Попробуйте ещё раз");
                    check(1);
                }
            }
            else if (type == 2){
                if (!town.updateTown(inputLine)){
                    System.out.println("Введено некорректное название города. Попробуйте ещё раз");
                    check(2);
                }
                else{
                    settings.setTown(town.getTown());
                }
            }
        }
    }

    public void finish(){
        scan.close();
        System.exit(0);
    }

    public ArrayList<String> getCommandList() {
        return commandList;
    }

    public ArrayList<String> getSources() {
        return sources;
    }
}

