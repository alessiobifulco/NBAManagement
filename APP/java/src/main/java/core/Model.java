package core;

import data.*;
import model.DBModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Model {

    public static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

    // Funzione per ottenere la rosa di una squadra
    List<Player> getTeamRoster(int idTeam) throws SQLException;

    // Funzione per cercare free agent
    List<Player> getFreeAgents() throws SQLException;

    // Funzione per creare una proposta di scambio
    void createTradeProposal(int idContract1, int idContract2) throws SQLException;

    // Funzione per aggiornare lo stato di uno scambio
    void updateTradeStatus(int idScambio, String status) throws SQLException;

    // Funzione per visualizzare lo storico delle partite
    List<String> getMatchHistory(int idTeam) throws SQLException;

    // Funzione per ottenere i contratti in scadenza
    List<Contract> getExpiringContracts() throws SQLException;

    // Funzione per registrare l'accesso di un GM
    boolean registerGMAccess(String email, String password) throws SQLException;

    // Funzione per aggiungere un contratto a una squadra
    void addContractToTeam(int idPlayer, int idTeam, int salary, int years) throws SQLException;

    // Funzione per pianificare un allenamento
    void scheduleTraining(int idCoach, int idPlayer, int duration, String focus) throws SQLException;

    // Funzione per aggiungere esercizi all'allenamento
    void addExerciseToTraining(int idTraining, int idExercise, int series) throws SQLException;

    // Nuovi metodi aggiunti

    // Funzione per aggiungere un giocatore
    void addPlayer(String nome, String cognome, String position, String categoria,
                   double valutazione, int anniEsperienza) throws SQLException;

    // Funzione per rimuovere un giocatore
    void removePlayer(int idGiocatore) throws SQLException;

    // Funzione per aggiungere un allenatore
    void addCoach(String nome, String cognome, double stipendio, int anniEsperienza) throws SQLException;

    // Funzione per rimuovere un allenatore
    void removeCoach(int idAllenatore) throws SQLException;

    // Funzione per aggiungere un osservatore
    void addObserver(String nome, String cognome, double stipendio, int anniEsperienza) throws SQLException;

    // Funzione per rimuovere un osservatore
    void removeObserver(int idOsservatore) throws SQLException;

    // Funzione per aggiungere una partita
    void addGame(int idSquadra1, int idSquadra2, String risultato) throws SQLException;

    // Funzione per rimuovere una partita
    void removeGame(int idPartita) throws SQLException;
}
