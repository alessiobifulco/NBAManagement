-- Creazione del database
CREATE DATABASE IF NOT EXISTS NBA_Management;
USE NBA_Management;

-- Tabella GM
CREATE TABLE GM (
    idGM INT AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nome_cognome VARCHAR(255) NOT NULL
);

-- Tabella ACCESSO
CREATE TABLE ACCESSO (
    idAccesso INT AUTO_INCREMENT PRIMARY KEY,
    data DATETIME NOT NULL,
    idGM INT,
    FOREIGN KEY (idGM) REFERENCES GM(idGM) ON DELETE CASCADE
);

-- Tabella ALLENATORE
CREATE TABLE ALLENATORE (
    idAllenatore INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    stipendio DECIMAL(15, 2) NOT NULL,
    anni_esperienza INT NOT NULL
);

-- Tabella OSSERVATORE
CREATE TABLE OSSERVATORE (
    idOsservatore INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    stipendio DECIMAL(15, 2) NOT NULL,
    anni_esperienza INT NOT NULL
);

-- Tabella GIOCATORE
CREATE TABLE GIOCATORE (
    idGiocatore INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    categoria ENUM('Superstar', 'All-Star', 'Role Player', 'Bench Player') NOT NULL,
    valutazione DECIMAL(3, 1) NOT NULL,
    anni_esperienza INT NOT NULL,
    freeagent BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabella SQUADRA
CREATE TABLE SQUADRA (
    idSquadra INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    citta VARCHAR(255) NOT NULL,
    idGM INT,
    idAllenatore INT,
    idOsservatore INT,
    n_giocatori INT NOT NULL,
    max_salariale DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (idGM) REFERENCES GM(idGM) ON DELETE SET NULL,
    FOREIGN KEY (idAllenatore) REFERENCES ALLENATORE(idAllenatore) ON DELETE SET NULL,
    FOREIGN KEY (idOsservatore) REFERENCES OSSERVATORE(idOsservatore) ON DELETE SET NULL
);

-- Tabella STADIO
CREATE TABLE STADIO (
    idStadio INT AUTO_INCREMENT PRIMARY KEY,
    capacita INT NOT NULL,
    citta VARCHAR(255) NOT NULL
);



-- Tabella CONTRATTO
CREATE TABLE CONTRATTO (
    idContratto INT AUTO_INCREMENT PRIMARY KEY,
    idSquadra INT,
    idGiocatore INT,
    data DATE NOT NULL,
    durata INT NOT NULL, -- Durata in anni
    stipendio DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (idSquadra) REFERENCES SQUADRA(idSquadra) ON DELETE CASCADE,
    FOREIGN KEY (idGiocatore) REFERENCES GIOCATORE(idGiocatore) ON DELETE CASCADE
);

-- Tabella SCAMBIO
CREATE TABLE SCAMBIO (
    idScambio INT AUTO_INCREMENT PRIMARY KEY,
    idContratto1 INT,
    idContratto2 INT,
    risultato ENUM('Accettato', 'In corso', 'Rifiutato') NOT NULL,
    data DATE NOT NULL,
    FOREIGN KEY (idContratto1) REFERENCES CONTRATTO(idContratto) ON DELETE CASCADE,
    FOREIGN KEY (idContratto2) REFERENCES CONTRATTO(idContratto) ON DELETE CASCADE
);

-- Tabella ALLENAMENTO
CREATE TABLE ALLENAMENTO (
    idAllenamento INT AUTO_INCREMENT PRIMARY KEY,
    idAllenatore INT,
    categoria ENUM('Singolo', 'Gruppo') NOT NULL,
    idGiocatore INT, -- Solo per allenamenti singoli con Superstar
    durata INT NOT NULL, -- Durata in minuti
    data DATE NOT NULL,
    focus VARCHAR(255) NOT NULL,
    FOREIGN KEY (idAllenatore) REFERENCES ALLENATORE(idAllenatore) ON DELETE SET NULL,
    FOREIGN KEY (idGiocatore) REFERENCES GIOCATORE(idGiocatore) ON DELETE SET NULL
);

-- Tabella ESERCIZIO
CREATE TABLE ESERCIZIO (
    idEsercizio INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    fondamentale VARCHAR(255) NOT NULL,
    intensita ENUM('Bassa', 'Media', 'Alta') NOT NULL
);

-- Tabella di collegamento ESERCIZIO_IN_ALLENAMENTO (N:N tra ALLENAMENTO e ESERCIZIO)
CREATE TABLE ESERCIZIO_IN_ALLENAMENTO (
    idAllenamento INT,
    idEsercizio INT,
    PRIMARY KEY (idAllenamento, idEsercizio),
    FOREIGN KEY (idAllenamento) REFERENCES ALLENAMENTO(idAllenamento) ON DELETE CASCADE,
    FOREIGN KEY (idEsercizio) REFERENCES ESERCIZIO(idEsercizio) ON DELETE CASCADE
);

-- Tabella di collegamento GIOCATORI_OSSERVATI (N:N tra GIOCATORE e OSSERVATORE)
CREATE TABLE GIOCATORI_OSSERVATI (
    idGiocatore INT,
    idOsservatore INT,
    PRIMARY KEY (idGiocatore, idOsservatore),
    FOREIGN KEY (idGiocatore) REFERENCES GIOCATORE(idGiocatore) ON DELETE CASCADE,
    FOREIGN KEY (idOsservatore) REFERENCES OSSERVATORE(idOsservatore) ON DELETE CASCADE
);