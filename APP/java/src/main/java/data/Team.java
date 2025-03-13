package data;

public class Team {
    
    String name;
    String city;
    int idGM;
    int idCoach;
    int idObserver;
    int nPlayers;
    int maxCap;
    int idTeam = 0;

    public Team(String name, String city, int idGM, int idCoach, int idObserver, int nPlayers, int maxCap){
        this.name = name;
        this.city = city;
        this.idGM = idGM;
        this.idCoach = idCoach;
        this.idObserver = idObserver;
        this.nPlayers = nPlayers;
        this.maxCap = maxCap;
        this.idTeam = idTeam++;
    }

}
