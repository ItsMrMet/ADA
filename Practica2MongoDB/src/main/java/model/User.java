
package model;


public class User {
    
    private String dni;
    private String name;
    private String lastName;
    private String city;
    
    public User() {

    }

    public User(String dni) {
        this.dni = dni;
    }
    
    public User(String dni, String name, String lastName, String city) {
        this.dni = dni;
        this.name = name;
        this.lastName  = lastName;
        this.city = city;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
}
