package model;

import data.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import core.Model;

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

            while (rs.next()) {
                history.add(rs.getString("data") + " - " +
                        rs.getString("risultato") + " - " +
                        rs.getString("squadra_casa") + " vs " +
                        rs.getString("squadra_ospite"));
            }

        }
        return history;
    }

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

    @Override
    public void removePlayer(int idGiocatore) throws SQLException {
        String sql = "DELETE FROM GIOCATORE WHERE idGiocatore = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGiocatore);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addCoach(String nome, String cognome, double stipendio, int anniEsperienza, boolean free)
            throws SQLException {
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
    public void addObserver(String nome, String cognome, double stipendio, int anniEsperienza, boolean free)
            throws SQLException {
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
    public void addGame(int idSquadra1, int idSquadra2, String risultato, int stadium, LocalDate data)
            throws SQLException {
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

    public List<Trade> getTradesByTeam(int idTeam) throws SQLException {
        List<Trade> trades = new ArrayList<>();
        String sql = "SELECT s.* FROM SCAMBIO s " +
                "JOIN CONTRATTO c1 ON s.idContratto1 = c1.idContratto " +
                "JOIN CONTRATTO c2 ON s.idContratto2 = c2.idContratto " +
                "WHERE c1.idSquadra = ? OR c2.idSquadra = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trade trade = new Trade(
                        rs.getInt("idContratto1"),
                        rs.getInt("idContratto2"),
                        Trade.State.fromDbValue(rs.getString("risultato")), // Usa fromDbValue invece di valueOf
                        rs.getDate("data").toLocalDate());
                trade.setIdTrade(rs.getInt("idScambio"));
                trades.add(trade);
            }
        }
        return trades;
    }

    public Contract getContractByPlayerId(int idPlayer) throws SQLException {
        String query = "SELECT * FROM CONTRATTO WHERE idGiocatore = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPlayer); // Passa l'ID del giocatore
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
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
        List<Trade> trades = new ArrayList<>();
        String sql = "SELECT s.* FROM SCAMBIO s " +
                "JOIN CONTRATTO c1 ON s.idContratto1 = c1.idContratto " +
                "JOIN CONTRATTO c2 ON s.idContratto2 = c2.idContratto " +
                "WHERE (c1.idSquadra = ? OR c2.idSquadra = ?) " +
                "AND s.risultato = 'In corso'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idTeam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trade trade = new Trade(
                        rs.getInt("idContratto1"),
                        rs.getInt("idContratto2"),
                        Trade.State.fromDbValue(rs.getString("risultato")),
                        rs.getDate("data").toLocalDate());
                trade.setIdTrade(rs.getInt("idScambio"));
                trades.add(trade);
            }
        }
        return trades;
    }

    @Override
    public Contract getActiveContract(int idPlayer) throws SQLException {
        String sql = "SELECT * FROM CONTRATTO WHERE idGiocatore = ? AND stato = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPlayer);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Contract contract = new Contract(
                        rs.getInt("idGiocatore"),
                        rs.getInt("idSquadra"),
                        rs.getInt("stipendio"),
                        rs.getDate("data").toLocalDate(),
                        rs.getInt("durata"),
                        rs.getBoolean("stato"));
                contract.setIdContract(rs.getInt("idContratto")); // Aggiungi questa linea
                return contract;
            }
            return null;
        }
    }

    @Override
    public Team getTeamById(int idTeam) throws SQLException {
        String sql = "SELECT * FROM SQUADRA WHERE idSquadra = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTeam);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Team team = new Team(
                        rs.getString("nome"),
                        rs.getString("citta"),
                        rs.getInt("idGM"),
                        rs.getInt("idAllenatore"),
                        rs.getInt("idOsservatore"),
                        rs.getInt("n_giocatori"),
                        rs.getInt("max_salariale"));
                team.setIdTeam(rs.getInt("idSquadra"));
                return team;
            }
            return null;
        }
    }

    @Override
    public void acceptTrade(int idTrade) throws SQLException {
        // Ottieni i dettagli dello scambio
        String selectSql = "SELECT * FROM SCAMBIO WHERE idScambio = ?";
        int idContract1 = 0;
        int idContract2 = 0;

        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setInt(1, idTrade);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idContract1 = rs.getInt("idContratto1");
                idContract2 = rs.getInt("idContratto2");
            } else {
                throw new SQLException("Trade not found");
            }
        }

        // Ottieni i dettagli dei contratti
        Contract contract1 = getContractById(idContract1);
        Contract contract2 = getContractById(idContract2);

        if (contract1 == null || contract2 == null) {
            throw new SQLException("One or both contracts not found");
        }

        // Inizia una transazione
        connection.setAutoCommit(false);

        try {
            // 1. Disattiva i contratti esistenti (stato = false, durata = 0)
            String disableSql = "UPDATE CONTRATTO SET stato = FALSE, durata = 0 WHERE idContratto IN (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(disableSql)) {
                stmt.setInt(1, idContract1);
                stmt.setInt(2, idContract2);
                stmt.executeUpdate();
            }

            // 2. Crea i nuovi contratti con squadre invertite
            String insertSql = "INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio, stato) " +
                    "VALUES (?, ?, ?, ?, ?, TRUE)";

            // Nuovo contratto per il giocatore 1 con la squadra del giocatore 2
            try (PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, contract2.getIdTeam());
                stmt.setInt(2, contract1.getIdPlayer());
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.setInt(4, contract1.getYears());
                stmt.setInt(5, contract1.getSalary());
                stmt.executeUpdate();
            }

            // Nuovo contratto per il giocatore 2 con la squadra del giocatore 1
            try (PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, contract1.getIdTeam());
                stmt.setInt(2, contract2.getIdPlayer());
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.setInt(4, contract2.getYears());
                stmt.setInt(5, contract2.getSalary());
                stmt.executeUpdate();
            }

            // 3. Aggiorna lo stato dello scambio a "Accettato"
            String updateTradeSql = "UPDATE SCAMBIO SET risultato = 'Accettato' WHERE idScambio = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateTradeSql)) {
                stmt.setInt(1, idTrade);
                stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void rejectTrade(int idTrade) throws SQLException {
        // Semplicemente aggiorna lo stato dello scambio a "Rifiutato"
        String sql = "UPDATE SCAMBIO SET risultato = 'Rifiutato' WHERE idScambio = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTrade);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Trade not found");
            }
        }
    }

    @Override
    public Contract getContractById(int idContract) throws SQLException {
        String sql = "SELECT * FROM CONTRATTO WHERE idContratto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idContract);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Contract contract = new Contract(
                        rs.getInt("idGiocatore"),
                        rs.getInt("idSquadra"),
                        rs.getInt("stipendio"),
                        rs.getDate("data").toLocalDate(),
                        rs.getInt("durata"),
                        rs.getBoolean("stato"));
                contract.setIdContract(rs.getInt("idContratto"));
                return contract;
            }
            return null;
        }
    }

    // Ottieni solo giocatori con contratto attivo (stato = TRUE)
    @Override
    public List<Player> getActivePlayersByTeam(int idTeam) throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT g.* FROM GIOCATORE g " +
                "JOIN CONTRATTO c ON g.idGiocatore = c.idGiocatore " +
                "WHERE c.idSquadra = ? AND c.stato = TRUE";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        }
        return players;
    }

    // Ottieni contratti in scadenza (durata = 1 e stato = TRUE)
    @Override
    public List<Player> getExpiringContracts(int idTeam) throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT g.* FROM GIOCATORE g " +
                "JOIN CONTRATTO c ON g.idGiocatore = c.idGiocatore " +
                "WHERE c.idSquadra = ? AND c.stato = TRUE AND c.durata = 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        }
        return players;
    }

    // I metodi esistenti getPlayersSortedByRating, getPlayersSortedByAge,
    // getPlayersSortedByPosition devono usare getActivePlayersByTeam come base
    @Override
    public List<Player> getPlayersSortedByRating(int idTeam) throws SQLException {
        List<Player> players = getActivePlayersByTeam(idTeam);
        players.sort((p1, p2) -> Integer.compare(p2.getRank(), p1.getRank()));
        return players;
    }

    @Override
    public List<Player> getPlayersSortedByAge(int idTeam) throws SQLException {
        List<Player> players = getActivePlayersByTeam(idTeam);
        players.sort((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
        return players;
    }

    @Override
    public List<Player> getPlayersSortedByPosition(int idTeam) throws SQLException {
        List<Player> players = getActivePlayersByTeam(idTeam);
        players.sort((p1, p2) -> p1.getPosition().compareTo(p2.getPosition()));
        return players;
    }

    @Override
    public List<Player> getAllPlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM GIOCATORE";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        }
        return players;
    }

    @Override
    public List<Coach> getAllCoaches() throws SQLException {
        List<Coach> coaches = new ArrayList<>();
        String sql = "SELECT * FROM ALLENATORE";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coach coach = new Coach(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("stipendio"),
                        rs.getInt("anni_esperienza"),
                        rs.getBoolean("free"));
                coach.setIdCoach(rs.getInt("idAllenatore"));
                coaches.add(coach);
            }
        }
        return coaches;
    }

    @Override
    public List<Observer> getAllObservers() throws SQLException {
        List<Observer> observers = new ArrayList<>();
        String sql = "SELECT * FROM OSSERVATORE";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Observer observer = new Observer(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("stipendio"),
                        rs.getInt("anni_esperienza"),
                        rs.getBoolean("free"));
                observer.setIdObserver(rs.getInt("idOsservatore"));
                observers.add(observer);
            }
        }
        return observers;
    }

    @Override
    public List<Game> getAllGames() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM PARTITA";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Game game = new Game(
                        rs.getInt("idSquadra1"),
                        rs.getInt("idSquadra2"),
                        rs.getString("risultato"),
                        rs.getInt("idStadio"),
                        rs.getDate("data").toLocalDate());
                game.setIdGame(rs.getInt("idPartita"));
                games.add(game);
            }
        }
        return games;
    }

    @Override
    public void addPlayer(String name, String surname, int age, String position,
            String category, double rating, int experience,
            boolean freeAgent) throws SQLException {

        // Validazione degli input
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        if (surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Player surname cannot be empty");
        }
        if (age < 18 || age > 45) {
            throw new IllegalArgumentException("Player age must be between 18 and 45");
        }
        if (rating < 0.0 || rating > 99.9) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 99.9");
        }
        if (experience < 0 || experience > 30) {
            throw new IllegalArgumentException("Experience must be between 0 and 30 years");
        }

        // Controllo che position e category siano valori validi (anche se la combo box
        // dovrebbe già filtrarli)
        try {
            Player.Position.valueOf(position); // Verifica che sia un enum valido
            Player.Category.valueOf(category); // Verifica che sia un enum valido
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid position or category value");
        }

        String sql = "INSERT INTO GIOCATORE (nome, cognome, eta, position, categoria, " +
                "valutazione, anni_esperienza, freeagent) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Impostazione parametri
            stmt.setString(1, name.trim());
            stmt.setString(2, surname.trim());
            stmt.setInt(3, age);
            stmt.setString(4, position);
            stmt.setString(5, category);
            stmt.setDouble(6, rating);
            stmt.setInt(7, experience);
            stmt.setBoolean(8, freeAgent);

            // Esecuzione
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating player failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    System.out.println("Created player with ID: " + newId);
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                throw new SQLException("Database constraint violation: Possible duplicate player", e);
            }
            throw e;
        }
    }

    @Override
    public void proposeTrade(int idContract, int idContract2) throws SQLException {
        // Debug: stampa gli ID contratti ricevuti
        System.out.println("[DEBUG] Starting proposeTrade with contracts: " + idContract + " and " + idContract2);

        // Verifica che gli ID siano validi
        if (idContract <= 0 || idContract2 <= 0) {
            String errorMsg = "Invalid contract IDs. ID1: " + idContract + ", ID2: " + idContract2;
            System.out.println("[ERROR] " + errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        // Verifica che i contratti siano diversi
        if (idContract == idContract2) {
            String errorMsg = "Cannot trade the same contract. ID: " + idContract;
            System.out.println("[ERROR] " + errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        // Verifica che i contratti esistano e siano attivi
        if (!isContractActive(idContract)) {
            String errorMsg = "First contract is not active or doesn't exist. ID: " + idContract;
            System.out.println("[ERROR] " + errorMsg);
            throw new IllegalStateException(errorMsg);
        }

        if (!isContractActive(idContract2)) {
            String errorMsg = "Second contract is not active or doesn't exist. ID: " + idContract2;
            System.out.println("[ERROR] " + errorMsg);
            throw new IllegalStateException(errorMsg);
        }

        // Verifica che i contratti non appartengano alla stessa squadra
        int idTeam1 = getTeamIdByContract(idContract);
        int idTeam2 = getTeamIdByContract(idContract2);

        if (idTeam1 == idTeam2) {
            String errorMsg = "Both contracts belong to the same team. Team ID: " + idTeam1;
            System.out.println("[ERROR] " + errorMsg);
            throw new IllegalStateException(errorMsg);
        }

        // Crea la proposta di scambio nel database
        String sql = "INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data) VALUES (?, ?, 'In corso', ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idContract);
            stmt.setInt(2, idContract2);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));

            int rowsAffected = stmt.executeUpdate();
            System.out.println("[DEBUG] Trade proposal inserted successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("[ERROR] Database error during trade proposal: " + e.getMessage());
            throw e;
        }
    }

    // Metodi di supporto aggiuntivi
    private boolean isContractActive(int idContract) throws SQLException {
        String sql = "SELECT 1 FROM CONTRATTO WHERE idContratto = ? AND stato = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idContract);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    private int getTeamIdByContract(int idContract) throws SQLException {
        String sql = "SELECT idSquadra FROM CONTRATTO WHERE idContratto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idContract);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idSquadra");
            }
            throw new SQLException("Contract not found. ID: " + idContract);
        }
    }

    @Override
    public Team getTeamByName(String fullName) throws SQLException {
        if (fullName == null || fullName.trim().isEmpty() || !fullName.contains(" ")) {
            // Se il nome non è valido o non contiene uno spazio, non può essere "Città Nome"
            return null;
        }

        // Divide il nome completo in città e nome della squadra
        int lastSpaceIndex = fullName.lastIndexOf(' ');
        String city = fullName.substring(0, lastSpaceIndex).trim();
        String name = fullName.substring(lastSpaceIndex + 1).trim();

        Team team = null;
        String sql = "SELECT * FROM team WHERE city = ? AND name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, city);
            pstmt.setString(2, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Se troviamo una corrispondenza, creiamo un oggetto Team
                    team = new Team(
                            rs.getString("name"),
                            rs.getString("city"),
                            rs.getInt("idGM"),
                            rs.getInt("idCoach"),
                            rs.getInt("idObserver"),
                            rs.getInt("nPlayers"),
                            rs.getInt("maxCap")
                    );
                    // Impostiamo l'ID della squadra, che non è nel costruttore originale
                    team.setIdTeam(rs.getInt("idTeam"));
                }
            }
        }
        return team; // Restituisce l'oggetto team o null se non trovato
    }

}
