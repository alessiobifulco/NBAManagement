package data;

public class Team {
    
    String name;
    String city;
    int idGM;
    int idCoach;
    int idObserver;
    int nPlayers;
    int maxCap;
    int idTeam;

    public Team(String name, String city, int idGM, int idCoach, int idObserver, int nPlayers, int maxCap){
        this.name = name;
        this.city = city;
        this.idGM = idGM;
        this.idCoach = idCoach;
        this.idObserver = idObserver;
        this.nPlayers = nPlayers;
        this.maxCap = maxCap;
    }

    public int getIdTeam(){
        return idTeam;
    }

    public String getName(){
        return name;
    }

    public String getCity(){
        return city;
    }

    public int getIdGM(){
        return idGM;
    }

    public int getIdCoach(){
        return idCoach;
    }

    public int getIdObserver(){
        return idObserver;
    }

    public int getNPlayers(){
        return nPlayers;
    }

    public int getMaxCap(){
        return maxCap;
    }

    public void setIdTeam(int idTeam){
        this.idTeam = idTeam;
    }



}
