
package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.ModelWeatherData;

public class XMLReader {
    
    public static ArrayList<ModelWeatherData> readXML(String filePath) {
        ArrayList<ModelWeatherData> weatherDatasFromXML = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filePath));
           
            doc.getDocumentElement().normalize();
            
            NodeList list = doc.getElementsByTagName("modelWeatherData");
            
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i); 
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    
                    
                    int recordId = Integer.parseInt(element.getElementsByTagName("record_id").item(0).getTextContent());
                    String city = element.getElementsByTagName("city").item(0).getTextContent();
                    String country = element.getElementsByTagName("country").item(0).getTextContent();
                    double latitude = Double.parseDouble(element.getElementsByTagName("latitude").item(0).getTextContent());
                    double longitude = Double.parseDouble(element.getElementsByTagName("longitude").item(0).getTextContent());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(element.getElementsByTagName("updated").item(0).getTextContent());

                    double temperatureCelsius = Double.parseDouble(element.getElementsByTagName("temperature_celsius").item(0).getTextContent());
                    double humidityPercent = Double.parseDouble(element.getElementsByTagName("humidity_percent").item(0).getTextContent());
                    double precipitation = Double.parseDouble(element.getElementsByTagName("precipitation_mm").item(0).getTextContent());
                    double windSpeedKmh = Double.parseDouble(element.getElementsByTagName("wind_speed_kmh").item(0).getTextContent());
                    String weatherCondition = element.getElementsByTagName("weather_condition").item(0).getTextContent();
                    String forecast = element.getElementsByTagName("forecast").item(0).getTextContent();

                    SimpleDateFormat dateFormatUpdated = new SimpleDateFormat("yyyy-MM-dd");
                    Date updated = dateFormatUpdated.parse(element.getElementsByTagName("updated").item(0).getTextContent());
                                        
                    weatherDatasFromXML.add(new ModelWeatherData(recordId, city, country, latitude, longitude, date, temperatureCelsius, humidityPercent, precipitation, windSpeedKmh, weatherCondition, forecast, updated));
                }
            }
            
        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            System.out.println();
            System.out.println("[!] Atenció! No s'ha pogut llegir l'arxiu XML.");
            System.out.println("[!] Pista: Comprova si la ruta de l'arxiu és la correcta.");
        }
        
        return weatherDatasFromXML;
    }
}