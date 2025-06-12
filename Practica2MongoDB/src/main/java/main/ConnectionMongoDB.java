package main;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class ConnectionMongoDB {
    
    public static MongoClient mongoDBConnection = null;
    public static MongoDatabase mongoDBDatabase = null;
    public static MongoCollection<Document> collection = null;
    
    public static void connectMongoDB() {
        try {
            mongoDBConnection = new MongoClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void desconnectMongoDB() {
        try {
            if (mongoDBConnection != null) {
                mongoDBConnection.close();
                System.out.println();
                System.out.println("S'ha desconnectat de les bases de dades de MongoDB.");
            }
        } catch (Exception ex) {
            System.out.println();
            System.out.println("No s'ha pogut desconnectar de la base de dades de MongoDB.");
        }
    }
    
    public static boolean collectionExists(String collectionName, MongoDatabase database) {
        MongoIterable<String> collections = database.listCollectionNames();
        
        for (String collection : collections) {
            if (collection.equals(collectionName)) {
                return true;
            }
        }
        return false;
    }
    
    public static void createCollectionIfNotExists(String collectionName, MongoDatabase database) {
        try {
            if (collectionExists(collectionName, database)) {
                System.out.println();
                System.out.println("Atenció! Ja existeix la col·lecció" + collectionName);
            } else {
                database.createCollection(collectionName);
                System.out.println();
                System.out.println("La col·lecció " + collectionName + " ha sigut creada.");
            }
        } catch (Exception e){
            System.out.println();
            System.out.println("No s'ha pogut crear la col·lecció.");
            e.printStackTrace();
        }
    }
    
    public static void getCollection(String collectionName) {
        try {
            collection = mongoDBDatabase.getCollection(collectionName);
        } catch (Exception e) {
            System.out.println();
            System.out.println("No s'ha pogut obtenir la col·lecció.");
        }
    }
    
    public static MongoDatabase getDatabase(String databaseName) {
        try {
            mongoDBDatabase = mongoDBConnection.getDatabase(databaseName);
            return mongoDBDatabase;
        } catch (Exception e) {
            System.out.println();
            System.out.println("No s'ha pogut accedir a la col·lecció.");
        }
        return null;
    }  
    
    public static void disableMongoLogging() {

    }
}
