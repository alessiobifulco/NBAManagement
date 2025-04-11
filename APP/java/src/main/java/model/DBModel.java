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
    public void proposeTrade(Player playerToTrade, Player selectedPlayerForTrade, Team selectedTeam)
            throws SQLException {
        // Verifica che i parametri non siano nulli
        if (playerToTrade == null || selectedPlayerForTrade == null || selectedTeam == null) {
            throw new IllegalArgumentException("Player and Team parameters cannot be null");
        }

        // Ottieni i contratti attivi dei giocatori
        Contract contract1 = getActiveContract(playerToTrade.getIdPlayer());
        Contract contract2 = getActiveContract(selectedPlayerForTrade.getIdPlayer());

        if (contract1 == null || contract2 == null) {
            throw new IllegalStateException("Both players must have active contracts");
        }

        // Verifica che i giocatori non appartengano già alla stessa squadra
        if (contract1.getIdTeam() == contract2.getIdTeam()) {
            throw new IllegalStateException("Cannot trade players from the same team");
        }

        // Verifica che uno dei giocatori appartenga alla squadra selezionata
        if (contract1.getIdTeam() != selectedTeam.getIdTeam() && contract2.getIdTeam() != selectedTeam.getIdTeam()) {
            throw new IllegalStateException("At least one player must belong to the selected team");
        }

        // Crea la proposta di scambio nel database
        String sql = "INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data) VALUES (?, ?, 'In corso', ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contract1.getIdContract());
            stmt.setInt(2, contract2.getIdContract());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        }
    }

    @Override
    public Contract getActiveContract(int idPlayer) throws SQLException {
        String sql = "SELECT * FROM CONTRATTO WHERE idGiocatore = ? AND stato = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPlayer);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Contract(
                        rs.getInt("idGiocatore"),
                        rs.getInt("idSquadra"),
                        rs.getInt("stipendio"),
                        rs.getDate("data").toLocalDate(),
                        rs.getInt("durata"),
                        rs.getBoolean("stato"));
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
    public void proposeTrade(int idContract, int idContract2) throws SQLException {
        // Verifica che i contratti siano diversi
        if (idContract == idContract2) {
            throw new IllegalArgumentException("Cannot trade the same contract");
        }

        // Verifica che i contratti esistano e siano attivi
        if (!isContractActive(idContract) || !isContractActive(idContract2)) {
            throw new IllegalStateException("Both contracts must be active");
        }

        // Crea la proposta di scambio nel database
        String sql = "INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data) VALUES (?, ?, 'In corso', ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idContract);
            stmt.setInt(2, idContract2);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        }
    }

    // Metodo helper per verificare se un contratto è attivo
    private boolean isContractActive(int idContract) throws SQLException {
        String sql = "SELECT stato FROM CONTRATTO WHERE idContratto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idContract);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getBoolean("stato");
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

}
