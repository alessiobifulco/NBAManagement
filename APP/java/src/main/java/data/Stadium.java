package data;

public class Stadium {
    
    int idStadium;
    String name;
    String city;
    int capacity;
    int idTeam;

    public Stadium(String name, String city, int capacity, int idTeam){
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.idTeam = idTeam;
    }
}
