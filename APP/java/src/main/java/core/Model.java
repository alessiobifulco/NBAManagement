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
    void registerGMAccess(String email, String password) throws SQLException;

    // Funzione per aggiungere un contratto a una squadra
    void addContractToTeam(int idPlayer, int idTeam, int salary, int years) throws SQLException;

    // Funzione per pianificare un allenamento
    void scheduleTraining(int idCoach, int idPlayer, int duration, String focus) throws SQLException;

    // Funzione per aggiungere esercizi all'allenamento
    void addExerciseToTraining(int idTraining, int idExercise, int series) throws SQLException;

 
}
