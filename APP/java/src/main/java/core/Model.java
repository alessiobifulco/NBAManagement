package core;

import data.*;
import model.DBModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface Model {

    public static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

    // Funzione per registrare l'accesso di un GM
    boolean registerGMAccess(String email, String password) throws SQLException;

    // Funzione per rimuovere un giocatore
    void removePlayer(int idGiocatore) throws SQLException;

    // Funzione per aggiungere un allenatore
    void addCoach(String nome, String cognome, double stipendio, int anniEsperienza, boolean free) throws SQLException;

    // Funzione per rimuovere un allenatore
    void removeCoach(int idAllenatore) throws SQLException;

    // Funzione per aggiungere un osservatore
    void addObserver(String nome, String cognome, double stipendio, int anniEsperienza, boolean free)
            throws SQLException;

    // Funzione per rimuovere un osservatore
    void removeObserver(int idOsservatore) throws SQLException;

    // Funzione per aggiungere una partita
    void addGame(int idSquadra1, int idSquadra2, String risultato, int stadium, LocalDate data) throws SQLException;

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

    Player getPlayerById(int idPlayer);

    List<Team> getTeamsExcludingLogged(int idLoggedTeam);

    List<Player> getPlayersSortedByPosition(int idTeam) throws SQLException;

    List<Player> getPlayersSortedByAge(int idTeam) throws SQLException;

    List<Player> getPlayersSortedByRating(int idTeam) throws SQLException;

    List<Trade> getTradesInProgress(int idTeam) throws SQLException;

    List<Player> getActivePlayersByTeam(int idTeam) throws SQLException;

    Contract getActiveContract(int idPlayer) throws SQLException;

    Team getTeamById(int idTeam) throws SQLException;

    List<Player> getExpiringContracts(int idTeam) throws SQLException;

    void proposeTrade(int idContract, int idContract2) throws SQLException;

    void acceptTrade(int idTrade) throws SQLException;

    void rejectTrade(int idTrade) throws SQLException;

    Contract getContractById(int idContract) throws SQLException;

    List<Game> getAllGames() throws SQLException;

    List<Observer> getAllObservers() throws SQLException;

    List<Coach> getAllCoaches() throws SQLException;

    List<Player> getAllPlayers() throws SQLException;

    void addPlayer(String text, String text2, int int1, String string, String string2, double double1, int int2,
            boolean b) throws SQLException;

}
