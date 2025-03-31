package data;

import java.time.LocalDate;

public class Game {

    int idGame;
    int idTeam1;
    int idTeam2;
    String result;
    LocalDate date;

    public Game(int idTeam1, int idTeam2, String result, LocalDate date) {
        this.idTeam1 = idTeam1;
        this.idTeam2 = idTeam2;
        this.result = result;
        this.date = date;
    }

    public int getIdGame() {
        return idGame;
    }
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }
    public int getIdTeam1() {
        return idTeam1;
    }
    public void setIdTeam1(int idTeam1) {
        this.idTeam1 = idTeam1;
    }
    public int getIdTeam2() {
        return idTeam2;
    }
    public void setIdTeam2(int idTeam2) {
        this.idTeam2 = idTeam2;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Game{" +
                "idGame=" + idGame +
                ", idTeam1=" + idTeam1 +
                ", idTeam2=" + idTeam2 +
                ", result='" + result + '\'' +
                ", date=" + date +
                '}';
    }
    
}
