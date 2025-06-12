package vista;

import model.ModelWeatherData;
import java.util.ArrayList;


public class ViewWeatherData { 
    //Attributes
    private final Input input = new Input();

    //Methods
    public void showWeatherData(ModelWeatherData modelWeatherData) {
        if (modelWeatherData == null) {
            return;
        }

        System.out.println(modelWeatherData.toString());
    }

    public void showWeatherDatas(ArrayList<ModelWeatherData> modelWeatherData) {
        for (int i = 0; i < modelWeatherData.size(); i++) {
            if (i % 4 == 0 && i > 0) {
                input.getString("Presiona 'Intro' per a continuar... ");
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Weather Data " + (i + 1) + ":");
            showWeatherData(modelWeatherData.get(i));
        }
    }
}