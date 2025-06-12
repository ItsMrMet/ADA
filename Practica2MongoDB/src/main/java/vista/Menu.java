
package vista;

import java.util.ArrayList;
import model.User;

public class Menu {

    //Constructor
    public Menu() {
        
    }
    
    //Methods

    //MESSAGES
    
    public String welcomeMessage(User user, int temperatureCelsius) {
        String welcomeMessage = "";
        welcomeMessage = "Benvingut " + user.getName() + ", a la teua ciutat " + user.getCity() + " hi ha una temperatura de " + temperatureCelsius + " graus centígrads.";

        return welcomeMessage;
    }
    
    private String currentDatabase(String database, int currentItems) {
        return "Estas emprant la BBDD de " + database + ".\nActualment hi ha " + currentItems + " items." ;
    }

    public String goodbyeMessage() {
        ArrayList<String> goodbyeMessages = new ArrayList<>();
        goodbyeMessages.add("Adéu! Moltes gràcies per emprar la meua aplicació!");
        goodbyeMessages.add("Un plaer! Espere que ens tornem a veure!");
        goodbyeMessages.add("Fins després! Espere que t'haja agradat la meua aplicació!");
        
        goodbyeMessages.add("Adéu i fins aviat! Que tingues un bon dia!");
        goodbyeMessages.add("Fins la propera vegada! Gràcies per utilitzar la meua aplicació!");
        goodbyeMessages.add("Adéu amic/ga! Espere veure't de nou prompte!");
        
        goodbyeMessages.add("Que la passes bé! Fins després!");
        goodbyeMessages.add("Fins aviat! No t'oblides de tornar!");
        goodbyeMessages.add("Adéu per ara! Gràcies per la teua preferència!");
        
        int randomNumber = (int) (Math.random() * goodbyeMessages.size());

        return goodbyeMessages.get(randomNumber) + "\nAplicació desenvolupada per Thomas Van.";
    }

    //MAIN MENU
    
    public void mainMenu(int currentItemsMySQL, int currentItemsMongoDB) {
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Items actuals en les BBDD:");
        System.out.println("MySQL: " + currentItemsMySQL);
        System.out.println("MongoDB: " + currentItemsMongoDB);
        System.out.println();
        System.out.println("Amb quina BBDD desitjes treballar?");
        System.out.println();
        System.out.println("1. MySQL.");
        System.out.println("2. MongoDB.");
        System.out.println("3. Eixir.");
    }

    //DATABASE MENU
    
    public void databaseMenu(int currentItems, boolean database, boolean canSynchronize) {
        String databaseName;
        if (database) {
            databaseName = "MySQL";
        } else {
            databaseName = "MongoDB";
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println(currentDatabase(databaseName, currentItems));
        System.out.println();
        if (canSynchronize) {
            System.out.println("0. Sincronitzar.");
        }
        System.out.println("1. Inserir un element.");
        System.out.println("2. Inserir varios elements.");
        System.out.println("3. Llistar.");
        System.out.println("4. Esborrar.");
        if (database) {
            System.out.println("5. Importar.");
            System.out.println("6. Tornar.");
        } else {
            System.out.println("5. Upsert.");
            System.out.println("6. Importar.");
            System.out.println("7. Tornar.");
        }
    }
    
    //SUBMENUS

    public void readSubmenu(Boolean database, int currentItems) {
        String databaseName;
        if (database) {
            databaseName = "MySQL";
        } else {
            databaseName = "MongoDB";
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println(currentDatabase(databaseName, currentItems));
        System.out.println();
        System.out.println("1. Llistar per ciutat.");
        System.out.println("2. Llistar per diverses ciutats.");
        System.out.println("3. Llistar tots, ordenats alfabèticament per ciutat.");
        System.out.println("4. Tornar.");
    }

    public void deleteSubmenu(Boolean database, int currentItems) {
        String databaseName;
        if (database) {
            databaseName = "MySQL";
        } else {
            databaseName = "MongoDB";
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println(currentDatabase(databaseName, currentItems));
        System.out.println();
        System.out.println("1. Esborrar tots.");
        System.out.println("2. Esborrar per ciutat.");
        System.out.println("3. Esborrar per diverses ciutats.");
        System.out.println("4. Tornar.");
    }
}