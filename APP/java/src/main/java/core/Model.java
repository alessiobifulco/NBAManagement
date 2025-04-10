package core;

import data.*;
import model.DBModel;

import java.sql.Connection;
import java.sql.ResultSet;
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

    // Funzione per ottenere lo storico degli allenamenti
    List<String> getTrainingHistory(int idTeam) throws SQLException;

    // Funzione per ottenere lo storico degli scambi
    List<String> getTradeHistory(int idTeam) throws SQLException;

    // Funzione per ottenere lo storico dei contratti
    List<String> getContractHistory(int idTeam) throws SQLException;

    // Funzione per visualizzare lo storico delle partite
    List<String> getMatchHistory(int idTeam) throws SQLException;

    int getTeamIdByGM(String username, String password) throws SQLException;

    void removePlayerByNameAndSurname(String playerName, String playerSurname) throws SQLException;

    void removeObserverByNameAndSurname(String name, String surname) throws SQLException;

    void removeCoachByNameAndSurname(String name, String surname) throws SQLException;

    ResultSet executeQuery(String query, Object... params) throws SQLException;

    int executeUpdate(String query, Object... params) throws SQLException;

    List<String> getSortedPlayers(String teamName, String sortOption) throws SQLException;

    List<String> getAllTeams() throws SQLException;

    Player getPlayerById(int idPlayer);

    List<Player> getPlayersByTeam(int idTeam);

    List<Team> getTeamsExcludingLogged(int idLoggedTeam);

    List<Player> getPlayersSortedByPosition(int idTeam) throws SQLException;

    List<Player> getPlayersSortedByAge(int idTeam) throws SQLException;

    List<Player> getPlayersSortedByRating(int idTeam) throws SQLException;

    List<Trade> getTradesByTeam(int idTeam) throws SQLException;

    void proposeTrade(Player playerToTrade, Player selectedPlayerForTrade, Team selectedTeam) throws SQLException;

    // Aggiungi questo metodo all'interfaccia Model
    Team getTeamByPlayerId(int idPlayer) throws SQLException;

    Team getTeamByContract(Contract playerToTradeContract) throws SQLException;

    Contract getContractByPlayerId(int idPlayer) throws SQLException;

    List<Trade> getTradesInProgress(int idTeam) throws SQLException;

    List<Player> getActivePlayersByTeam(int idTeam) throws SQLException;

    Contract getActiveContract(int idPlayer) throws SQLException;

    Team getTeamById(int idTeam) throws SQLException;

    List<Player> getExpiringContracts(int idTeam) throws SQLException;

    void proposeTrade(int idContract, int idContract2 ) throws SQLException;

    void acceptTrade(int idTrade) throws SQLException;

    void rejectTrade(int idTrade) throws SQLException;

    Contract getContractById(int idContract) throws SQLException;

}
