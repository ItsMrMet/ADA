
package controlador;

import dao.DAOMongoDB;
import dao.DAOMySQL;
import java.util.ArrayList;
import model.ModelWeatherData;
import vista.ViewWeatherData;


public class ControllerMongoDB {
    
    private final DAOMongoDB daoMongoDB = new DAOMongoDB();
    private final DAOMySQL daoMySQL = new DAOMySQL();
    private final ViewWeatherData viewWeatherData = new ViewWeatherData();
    
    public int getCurrentItems() {
        return daoMongoDB.DAOgetCurrentItems();
    }
    
    public void insertItem(ModelWeatherData createdWeatherData) {
        daoMongoDB.DAOinsertItem(createdWeatherData);
    }
    
    public void insertVariousItems(ArrayList<ModelWeatherData> createdWeatherData) {
        daoMongoDB.DAOinsertVariousItems(createdWeatherData);
    }
    
    public void getAllItems(Boolean ordered) {
        viewWeatherData.showWeatherDatas(daoMongoDB.DAOgetAllItems(ordered));
    }

    public void getItemsByCities(ArrayList<String> cities) {
        viewWeatherData.showWeatherDatas(daoMongoDB.DAOgetItemsByCities(cities));
    }

    public void deleteAllItems() {
        daoMongoDB.DAOdeleteAllItems();
    }
    
    public void deleteItemsByCities(ArrayList<String> cities) {
        daoMongoDB.DAOdeleteItemsByCities(cities);
    }
    
    public void synchronizeDatabase() {
        daoMySQL.DAOsynchronizeDatabase(daoMongoDB.DAOgetAllItems(true));
    }
    
    public void upsert(ModelWeatherData weatherData) {
        daoMongoDB.DAOupsert(weatherData);
    }

    public void importItems(ArrayList<ModelWeatherData> weatherDatas) {

    }
}
