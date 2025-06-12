
package dao;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import main.ConnectionMongoDB;
import model.ModelWeatherData;

public class DAOMongoDB {

    private Document query;

    public void setQuery(String key, Object value) {
        this.query = new Document(key, value);
    }

    // Methods
    private boolean DAOexistsCity(String city) {
        setQuery("city", city);
        long count = ConnectionMongoDB.collection.countDocuments(query);
        return count > 0;
    }

    public int DAOgetCurrentItems() {
        return (int) ConnectionMongoDB.collection.countDocuments();
    }

    private ModelWeatherData documentToModelWeatherData(Document document) {
        if (document == null) {
            return null;
        }

        try {
            int recordId = document.getInteger("record_id");
            String city = document.getString("city");
            String country = document.getString("country");
            double latitude = document.getDouble("latitude");
            double longitude = document.getDouble("longitude");

            // Obtener las fechas directamente del documento de MongoDB
            Date utilDate = document.getDate("date");
            java.sql.Date date = null;
            if (utilDate != null) {
                date = new java.sql.Date(utilDate.getTime());
            }

            Date utilUpdated = document.getDate("updated");
            java.sql.Date updated = null;
            if (utilUpdated != null) {
                updated = new java.sql.Date(utilUpdated.getTime());
            }

            double temperatureCelsius = document.getDouble("temperature_celsius");
            double humidityPercent = document.getDouble("humidity_percent");
            double precipitation = document.getDouble("precipitation_mm");
            double windSpeedKmh = document.getDouble("wind_speed_kmh");
            String weatherCondition = document.getString("weather_condition");
            String forecast = document.getString("forecast");

            return new ModelWeatherData(
                    recordId,
                    city,
                    country,
                    latitude,
                    longitude,
                    date,
                    temperatureCelsius,
                    humidityPercent,
                    precipitation,
                    windSpeedKmh,
                    weatherCondition,
                    forecast,
                    updated);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
            ConnectionMongoDB.collection.insertOne(createdWeatherData.toDocument());

            System.out.println();
            System.out.println("S'ha inserit el nou ítem sense problemes!");
            return true;
        } catch (MongoWriteException e) {
            System.out.println();
            System.out.println("No s'ha pogut inserir l'ítem." + e.getMessage());
            return false;
        }
    }

    public void DAOinsertVariousItems(ArrayList<ModelWeatherData> createdWeatherDatas) {
        int numInserted = 0;

        for (ModelWeatherData weatherData : createdWeatherDatas) {
            if (DAOinsertItem(weatherData)) {
                numInserted++;
            }
        }

        System.out.println();
        System.out.println("S'han inserit " + numInserted + " ciutats.");
    }

    // READ

    public ArrayList<ModelWeatherData> DAOgetAllItems(boolean ordered) {
        ArrayList<ModelWeatherData> allItems = new ArrayList<>();
        FindIterable<Document> cursor;

        if (DAOgetCurrentItems() == 0) {
            System.out.println();
            System.out.println("Atenció! No hi ha ítems a la base de dades!");
            return allItems;
        }

        if (ordered) {
            cursor = ConnectionMongoDB.collection.find().sort(Sorts.ascending("city"));
        } else {
            cursor = ConnectionMongoDB.collection.find();
        }

        try (MongoCursor<Document> iterator = cursor.iterator()) {
            while (iterator.hasNext()) {
                Document document = iterator.next();
                ModelWeatherData weatherData = documentToModelWeatherData(document);
                allItems.add(weatherData);
            }
        }
        return allItems;
    }

    public ArrayList<ModelWeatherData> DAOgetItemsByCities(ArrayList<String> cities) {
        ArrayList<ModelWeatherData> itemsByCity = new ArrayList<>();

        Document filter = new Document("city", new Document("$in", cities));

        FindIterable<Document> cursor = ConnectionMongoDB.collection.find(filter);

        try (MongoCursor<Document> iterator = cursor.iterator()) {
            while (iterator.hasNext()) {
                Document document = iterator.next();
                ModelWeatherData weatherData = documentToModelWeatherData(document); // Convertir a modelo
                itemsByCity.add(weatherData); // Añadir el modelo a la lista
            }
        }

        return itemsByCity;
    }

    // DELETE

    public void DAOdeleteAllItems() {
        int numDeleted = DAOgetCurrentItems();

        if (DAOgetCurrentItems() == 0) {
            System.out.println();
            System.out.println("Atenció! No hi ha ítems a la base de dades.");
        }

        try {
            ConnectionMongoDB.collection.deleteMany(new Document());
            System.out.println();
            System.out.println("S'han esborrat " + numDeleted + " ítems de la base de dades.");
        } catch (MongoException e) {
            System.out.println();
            System.out.println("No s'han pogut esborrar els ítems de la base de dades.");
        }
    }

    public boolean DAOdeleteItemsByCities(ArrayList<String> cities) {
        try {
            Document filter = new Document("city", new Document("$in", cities));

            DeleteResult result = ConnectionMongoDB.collection.deleteMany(filter);

            if (result.getDeletedCount() > 0) {
                System.out.println(result.getDeletedCount() + " documents van ser eliminats.");
                return true;
            } else {
                System.out.println("No s'han trobat registres per eliminar.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("S'ha produït un error en intentar eliminar els registres.");
            e.printStackTrace();
            return false;
        }
    }

    // SYNCHRONISE

    public void DAOsynchronizeDatabase(ArrayList<ModelWeatherData> mySQLWeatherData) {
        DAOdeleteAllItems();
        DAOinsertVariousItems(mySQLWeatherData);
    }

    public void DAOupsert(ModelWeatherData weatherData) {
        Document document = new Document("recordId", weatherData.getRecordId())
                .append("city", weatherData.getCity())
                .append("country", weatherData.getCountry())
                .append("latitude", weatherData.getLatitude())
                .append("longitude", weatherData.getLongitude())
                .append("date", weatherData.getDate())
                .append("temperatureCelsius", weatherData.getTemperatureCelsius())
                .append("humidityPercent", weatherData.getHumidityPercent())
                .append("precipitation", weatherData.getPrecipitation())
                .append("windSpeedKmh", weatherData.getWindSpeedKmh())
                .append("weatherCondition", weatherData.getWeatherCondition())
                .append("forecast", weatherData.getForecast())
                .append("updated", weatherData.getUpdated());

        Document filter = new Document("city", weatherData.getCity());

        ConnectionMongoDB.collection.replaceOne(filter, document, new UpdateOptions().upsert(true));
    }
}
