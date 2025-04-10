package data;

import java.time.LocalDate;

public class Trade {
    private int idTrade;
    private int idContract1;
    private int idContract2;
    private State state;
    private LocalDate date;

    public Trade(int idContract1, int idContract2, State state, LocalDate date) {
        this.idContract1 = idContract1;
        this.idContract2 = idContract2;
        this.state = state;
        this.date = date;
    }

    public enum State {
        ACCEPTED("Accettato"),
        IN_PROGRESS("In corso"),
        REJECTED("Rifiutato");

        private final String dbValue;

        State(String dbValue) {
            this.dbValue = dbValue;
        }

        public String getDbValue() {
            return dbValue;
        }

        public static State fromDbValue(String dbValue) {
            for (State state : values()) {
                if (state.dbValue.equalsIgnoreCase(dbValue)) {
                    return state;
                }
            }
            throw new IllegalArgumentException("No enum constant for db value: " + dbValue);
        }

        @Override
        public String toString() {
            return dbValue;
        }
    }

    // Getters and Setters
    public int getIdTrade() {
        return idTrade;
    }

    public void setIdTrade(int idTrade) {
        this.idTrade = idTrade;
    }

    public int getIdContract1() {
        return idContract1;
    }

    public void setIdContract1(int idContract1) {
        this.idContract1 = idContract1;
    }

    public int getIdContract2() {
        return idContract2;
    }

    public void setIdContract2(int idContract2) {
        this.idContract2 = idContract2;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "idTrade=" + idTrade +
                ", idContract1=" + idContract1 +
                ", idContract2=" + idContract2 +
                ", state=" + state +
                ", date=" + date +
                '}';
    }
}