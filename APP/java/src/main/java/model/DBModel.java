package model;

import data.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import core.Model;

@SuppressWarnings("unused")
public class DBModel implements Model {

    private final Connection connection;

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt.executeQuery();
    }

    @Override
    public int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            int affectedRows = stmt.executeUpdate();
            return affectedRows;
        }
    }

    // Funzione per cercare free agent
    @Override
    public List<Player> getFreeAgents() throws SQLException {
        List<Player> freeAgents = new ArrayList<>();
        String sql = "SELECT idGiocatore, nome, cognome, position, categoria, valutazione " +
                "FROM GIOCATORE WHERE freeagent = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Player player = new Player(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("age"),
                        Player.Position.valueOf(rs.getString("position")),
                        Player.Category.valueOf(rs.getString("categoria")),
                        rs.getInt("rank"),
                        rs.getInt("expereince"),
                        rs.getBoolean("freeagent"));
                player.setIdPlayer(rs.getInt("idGiocatore"));
                freeAgents.add(player);
            }
        }
        return freeAgents;
    }

    // Funzione per creare una proposta di scambio
    @Override
    public void createTradeProposal(int idContract1, int idContract2) throws SQLException {
        String sql = "INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data) " +
                "VALUES (?, ?, 'In corso', NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idContract1);
            stmt.setInt(2, idContract2);
            stmt.executeUpdate();
        }
    }

    // Funzione per visualizzare lo storico delle partite
    @Override
    public List<String> getMatchHistory(int idTeam) throws SQLException {
        List<String> history = new ArrayList<>();
        String sql = "SELECT P.data, P.risultato, S1.nome AS squadra_casa, S2.nome AS squadra_ospite " +
                "FROM PARTITA P " +
                "JOIN SQUADRA S1 ON P.idSquadra1 = S1.idSquadra " +
                "JOIN SQUADRA S2 ON P.idSquadra2 = S2.idSquadra " +
                "WHERE S1.idSquadra = ? OR S2.idSquadra = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idTeam);
            ResultSet rs = stmt.executeQuery();

            int count = 0; // Contatore per vedere quante righe vengono restituite
            while (rs.next()) {
                history.add(rs.getString("data") + " - " +
                        rs.getString("risultato") + " - " +
                        rs.getString("squadra_casa") + " vs " +
                        rs.getString("squadra_ospite"));
                count++;
            }

            System.out.println("Number of matches found: " + count); // Stampa del conteggio
        }
        return history;
    }

    // Funzione per ottenere i contratti in scadenza
    @Override
    public List<Contract> getExpiringContracts() throws SQLException {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT C.idContratto, C.idGiocatore, C.idSquadra, C.stipendio, C.data, C.durata, C.stato " +
                "FROM CONTRATTO C WHERE C.stato = TRUE AND C.durata = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract(
                        rs.getInt("idGiocatore"),
                        rs.getInt("idSquadra"),
                        rs.getInt("stipendio"),
                        rs.getDate("data").toLocalDate(),
                        rs.getInt("durata"),
                        rs.getBoolean("stato"));
                contract.setIdContract(rs.getInt("idContratto"));
                contracts.add(contract);
            }
        }
        return contracts;
    }

    // Funzione per registrare l'accesso di un GM
    @Override
    public boolean registerGMAccess(String email, String password) {
        String sql = "SELECT idGM FROM GM WHERE mail = ? AND password = ? AND idGM IN (SELECT idGM FROM SQUADRA)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idGM = rs.getInt("idGM");
                System.out.println("Login valido per idGM: " + idGM);

                String insertAccess = "INSERT INTO ACCESSO (data, idGM) VALUES (NOW(), ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertAccess)) {
                    insertStmt.setInt(1, idGM);
                    insertStmt.executeUpdate();
                }

                return true; // Login e registrazione accesso riusciti
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log dell'errore
            System.out.println("Login fallito: Nessun GM trovato con queste credenziali.");

        }
        return false; // Login fallito
    }

    // Funzione per aggiungere un contratto a una squadra
    @Override
    public void addContractToTeam(int idPlayer, int idTeam, int salary, int years) throws SQLException {
        String sql = "INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio, stato) " +
                "VALUES (?, ?, NOW(), ?, ?, TRUE)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idPlayer);
            stmt.setInt(3, years);
            stmt.setInt(4, salary);
            stmt.executeUpdate();
        }
    }

    // Funzione per pianificare un allenamento
    @Override
    public void scheduleTraining(int idCoach, int idPlayer, int duration, String focus) throws SQLException {
        String sql = "INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus) " +
                "VALUES (?, 'Gruppo', ?, ?, NOW(), ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCoach);
            stmt.setInt(2, idPlayer);
            stmt.setInt(3, duration);
            stmt.setString(4, focus);
            stmt.executeUpdate();
        }
    }

    // Funzione per aggiungere esercizi all'allenamento
    @Override
    public void addExerciseToTraining(int idTraining, int idExercise, int series) throws SQLException {
        String sql = "INSERT INTO ESERCIZIO_IN_ALLENAMENTO (idAllenamento, idEsercizio, serie) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTraining);
            stmt.setInt(2, idExercise);
            stmt.setInt(3, series);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addPlayer(String nome, String cognome, String position, String categoria,
            double valutazione, int anniEsperienza) throws SQLException {
        String sql = "INSERT INTO GIOCATORE (nome, cognome, position, categoria, valutazione, anni_esperienza, freeagent) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, FALSE)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, position);
            stmt.setString(4, categoria);
            stmt.setDouble(5, valutazione);
            stmt.setInt(6, anniEsperienza);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removePlayer(int idGiocatore) throws SQLException {
        String sql = "DELETE FROM GIOCATORE WHERE idGiocatore = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGiocatore);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addCoach(String nome, String cognome, double stipendio, int anniEsperienza) throws SQLException {
        String sql = "INSERT INTO ALLENATORE (nome, cognome, stipendio, anni_esperienza, free) " +
                "VALUES (?, ?, ?, ?, FALSE)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setDouble(3, stipendio);
            stmt.setInt(4, anniEsperienza);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeCoach(int idAllenatore) throws SQLException {
        String sql = "DELETE FROM ALLENATORE WHERE idAllenatore = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAllenatore);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addObserver(String nome, String cognome, double stipendio, int anniEsperienza) throws SQLException {
        String sql = "INSERT INTO OSSERVATORE (nome, cognome, stipendio, anni_esperienza, free) " +
                "VALUES (?, ?, ?, ?, FALSE)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setDouble(3, stipendio);
            stmt.setInt(4, anniEsperienza);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeObserver(int idOsservatore) throws SQLException {
        String sql = "DELETE FROM OSSERVATORE WHERE idOsservatore = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOsservatore);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addGame(int idSquadra1, int idSquadra2, String risultato) throws SQLException {
        String sql = "INSERT INTO PARTITA (idSquadra1, idSquadra2, risultato, data) " +
                "VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSquadra1);
            stmt.setInt(2, idSquadra2);
            stmt.setString(3, risultato);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeGame(int idPartita) throws SQLException {
        String sql = "DELETE FROM PARTITA WHERE idPartita = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPartita);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<String> getTrainingHistory(int idTeam) throws SQLException {
        List<String> trainingHistory = new ArrayList<>();

        // Query che unisce SQUADRA, ALLENATORE e ALLENAMENTO
        String sql = "SELECT A.data, A.focus, G.nome AS nome_giocatore, G.cognome AS cognome_giocatore " +
                "FROM ALLENAMENTO A " +
                "JOIN ALLENATORE AL ON A.idAllenatore = AL.idAllenatore " +
                "JOIN GIOCATORE G ON A.idGiocatore = G.idGiocatore " +
                "WHERE AL.idAllenatore = (SELECT idAllenatore FROM SQUADRA WHERE idSquadra = ?) " + // Risale
                                                                                                    // all'allenatore
                                                                                                    // della squadra
                "ORDER BY A.data DESC"; // Ordina per data

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam); // Passi l'idSquadra (idTeam) come parametro per ottenere l'allenatore
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String record = "Date: " + rs.getString("data") + ", Focus: " + rs.getString("focus") +
                        ", Player: " + rs.getString("nome_giocatore") + " " + rs.getString("cognome_giocatore");
                trainingHistory.add(record);
            }
        }
        return trainingHistory;
    }

    @Override
    public List<String> getTradeHistory(int idTeam) throws SQLException {
        List<String> tradeHistory = new ArrayList<>();
        String sql = "SELECT S1.nome AS squadra_iniziale, S2.nome AS squadra_destinazione, " +
                "P1.nome AS player_iniziale, P2.nome AS player_destinazione, " +
                "T.data, T.risultato " +
                "FROM SCAMBIO T " +
                "JOIN CONTRATTO C1 ON T.idContratto1 = C1.idContratto " +
                "JOIN CONTRATTO C2 ON T.idContratto2 = C2.idContratto " +
                "JOIN SQUADRA S1 ON C1.idSquadra = S1.idSquadra " +
                "JOIN SQUADRA S2 ON C2.idSquadra = S2.idSquadra " +
                "JOIN GIOCATORE P1 ON C1.idGiocatore = P1.idGiocatore " +
                "JOIN GIOCATORE P2 ON C2.idGiocatore = P2.idGiocatore " +
                "WHERE S1.idSquadra = ? OR S2.idSquadra = ? " +
                "ORDER BY T.data DESC"; // Orders by most recent trade

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String record = "Date: " + rs.getString("data") + ", Result: " + rs.getString("risultato") +
                        ", From: " + rs.getString("squadra_iniziale") + " to " + rs.getString("squadra_destinazione") +
                        ", Players: " + rs.getString("player_iniziale") + " -> " + rs.getString("player_destinazione");
                tradeHistory.add(record);
            }
        }
        return tradeHistory;
    }

    @Override
    public List<String> getContractHistory(int idTeam) throws SQLException {
        List<String> contractHistory = new ArrayList<>();
        String sql = "SELECT G.nome AS nome_giocatore, G.cognome AS cognome_giocatore, " +
                "C.data, C.durata, C.stipendio, S.nome AS squadra_nome " +
                "FROM CONTRATTO C " +
                "JOIN GIOCATORE G ON C.idGiocatore = G.idGiocatore " +
                "JOIN SQUADRA S ON C.idSquadra = S.idSquadra " +
                "WHERE S.idSquadra = ? AND C.stato = TRUE " +
                "ORDER BY C.data DESC"; // Orders by most recent contract

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String record = "Player: " + rs.getString("nome_giocatore") + " " + rs.getString("cognome_giocatore") +
                        ", Team: " + rs.getString("squadra_nome") + ", Date: " + rs.getString("data") +
                        ", Duration: " + rs.getInt("durata") + " years, Salary: " + rs.getDouble("stipendio");
                contractHistory.add(record);
            }
        }
        return contractHistory;
    }

    @Override
    public int getTeamIdByGM(String username, String password) {
        int teamId = -1;
        String query = "SELECT S.idSquadra FROM SQUADRA S " +
                "JOIN GM G ON S.idGM = G.idGM " +
                "WHERE G.mail = ? AND G.password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                teamId = rs.getInt("idSquadra"); // Ottieni idSquadra dalla tabella SQUADRA
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamId;
    }

    // Nel Model (aggiungi questo metodo)
    @Override
    public void removePlayerByNameAndSurname(String playerName, String playerSurname) throws SQLException {
        String sql = "DELETE FROM Player WHERE name = ? AND surname = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playerName);
            stmt.setString(2, playerSurname);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No player found with name: " + playerName + " and surname: " + playerSurname);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error removing player: " + ex.getMessage());
        }
    }

    @Override
    public void removeObserverByNameAndSurname(String name, String surname) throws SQLException {
        String query = "DELETE FROM Observer WHERE name = ? AND surname = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No observer found with the provided name and surname.");
            }
        } catch (SQLException ex) {
            throw new SQLException("Error removing observer: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void removeCoachByNameAndSurname(String name, String surname) throws SQLException {
        String query = "DELETE FROM Coach WHERE name = ? AND surname = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No coach found with the provided name and surname.");
            }
        } catch (SQLException ex) {
            throw new SQLException("Error removing coach: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<String> getAllTeams() throws SQLException {
        List<String> teams = new ArrayList<>();
        String query = "SELECT nome FROM SQUADRA"; // Usa il nome della tua tabella
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                teams.add(rs.getString("nome"));
            }
        }
        return teams;
    }

    @Override
    public List<String> getExpiringContractsForTeam(String teamName) throws SQLException {
        List<String> contracts = new ArrayList<>();
        String query = "SELECT g.nome, g.cognome, c.data, c.durata FROM CONTRATTO c " +
                "JOIN GIOCATORE g ON c.idGiocatore = g.idGiocatore " +
                "JOIN SQUADRA s ON c.idSquadra = s.idSquadra " +
                "WHERE s.nome = ? AND DATEDIFF(c.data, CURDATE()) <= 365";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, teamName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contracts.add(rs.getString("g.nome") + " " + rs.getString("g.cognome") +
                            " - " + rs.getDate("c.data") + " (Durata: " + rs.getInt("c.durata") + " anni)");
                }
            }
        }
        return contracts;
    }

    @Override
    public List<String> getSortedPlayers(String teamName, String sortOption) throws SQLException {
        List<String> players = new ArrayList<>();
        String query = "";
        switch (sortOption) {
            case "Sort by Evaluation":
                query = "SELECT g.nome, g.cognome FROM GIOCATORE g " +
                        "JOIN CONTRATTO c ON c.idGiocatore = g.idGiocatore " +
                        "JOIN SQUADRA s ON c.idSquadra = s.idSquadra " +
                        "WHERE s.nome = ? ORDER BY g.valutazione DESC";
                break;
            case "Sort by Age":
                query = "SELECT g.nome, g.cognome FROM GIOCATORE g " +
                        "JOIN CONTRATTO c ON c.idGiocatore = g.idGiocatore " +
                        "JOIN SQUADRA s ON c.idSquadra = s.idSquadra " +
                        "WHERE s.nome = ? ORDER BY g.eta ASC";
                break;
            case "Sort by Role":
                query = "SELECT g.nome, g.cognome FROM GIOCATORE g " +
                        "JOIN CONTRATTO c ON c.idGiocatore = g.idGiocatore " +
                        "JOIN SQUADRA s ON c.idSquadra = s.idSquadra " +
                        "WHERE s.nome = ? ORDER BY g.categoria";
                break;
        }
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, teamName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    players.add(rs.getString("g.nome") + " " + rs.getString("g.cognome"));
                }
            }
        }
        return players;
    }

    @Override
    public List<Team> getTeamsExcludingLogged(int idLoggedTeam) {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM SQUADRA WHERE idSquadra != ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idLoggedTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Team team = new Team(
                        rs.getString("nome"),
                        rs.getString("citta"),
                        rs.getInt("idGM"),
                        rs.getInt("idAllenatore"),
                        rs.getInt("idOsservatore"),
                        rs.getInt("n_giocatori"),
                        rs.getInt("max_salariale"));
                team.setIdTeam(rs.getInt("idSquadra")); // Assegna manualmente l'id
                teams.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    @Override
    public List<Player> getPlayersByTeam(int idTeam) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT g.* FROM GIOCATORE g " +
                "JOIN CONTRATTO c ON c.idGiocatore = g.idGiocatore " +
                "WHERE c.idSquadra = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("eta"),
                        Player.Position.valueOf(rs.getString("position")),
                        Player.Category.valueOf(rs.getString("categoria")),
                        rs.getInt("valutazione"),
                        rs.getInt("anni_esperienza"),
                        rs.getBoolean("freeagent"));
                player.setIdPlayer(rs.getInt("idGiocatore"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public Player getPlayerById(int idPlayer) {
        String query = "SELECT * FROM GIOCATORE WHERE idGiocatore = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idPlayer);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Player player = new Player(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("eta"),
                        Player.Position.valueOf(rs.getString("position")),
                        Player.Category.valueOf(rs.getString("categoria")),
                        rs.getInt("valutazione"),
                        rs.getInt("anni_esperienza"),
                        rs.getBoolean("freeagent"));
                player.setIdPlayer(rs.getInt("idGiocatore"));
                return player;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Player> getPlayersSortedByRating(int idTeam) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT g.* FROM GIOCATORE g " +
                "JOIN CONTRATTO c ON c.idGiocatore = g.idGiocatore " +
                "WHERE c.idSquadra = ? ORDER BY g.valutazione DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("eta"),
                        Player.Position.valueOf(rs.getString("position")),
                        Player.Category.valueOf(rs.getString("categoria")),
                        rs.getInt("valutazione"),
                        rs.getInt("anni_esperienza"),
                        rs.getBoolean("freeagent"));
                player.setIdPlayer(rs.getInt("idGiocatore"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Player> getPlayersSortedByAge(int idTeam) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT g.* FROM GIOCATORE g " +
                "JOIN CONTRATTO c ON c.idGiocatore = g.idGiocatore " +
                "WHERE c.idSquadra = ? ORDER BY g.eta ASC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("eta"),
                        Player.Position.valueOf(rs.getString("position")),
                        Player.Category.valueOf(rs.getString("categoria")),
                        rs.getInt("valutazione"),
                        rs.getInt("anni_esperienza"),
                        rs.getBoolean("freeagent"));
                player.setIdPlayer(rs.getInt("idGiocatore"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Player> getPlayersSortedByPosition(int idTeam) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT g.* FROM GIOCATORE g " +
                "JOIN CONTRATTO c ON c.idGiocatore = g.idGiocatore " +
                "WHERE c.idSquadra = ? ORDER BY FIELD(g.position, 'PG', 'SG', 'SF', 'PF', 'C')";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("eta"),
                        Player.Position.valueOf(rs.getString("position")),
                        Player.Category.valueOf(rs.getString("categoria")),
                        rs.getInt("valutazione"),
                        rs.getInt("anni_esperienza"),
                        rs.getBoolean("freeagent"));
                player.setIdPlayer(rs.getInt("idGiocatore"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Trade> getTradesByTeam(int idTeam) {
        List<Trade> trades = new ArrayList<>();
        String query = "SELECT s.* FROM SCAMBIO s " +
                "JOIN CONTRATTO c1 ON s.idContratto1 = c1.idContratto " +
                "JOIN CONTRATTO c2 ON s.idContratto2 = c2.idContratto " +
                "WHERE c1.idSquadra = ? OR c2.idSquadra = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trade trade = new Trade(
                        rs.getInt("idContratto1"),
                        rs.getInt("idContratto2"),
                        Trade.State.valueOf(rs.getString("risultato").replace(" ", "")), // Converte ENUM dal DB
                        rs.getDate("data").toLocalDate());
                trade.setIdTrade(rs.getInt("idScambio"));
                trades.add(trade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trades;
    }

    @Override
    public void updateTradeStatus(int idScambio, String status) throws SQLException {
        String query = "UPDATE SCAMBIO SET risultato = ? WHERE idScambio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, idScambio);
            stmt.executeUpdate();
        }
    }

    public void proposeTrade(Player playerToTrade, Player selectedPlayerForTrade, Team selectedTeam)
            throws SQLException {
        String getTeamQuery = "SELECT idSquadra FROM CONTRATTO WHERE idGiocatore = ? AND stato = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(getTeamQuery)) {
            stmt.setInt(1, playerToTrade.getIdPlayer());
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int playerTeamId = resultSet.getInt("idSquadra");

                    // Recupero del contratto del primo giocatore
                    String getContractQuery = "SELECT idContratto FROM CONTRATTO WHERE idGiocatore = ? AND idSquadra = ? AND stato = TRUE";
                    int contract1Id, contract2Id;

                    try (PreparedStatement contractStmt1 = connection.prepareStatement(getContractQuery)) {
                        contractStmt1.setInt(1, playerToTrade.getIdPlayer());
                        contractStmt1.setInt(2, playerTeamId);
                        try (ResultSet contractResult1 = contractStmt1.executeQuery()) {
                            contract1Id = (contractResult1.next()) ? contractResult1.getInt("idContratto") : -1;
                        }
                    }

                    // Recupero del contratto del secondo giocatore
                    try (PreparedStatement contractStmt2 = connection.prepareStatement(getContractQuery)) {
                        contractStmt2.setInt(1, selectedPlayerForTrade.getIdPlayer());
                        contractStmt2.setInt(2, selectedTeam.getIdTeam());
                        try (ResultSet contractResult2 = contractStmt2.executeQuery()) {
                            contract2Id = (contractResult2.next()) ? contractResult2.getInt("idContratto") : -1;
                        }
                    }

                    if (contract1Id != -1 && contract2Id != -1) {
                        String tradeQuery = "INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement tradeStmt = connection.prepareStatement(tradeQuery)) {
                            tradeStmt.setInt(1, contract1Id);
                            tradeStmt.setInt(2, contract2Id);
                            tradeStmt.setString(3, "In corso");
                            tradeStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                            tradeStmt.executeUpdate();
                        }
                    } else {
                        throw new SQLException("One of the contracts is not found or not active.");
                    }
                } else {
                    throw new SQLException("The player does not have an active contract.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error while proposing the trade", e);
        }
    }

    @Override
    public List<Player> getTeamRoster(int idTeam) throws SQLException {
        List<Player> teamRoster = new ArrayList<>();
        String query = "SELECT G.idGiocatore, G.nome, G.cognome, G.position, G.categoria, G.valutazione, G.anni_esperienza, G.eta, G.freeagent "
                + "FROM GIOCATORE G "
                + "JOIN CONTRATTO C ON G.idGiocatore = C.idGiocatore "
                + "WHERE C.idSquadra = ? AND C.stato = TRUE";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idTeam);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int idPlayer = resultSet.getInt("idGiocatore");
                String name = resultSet.getString("nome");
                String surname = resultSet.getString("cognome");
                String positionStr = resultSet.getString("position");
                String categoryStr = resultSet.getString("categoria");
                int rank = resultSet.getInt("valutazione");
                int experience = resultSet.getInt("anni_esperienza");
                int age = resultSet.getInt("eta");
                boolean freeAgent = resultSet.getBoolean("freeagent");

                Player.Position position = Player.Position.valueOf(positionStr);
                Player.Category category = Player.Category.valueOf(categoryStr);
                Player player = new Player(name, surname, age, position, category, rank, experience, freeAgent);
                player.setIdPlayer(idPlayer);

                teamRoster.add(player);
            }
        } catch (SQLException e) {
            throw new SQLException("Error while retrieving team roster", e);
        }

        return teamRoster;
    }

    @Override
    public Team getTeamByPlayerId(int idPlayer) throws SQLException {
        // Query per ottenere la squadra del giocatore
        String query = "SELECT S.* FROM SQUADRA S " +
                "JOIN CONTRATTO C ON S.idSquadra = C.idSquadra " +
                "WHERE C.idGiocatore = ?";

        // Esegui la query
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPlayer); // Passa l'ID del giocatore
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crea un oggetto Team dalla risposta
                    Team team = new Team(
                            resultSet.getString("nome"),
                            resultSet.getString("citta"),
                            resultSet.getInt("idGM"),
                            resultSet.getInt("idAllenatore"),
                            resultSet.getInt("idOsservatore"),
                            resultSet.getInt("n_giocatori"),
                            resultSet.getInt("max_salariale"));
                    // Imposta l'ID della squadra
                    team.setIdTeam(resultSet.getInt("idSquadra"));
                    return team;
                } else {
                    return null; // Se non trovata, restituisci null
                }
            }
        }
    }

    public Contract getContractByPlayerId(int idPlayer) throws SQLException {
        // Query per ottenere il contratto del giocatore tramite l'idPlayer
        String query = "SELECT * FROM CONTRATTO WHERE idGiocatore = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPlayer); // Passa l'ID del giocatore
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crea un oggetto Contract dalla risposta
                    return new Contract(
                            resultSet.getInt("idGiocatore"),
                            resultSet.getInt("idSquadra"),
                            resultSet.getInt("stipendio"),
                            resultSet.getDate("dataInizio").toLocalDate(), // Conversione da java.sql.Date a LocalDate
                            resultSet.getInt("anni"),
                            resultSet.getBoolean("stato") // Se lo stato è booleano
                    );
                } else {
                    return null; // Se non trovato, restituisci null
                }
            }
        }
    }

    public Team getTeamByContract(Contract playerToTradeContract) throws SQLException {
        // Query per ottenere la squadra tramite l'idSquadra nel contratto
        String query = "SELECT S.* FROM SQUADRA S WHERE S.idSquadra = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerToTradeContract.getIdTeam()); // Passa l'idSquadra dal contratto
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crea un oggetto Team dalla risposta
                    Team team = new Team(
                            resultSet.getString("nome"),
                            resultSet.getString("citta"),
                            resultSet.getInt("idGM"),
                            resultSet.getInt("idAllenatore"),
                            resultSet.getInt("idOsservatore"),
                            resultSet.getInt("n_giocatori"),
                            resultSet.getInt("max_salariale"));
                    team.setIdTeam(resultSet.getInt("idSquadra")); // Imposta l'idSquadra
                    return team;
                } else {
                    return null; // Se non trovato, restituisci null
                }
            }
        }
    }

    @Override
    public List<Trade> getTradesInProgress(int idTeam) throws SQLException {
        String query = "SELECT t.idScambio, t.idContratto1, t.idContratto2, t.risultato, t.data " +
                "FROM SCAMBIO t " +
                "JOIN CONTRATTO c1 ON c1.idContratto = t.idContratto1 " +
                "JOIN CONTRATTO c2 ON c2.idContratto = t.idContratto2 " +
                "WHERE (c1.idSquadra = ? OR c2.idSquadra = ?) " +
                "AND t.risultato = 'In corso'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Impostiamo l'id della squadra (idTeam) sia per il primo che per il secondo
            // contratto
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idTeam);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Trade> tradesInProgress = new ArrayList<>();

                while (rs.next()) {
                    int idTrade = rs.getInt("idScambio");
                    int idContract1 = rs.getInt("idContratto1");
                    int idContract2 = rs.getInt("idContratto2");
                    LocalDate date = rs.getDate("data").toLocalDate();

                    // Aggiungiamo il trade in corso alla lista
                    tradesInProgress.add(new Trade(idContract1, idContract2, Trade.State.In_Corso, date));
                }

                return tradesInProgress;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rilancia l'eccezione in caso di errore
        }
    }

    @Override
    public void acceptTrade(Trade selectedTrade) throws SQLException {
        String updateTradeQuery = "UPDATE SCAMBIO SET risultato = 'Accettato' WHERE idScambio = ?";
        String updateContractQuery = "UPDATE CONTRATTO SET stato = false WHERE idContratto = ?";
        String insertNewContractQuery = "INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio, stato) "
                +
                "SELECT ?, idGiocatore, data, durata, stipendio, true FROM CONTRATTO WHERE idContratto = ?";

        try (PreparedStatement updateTradeStmt = connection.prepareStatement(updateTradeQuery);
                PreparedStatement updateContractStmt = connection.prepareStatement(updateContractQuery);
                PreparedStatement insertNewContractStmt = connection.prepareStatement(insertNewContractQuery)) {

            // 1. Aggiorna lo stato dello scambio su "Accettato"
            updateTradeStmt.setInt(1, selectedTrade.getIdTrade());
            updateTradeStmt.executeUpdate();

            // 2. Disattiva il contratto del primo giocatore (idContratto1)
            updateContractStmt.setInt(1, selectedTrade.getIdContract1());
            updateContractStmt.executeUpdate();

            // 3. Disattiva il contratto del secondo giocatore (idContratto2)
            updateContractStmt.setInt(1, selectedTrade.getIdContract2());
            updateContractStmt.executeUpdate();

            // 4. Crea un nuovo contratto per il primo giocatore con la nuova squadra
            // (scambiando gli idSquadra)
            insertNewContractStmt.setInt(1, selectedTrade.getIdContract2()); // Nuova squadra per il primo giocatore
            insertNewContractStmt.setInt(2, selectedTrade.getIdContract1());
            insertNewContractStmt.executeUpdate();

            // 5. Crea un nuovo contratto per il secondo giocatore con la nuova squadra
            // (scambiando gli idSquadra)
            insertNewContractStmt.setInt(1, selectedTrade.getIdContract1()); // Nuova squadra per il secondo giocatore
            insertNewContractStmt.setInt(2, selectedTrade.getIdContract2());
            insertNewContractStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rilancia l'eccezione se qualcosa va storto
        }
    }

    @Override
    public void rejectTrade(Trade selectedTrade) throws SQLException {
        String updateTradeQuery = "UPDATE SCAMBIO SET risultato = 'Rifiutato' WHERE idScambio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateTradeQuery)) {
            // 1. Imposta lo stato dello scambio su "Rifiutato"
            stmt.setInt(1, selectedTrade.getIdTrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rilancia l'eccezione in caso di errore
        }
    }

}
