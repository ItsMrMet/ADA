
package controlador;

import dao.DAOMongoDB;
import dao.DAOMySQL;
import java.util.ArrayList;
import model.ModelWeatherData;
import vista.ViewWeatherData;


public class ControllerMySQL {
    
    private final DAOMySQL daoMySQL = new DAOMySQL();
    private final DAOMongoDB daoMongoDB = new DAOMongoDB();
    private final ViewWeatherData viewWeatherData = new ViewWeatherData();

    
    public int getCurrentItems() {
        return daoMySQL.DAOgetCurrentItems();
    }
    
    public void insertItem(ModelWeatherData createdWeatherData) {
        daoMySQL.DAOinsertItem(createdWeatherData);
    }
    
    public void insertVariousItems(ArrayList<ModelWeatherData> createdWeatherData) {
        daoMySQL.DAOinsertVariousItems(createdWeatherData);
    }

    public void getAllItems(Boolean ordered) {
        viewWeatherData.showWeatherDatas(daoMySQL.DAOgetAllItems(ordered));
    }

    public void getItemsByCities(ArrayList<String> cities) {
        viewWeatherData.showWeatherDatas(daoMySQL.DAOgetItemsByCities(cities));
    }

    public void deleteAllItems() {
        daoMySQL.DAOdeleteAllItems();
    }
    
    public void deleteItemsByCities(ArrayList<String> cities) {
        daoMySQL.DAOdeleteItemByCities(cities);
    }
    
    public void synchronizeDatabase() {
        daoMongoDB.DAOsynchronizeDatabase(daoMySQL.DAOgetAllItems(true));
    }

    public void importItems(ArrayList<ModelWeatherData> weatherDatas) {

    }
}
