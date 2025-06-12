package vista;

import model.ModelWeatherData;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

public class Input {
    //Attributes
    private final Scanner scanner = new Scanner(System.in);
    
    //Methods

    //GET INPUT

    public int getInt(String message) {
        int num = 0;
        boolean validated = false;
        while(!validated) {
            System.out.println();
            System.out.print(message);
            try {
                num = scanner.nextInt();
                validated = true;
            } catch (Exception e) {
                System.out.println();
                System.out.println("[!] No s'ha introduït un nombre enter.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return num;
    }
    
    public int getInt(String message, int firstOption, int secondOption) {
        int num = 0;
        boolean validated = false;
        while(!validated) {
            System.out.println();
            System.out.print(message);
            try {
                num = scanner.nextInt();
                if (num == 1 || num == 0) {
                    validated = true;
                } else {
                    System.out.println();
                    System.out.println("Per favor, introdueix " + firstOption + " o " + secondOption + ".");
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("[!] No s'ha introduït un nombre enter.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return num;
    }

    public ArrayList<Integer> getInts(String message) {
        ArrayList<Integer> nums = new ArrayList<>();
        boolean keepAsking = true;
        
        System.out.println();
        System.out.println("Quan vulgues parar, introdueix un -1.");
        while(keepAsking) {
            int num = getInt(message);
            if (num == -1) {
                keepAsking = false;
            } else {
                nums.add(num);
            }
        }

        return nums;
    }

    public double getDouble(String message) {
        double num = 0.0;
        boolean validated = false;
        while(!validated) {
            System.out.println();
            System.out.print(message);
            try {
                num = scanner.nextDouble();
                validated = true;
            } catch (Exception e) {
                System.out.println();
                System.out.println("[!] No s'ha introduït un nombre real.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return num;
    }

    public String getString(String message) {
        System.out.println();
        System.out.print(message);
        return scanner.nextLine();
    }

    public ArrayList<String> getCities(String message, Boolean onlyOne) {
        ArrayList<String> cities = new ArrayList<>();

        System.out.println();
        System.out.print(message);
        String input = scanner.nextLine(); 

        if (isValidCityList(input)) {
            String[] cityArray = input.split(", ");
            for (String city : cityArray) {
                cities.add(city.trim()); 
            }
        } else {
            System.out.println("El format no és vàlid. Assegura't d'introduir les ciutats separades per coma i un espai, per exemple: Barcelona, València, Madrid.");
            return getCities(message, onlyOne); 
        }
        
        if (onlyOne) {
            ArrayList<String> singleCity = new ArrayList<>();
            if (!cities.isEmpty()) {
                singleCity.add(cities.get(0));
            }
            return singleCity; 
        }

        return cities;
    }

    private boolean isValidCityList(String input) {
        return input.matches("([A-Za-zÁÉÍÓÚáéíóúÑñ]+( [A-Za-zÁÉÍÓÚáéíóúÑñ]+)?, )*[A-Za-zÁÉÍÓÚáéíóúÑñ]+( [A-Za-zÁÉÍÓÚáéíóúÑñ]+)?");
    }

    // METHODS THAT REQUIRE USER'S INPUT

    public ModelWeatherData createWeatherData() {
        System.out.println();
        System.out.println("Creant un Weather Data: ");
        
        int recordId = getInt("Introdueix el ID: ");
        if (recordId == 0) {
            System.out.println();
            System.out.println("S'ha cancelat la creació del Weather Data.");
            return null;
        }
        String city = getString("Introdueix la cuitat: ");
        String country = getString("Introdueix el país: ");
        double latitude = getDouble("Introdueix la latitud: ");
        double longitude = getDouble("Introdueix la longitud: ");

        java.util.Date utilDate = new java.util.Date();
        Date date = new Date(utilDate.getTime());

        double temperatureCelsius = getDouble("Introdueix la temperatura que fa (en celsius): ");
        double humidityPercent = getDouble("Introdueix la humedad que fa: ");
        double precipitation = getDouble("Introdueix la precipitació que fa: ");
        double windSpeedKmh = getDouble("Introdueix les ratxes de vent: ");
        String weatherCondition = getString("Introdueix el temps que va: ");
        String forecast = getString("Introdueix el pronòstic: ");

        java.util.Date utilUpdated = new java.util.Date();
        Date updated = new Date(utilUpdated.getTime());

        return new ModelWeatherData(recordId, city, country, latitude, longitude, date, temperatureCelsius, humidityPercent, precipitation, windSpeedKmh, weatherCondition, forecast, updated);
    }

    public ArrayList<ModelWeatherData> createWeatherDatas() {
        ArrayList<ModelWeatherData> createdModelWeatherDatas = new ArrayList<>();
        boolean keepCreating = true;

        System.out.println();
        System.out.println("[!] Quan vulgues parar, introdueix un 0 en el id.");
        while(keepCreating) {
            ModelWeatherData modelWeatherData = createWeatherData();
            if (modelWeatherData == null) {
                keepCreating = false;
            } else {
                createdModelWeatherDatas.add(modelWeatherData);
            }
        }

        return createdModelWeatherDatas;
    }
    
}