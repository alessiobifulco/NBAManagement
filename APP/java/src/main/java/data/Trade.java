package data;

import java.time.LocalDate;

public class Trade {

    int idTrade;
    int idContract1;
    int idContract2;

    public enum State {
        Accettato, In_Corso, Rifiutato
    };

    State state;
    LocalDate date;

    public Trade(int idContract1, int idContract2, State state, LocalDate date) {
        this.idContract1 = idContract1;
        this.idContract2 = idContract2;
        this.state = state;
        this.date = date;
    }

    public int getIdTrade() {
        return idTrade;
    }

    public int getIdContract1() {
        return idContract1;
    }

    public int getIdContract2() {
        return idContract2;
    }

    public State getState() {
        return state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setIdTrade(int idTrade) {
        this.idTrade = idTrade;
    }

    public void setIdContract1(int idContract1) {
        this.idContract1 = idContract1;
    }

    public void setIdContract2(int idContract2) {
        this.idContract2 = idContract2;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Trade{" +
                ", idContract1=" + idContract1 +
                ", idContract2=" + idContract2 +
                ", state=" + state +
                ", date=" + date +
                '}';
    }

}
