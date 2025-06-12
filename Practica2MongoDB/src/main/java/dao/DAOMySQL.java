
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static main.ConnectionMySQL.mySQLConnection;
import model.ModelWeatherData;

public class DAOMySQL {

    private String query;

    private boolean DAOexistsCity(String city) {
        try {
            String query = "SELECT * FROM weatherdata WHERE city = ?";
            PreparedStatement ps = mySQLConnection.prepareStatement(query);
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public int DAOgetCurrentItems() {
        int currentItems = 0;
        String query = "SELECT COUNT(*) AS total FROM weatherdata";

        try {
            PreparedStatement ps = mySQLConnection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                currentItems = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("No s'ha pogut aconseguir el nombre total d'ítems.");
        }
        return currentItems;
    }

    // CRUD

    // CREATE

    public boolean DAOinsertItem(ModelWeatherData createdWeatherData) {
        if (createdWeatherData == null) {
            System.out.println();
            System.out.println("No pots inserir un ítem buit, necessites introduir una ciutat.");
            return false;
        }
        if (DAOexistsCity(createdWeatherData.getCity())) {
            System.out.println();
            System.out.println("Ja existeix un ítem amb la ciutat " + createdWeatherData.getCity() + ".");
            return false;
        }
        try {
            String query = "INSERT INTO weatherdata (record_Id, city, country, latitude, longitude, date, temperature_celsius, humidity_percent, precipitation_mm, wind_speed_kmh, weather_condition, forecast, updated) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = mySQLConnection.prepareStatement(query);

            ps.setInt(1, createdWeatherData.getRecordId());
            ps.setString(2, createdWeatherData.getCity());
            ps.setString(3, createdWeatherData.getCountry());
            ps.setDouble(4, createdWeatherData.getLatitude());
            ps.setDouble(5, createdWeatherData.getLongitude());

            java.util.Date utilDate = createdWeatherData.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ps.setDate(6, sqlDate);

            ps.setDouble(7, createdWeatherData.getTemperatureCelsius());
            ps.setDouble(8, createdWeatherData.getHumidityPercent());
            ps.setDouble(9, createdWeatherData.getPrecipitation());
            ps.setDouble(10, createdWeatherData.getWindSpeedKmh());
            ps.setString(11, createdWeatherData.getWeatherCondition());
            ps.setString(12, createdWeatherData.getForecast());

            java.util.Date utilUpdated = createdWeatherData.getUpdated();
            java.sql.Date sqlUpdated = new java.sql.Date(utilUpdated.getTime());
            ps.setDate(13, sqlUpdated);

            ps.executeUpdate();

            System.out.println();
            System.out.println("S'ha inserit l'ítem amb èxit.");

            return true;

        } catch (SQLException e) {
            System.out.println();
            System.out.println("No s'ha pogut inserir l'ítem." + e.getMessage());
            return false;
        }
    }

    public void DAOinsertVariousItems(ArrayList<ModelWeatherData> createdWeatherData) {
        int numInserted = 0;

        for (ModelWeatherData city : createdWeatherData) {
            if (DAOinsertItem(city)) {
                numInserted++;
            }
        }

        System.out.println();
        System.out.println("S'han inserit " + numInserted + " ciutats");
    }

    // READ

    public ArrayList<ModelWeatherData> DAOgetAllItems(Boolean ordered) {
        ArrayList<ModelWeatherData> allItems = new ArrayList<>();

        if (DAOgetCurrentItems() == 0) {
            System.out.println();
            System.out.println("Atenció! No hi ha ítems a la base de dades.");
            return allItems;
        }

        String query = "SELECT * FROM weatherdata";

        if (ordered != null && ordered) {
            query = "SELECT * FROM weatherdata ORDER BY city";
        }

        try {
            PreparedStatement ps = mySQLConnection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModelWeatherData weatherData = new ModelWeatherData();
                weatherData.setRecordId(rs.getInt("record_id"));
                weatherData.setCity(rs.getString("city"));
                weatherData.setCountry(rs.getString("country"));
                weatherData.setLatitude(rs.getDouble("latitude"));
                weatherData.setLongitude(rs.getDouble("longitude"));
                weatherData.setDate(rs.getDate("date"));
                weatherData.setTemperatureCelsius(rs.getDouble("temperature_celsius"));
                weatherData.setHumidityPercent(rs.getDouble("humidity_percent"));
                weatherData.setPrecipitation(rs.getDouble("precipitation_mm"));
                weatherData.setWindSpeedKmh(rs.getDouble("Wind_speed_kmh"));
                weatherData.setWeatherCondition(rs.getString("weather_condition"));
                weatherData.setForecast(rs.getString("forecast"));
                weatherData.setUpdated(rs.getDate("updated"));

                allItems.add(weatherData);
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("No s'han pogut obtenir els ítems de la base de dades: " + e.getMessage());
            e.printStackTrace(); // Imprimir el stack trace completo para diagnosticar mejor el problema
        }
        return allItems;
    }

    public ArrayList<ModelWeatherData> DAOgetItemsByCities(ArrayList<String> cities) {
        ArrayList<ModelWeatherData> weatherDataList = new ArrayList<>();

        if (cities == null || cities.isEmpty()) {
            System.out.println("La llista de ciutats és buida.");
            return weatherDataList;
        }

        StringBuilder query = new StringBuilder("SELECT * FROM weatherdata WHERE city IN (");

        for (int i = 0; i < cities.size(); i++) {
            query.append("?");
            if (i < cities.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        try (PreparedStatement ps = mySQLConnection.prepareStatement(query.toString())) {
            for (int i = 0; i < cities.size(); i++) {
                ps.setString(i + 1, cities.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModelWeatherData weatherData = new ModelWeatherData();
                weatherData.setRecordId(rs.getInt("record_Id"));
                weatherData.setCity(rs.getString("city"));
                weatherData.setCountry(rs.getString("country"));
                weatherData.setLatitude(rs.getDouble("latitude"));
                weatherData.setLongitude(rs.getDouble("longitude"));
                weatherData.setDate(rs.getDate("date"));
                weatherData.setTemperatureCelsius(rs.getDouble("temperature_celsius"));
                weatherData.setHumidityPercent(rs.getDouble("humidity_percent"));
                weatherData.setPrecipitation(rs.getDouble("precipitation_mm"));
                weatherData.setWindSpeedKmh(rs.getDouble("wind_speed_kmh"));
                weatherData.setWeatherCondition(rs.getString("weather_condition"));
                weatherData.setForecast(rs.getString("forecast"));
                weatherData.setUpdated(rs.getDate("updated"));

                weatherDataList.add(weatherData);
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenir els registres: " + e.getMessage());
        }

        return weatherDataList;
    }

    // DELETE

    public void DAOdeleteAllItems() {
        int numDeleted = DAOgetCurrentItems();

        if (DAOgetCurrentItems() == 0) {
            System.out.println();
            System.out.println("Atenció! No hi ha ítems a la base de dades.");
            return;
        }

        try {
            String query = "DELETE FROM weatherdata";
            PreparedStatement ps = mySQLConnection.prepareStatement(query);
            ps.executeUpdate();
            System.out.println();
            System.out.println("S'han esborrat " + numDeleted + " ítems de la base de dades.");
        } catch (SQLException e) {
            System.out.println();
            System.out.println("No s'han pogut esborrar els ítems de la base de dades.");
        }
    }

    public boolean DAOdeleteItemByCities(ArrayList<String> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("La llista de ciutats és buida.");
            return false;
        }

        StringBuilder query = new StringBuilder("DELETE FROM weatherdata WHERE city IN (");

        for (int i = 0; i < cities.size(); i++) {
            query.append("?");
            if (i < cities.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        try (PreparedStatement ps = mySQLConnection.prepareStatement(query.toString())) {
            for (int i = 0; i < cities.size(); i++) {
                ps.setString(i + 1, cities.get(i));
            }

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("S'han esborrat " + rowsDeleted + " registres.");
                return true;
            } else {
                System.out.println("No s'han trobat registres per esborrar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error en esborrar els registres: " + e.getMessage());
            return false;
        }
    }

    // SYNCHRONIZE

    public void DAOsynchronizeDatabase(ArrayList<ModelWeatherData> mongoWeatherData) {
        DAOdeleteAllItems();
        DAOinsertVariousItems(mongoWeatherData);
    }
}
