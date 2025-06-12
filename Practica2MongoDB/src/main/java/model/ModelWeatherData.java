
package model;

import java.util.Date;
import org.bson.Document;

public class ModelWeatherData {

    // Atributos
    private int recordId;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    private Date date;
    private double temperatureCelsius;
    private double humidityPercent;
    private double precipitation;
    private double windSpeedKmh;
    private String weatherCondition;
    private String forecast;
    private Date updated;

    // Constructor

    public ModelWeatherData(int recordId) {
        this.recordId = recordId;
    }

    public ModelWeatherData() {

    }

    public ModelWeatherData(int recordId, String city, String country, double latitude, double longitude, Date date,
            double temperatureCelsius, double humidityPercent, double precipitation, double windSpeedKmh,
            String weatherCondition, String forecast, Date updated) {
        this.recordId = recordId;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.temperatureCelsius = temperatureCelsius;
        this.humidityPercent = humidityPercent;
        this.precipitation = precipitation;
        this.windSpeedKmh = windSpeedKmh;
        this.weatherCondition = weatherCondition;
        this.forecast = forecast;
        this.updated = updated;
    }

    // Getters y setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public double getHumidityPercent() {
        return humidityPercent;
    }

    public void setHumidityPercent(double humidityPercent) {
        this.humidityPercent = humidityPercent;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public double getWindSpeedKmh() {
        return windSpeedKmh;
    }

    public void setWindSpeedKmh(double windSpeedKmh) {
        this.windSpeedKmh = windSpeedKmh;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return String.format(
                "Weather Data: \n" +
                        "----------------------------\n" +
                        "Record ID        : %d\n" +
                        "City            : %s\n" +
                        "Country         : %s\n" +
                        "Latitude        : %.4f\n" +
                        "Longitude       : %.4f\n" +
                        "Date            : %s\n" +
                        "Temperature (°C): %.2f\n" +
                        "Humidity (%%)    : %.2f\n" +
                        "Precipitation (mm): %.2f\n" +
                        "Wind Speed (km/h): %.2f\n" +
                        "Weather Condition: %s\n" +
                        "Forecast        : %s\n" +
                        "Last Updated    : %s\n",
                recordId, city, country, latitude, longitude, date,
                temperatureCelsius, humidityPercent, precipitation,
                windSpeedKmh, weatherCondition, forecast, updated);
    }

    public Document toDocument() {
        Document document = new Document();

        document.append("record_id", this.recordId);
        document.append("city", this.city);
        document.append("country", this.country);
        document.append("latitude", this.latitude);
        document.append("longitude", this.longitude);
        document.append("date", this.date);
        document.append("temperature_celsius", this.temperatureCelsius);
        document.append("humidity_percent", this.humidityPercent);
        document.append("precipitation_mm", this.precipitation);
        document.append("wind_speed_kmh", this.windSpeedKmh);
        document.append("weather_condition", this.weatherCondition);
        document.append("forecast", this.forecast);
        document.append("updated", this.updated);

        return document;
    }
}
