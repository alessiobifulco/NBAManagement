-- OP1: Visualizzazione della rosa di una squadra
SELECT G.nome, G.cognome, G.position, G.categoria, G.valutazione
FROM GIOCATORE G
JOIN CONTRATTO C ON G.idGiocatore = C.idGiocatore
WHERE C.idSquadra = 1 -- Sostituisci con l'ID della squadra
AND C.stato = TRUE; -- Solo contratti attivi

-- OP2: Ricerca di free agent
SELECT nome, cognome, position, categoria, valutazione
FROM GIOCATORE
WHERE freeagent = TRUE;

-- OP3: Creazione proposta di scambio
-- Verifica che i contratti siano attivi
SELECT idContratto
FROM CONTRATTO
WHERE idContratto IN (1, 2) -- Sostituisci con gli ID dei contratti da scambiare
AND stato = TRUE;

-- Se i contratti sono attivi, crea la proposta di scambio
INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data)
VALUES (1, 2, 'In corso', NOW()); -- Sostituisci con i valori corretti

-- OP4: Aggiornamento stato scambio
UPDATE SCAMBIO
SET risultato = 'Accettato' -- Oppure 'Rifiutato' o 'In corso'
WHERE idScambio = 1; -- Sostituisci con l'ID dello scambio da aggiornare

-- OP5: Visualizzazione storico partite
SELECT P.data, P.risultato, S1.nome AS squadra_casa, S2.nome AS squadra_ospite
FROM PARTITA P
JOIN SQUADRA S1 ON P.idSquadra1 = S1.idSquadra
JOIN SQUADRA S2 ON P.idSquadra2 = S2.idSquadra
WHERE S1.idSquadra = 1 OR S2.idSquadra = 1; -- Sostituisci con l'ID della squadra desiderata

-- OP6: Visualizzazione dei contratti in scadenza
SELECT G.nome, G.cognome, C.data, C.durata, C.stipendio
FROM CONTRATTO C
JOIN GIOCATORE G ON C.idGiocatore = G.idGiocatore
WHERE C.stato = TRUE
AND C.durata = 1; -- Contratti con durata di 1 anno

-- OP7: Registrazione accesso GM
SELECT idGM
FROM GM
WHERE mail = 'rob.pelinka@lakers.com' -- Sostituisci con la mail inserita
AND password = 'password1' -- Sostituisci con la password inserita
AND idGM IN (SELECT idGM FROM SQUADRA); -- Verifica che il GM gestisca una squadra

-- Se le credenziali sono valide, registra l'accesso
INSERT INTO ACCESSO (data, idGM)
VALUES (NOW(), 1); -- Sostituisci con l'ID del GM

-- OP8: Aggiunta/rimozione membro staff
-- Rimozione allenatore dalla squadra e lo rende free
UPDATE SQUADRA
SET idAllenatore = NULL
WHERE idSquadra = 1; -- Sostituisci con l'ID della squadra

UPDATE ALLENATORE
SET free = TRUE
WHERE idAllenatore = 2; -- Sostituisci con l'ID dell'allenatore da rendere free

-- Aggiungi il nuovo allenatore alla squadra
UPDATE SQUADRA
SET idAllenatore = 3 -- Sostituisci con l'ID del nuovo allenatore
WHERE idSquadra = 1; -- Sostituisci con l'ID della squadra

-- OP9: Aggiunta contratto a una squadra
SELECT n_giocatori
FROM SQUADRA
WHERE idSquadra = 1; -- Sostituisci con l'ID della squadra

SELECT idGiocatore
FROM GIOCATORE
WHERE idGiocatore = 1 -- Sostituisci con l'ID del giocatore
AND freeagent = TRUE;

SELECT max_salariale
FROM SQUADRA
WHERE idSquadra = 1; -- Sostituisci con l'ID della squadra

-- Se tutte le condizioni sono soddisfatte, aggiungi il contratto
INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio, stato)
VALUES (1, 1, NOW(), 3, 10000000.00, TRUE); -- Sostituisci con i valori corretti

-- OP10: Pianificazione allenamento
INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus)
VALUES (1, 'Gruppo', NULL, 120, '2023-10-20', 'Difesa'); -- Sostituisci con i valori corretti

INSERT INTO ESERCIZIO_IN_ALLENAMENTO (idAllenamento, idEsercizio, serie)
VALUES
(1, 1, 3), -- Tiro da 3 punti (3 serie)
(1, 8, 2); -- Difesa a uomo (2 serie)
