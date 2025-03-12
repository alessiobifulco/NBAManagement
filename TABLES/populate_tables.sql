USE NBA_Management;

-- Inserimento dati nella tabella GM
INSERT INTO GM (mail, password, nome_cognome) VALUES
('rob.pelinka@lakers.com', 'password1', 'Rob Pelinka'), -- Lakers
('bob.myers@warriors.com', 'password2', 'Bob Myers'), -- Warriors
('james.jones@suns.com', 'password3', 'James Jones'), -- Suns
('lawrence.frank@clippers.com', 'password4', 'Lawrence Frank'), -- Clippers
('monte.mcnair@kings.com', 'password5', 'Monte McNair'), -- Kings
('john.doe@nba.com', 'password6', 'John Doe'), -- Senza squadra
('jane.smith@nba.com', 'password7', 'Jane Smith'), -- Senza squadra
('mike.johnson@nba.com', 'password8', 'Mike Johnson'), -- Senza squadra
('chris.brown@nba.com', 'password9', 'Chris Brown'), -- Senza squadra
('anna.white@nba.com', 'password10', 'Anna White'); -- Senza squadra

-- Inserimento dati nella tabella ALLENATORE
INSERT INTO ALLENATORE (nome, cognome, stipendio, anni_esperienza) VALUES
-- Lakers
('Darvin', 'Ham', 5000000.00, 5), -- Allenatore principale
('Phil', 'Handy', 1500000.00, 10), -- Vice allenatore
-- Warriors
('Steve', 'Kerr', 8000000.00, 10), -- Allenatore principale
('Ron', 'Adams', 1200000.00, 15), -- Vice allenatore
-- Suns
('Monty', 'Williams', 6000000.00, 7), -- Allenatore principale
('Kevin', 'Young', 1000000.00, 8), -- Vice allenatore
-- Clippers
('Tyronn', 'Lue', 7000000.00, 8), -- Allenatore principale
('Jeremy', 'Castleberry', 900000.00, 6), -- Vice allenatore
-- Kings
('Mike', 'Brown', 5000000.00, 12), -- Allenatore principale
('Doug', 'Christie', 1100000.00, 9), -- Vice allenatore
-- Senza squadra
('Mark', 'Jackson', 3000000.00, 10), -- Allenatore senza squadra
('Jeff', 'Van Gundy', 3500000.00, 15); -- Allenatore senza squadra

-- Inserimento dati nella tabella OSSERVATORE
INSERT INTO OSSERVATORE (nome, cognome, stipendio, anni_esperienza) VALUES
-- Lakers
('Brian', 'Shaw', 1000000.00, 12), -- Osservatore 1
('Jesse', 'Mermuys', 900000.00, 8), -- Osservatore 2
-- Warriors
('Jarron', 'Collins', 1100000.00, 9), -- Osservatore 1
('Chris', 'DeMarco', 950000.00, 7), -- Osservatore 2
-- Suns
('Randy', 'Ayers', 1000000.00, 10), -- Osservatore 1
('Mark', 'Bryant', 900000.00, 8), -- Osservatore 2
-- Clippers
('Brendan', 'O\'Connor', 1100000.00, 9), -- Osservatore 1
('Jeremy', 'Castleberry', 950000.00, 6), -- Osservatore 2
-- Kings
('Bobby', 'Jackson', 1000000.00, 12), -- Osservatore 1
('Doug', 'Christie', 1100000.00, 9), -- Osservatore 2
-- Senza squadra
('Sam', 'Cassell', 800000.00, 10), -- Osservatore senza squadra
('Patrick', 'Ewing', 850000.00, 15); -- Osservatore senza squadra

-- Inserimento dati nella tabella SQUADRA
INSERT INTO SQUADRA (nome, citta, idGM, idAllenatore, idOsservatore, n_giocatori, max_salariale) VALUES
('Los Angeles Lakers', 'Los Angeles', 1, 1, 1, 14, 120000000.00),
('Golden State Warriors', 'San Francisco', 2, 3, 3, 15, 130000000.00),
('Phoenix Suns', 'Phoenix', 3, 5, 5, 13, 110000000.00),
('Los Angeles Clippers', 'Los Angeles', 4, 7, 7, 12, 115000000.00),
('Sacramento Kings', 'Sacramento', 5, 9, 9, 14, 125000000.00);

-- Inserimento dati nella tabella STADIO
INSERT INTO STADIO (capacita, citta) VALUES
(19000, 'Los Angeles'), -- Lakers
(18000, 'San Francisco'), -- Warriors
(18000, 'Phoenix'), -- Suns
(19000, 'Los Angeles'), -- Clippers
(17608, 'Sacramento'); -- Kings

-- Inserimento dati nella tabella GIOCATORE
-- Giocatori con squadra (12-15 per squadra)
INSERT INTO GIOCATORE (nome, cognome, categoria, valutazione, anni_esperienza, freeagent) VALUES
-- Lakers (14 giocatori)
('LeBron', 'James', 'Superstar', 9.8, 18, FALSE),
('Anthony', 'Davis', 'All-Star', 9.0, 9, FALSE),
('Russell', 'Westbrook', 'All-Star', 8.5, 13, FALSE),
('Carmelo', 'Anthony', 'Role Player', 7.5, 19, FALSE),
('Dwight', 'Howard', 'Bench Player', 7.0, 18, FALSE),
('Austin', 'Reaves', 'Role Player', 7.2, 2, FALSE),
('Talen', 'Horton-Tucker', 'Bench Player', 6.8, 3, FALSE),
('Kendrick', 'Nunn', 'Bench Player', 6.5, 4, FALSE),
('Malik', 'Monk', 'Role Player', 7.3, 5, FALSE),
('Stanley', 'Johnson', 'Bench Player', 6.7, 6, FALSE),
('Wayne', 'Ellington', 'Bench Player', 6.5, 13, FALSE),
('Trevor', 'Ariza', 'Bench Player', 6.4, 18, FALSE),
('Kent', 'Bazemore', 'Bench Player', 6.6, 10, FALSE),
('DeAndre', 'Jordan', 'Bench Player', 6.3, 14, FALSE),
-- Warriors (15 giocatori)
('Stephen', 'Curry', 'Superstar', 9.7, 12, FALSE),
('Klay', 'Thompson', 'All-Star', 8.5, 10, FALSE),
('Draymond', 'Green', 'Role Player', 8.0, 10, FALSE),
('Andrew', 'Wiggins', 'All-Star', 8.2, 8, FALSE),
('James', 'Wiseman', 'Bench Player', 6.5, 2, FALSE),
('Jordan', 'Poole', 'Role Player', 7.8, 3, FALSE),
('Jonathan', 'Kuminga', 'Bench Player', 6.7, 1, FALSE),
('Moses', 'Moody', 'Bench Player', 6.6, 1, FALSE),
('Kevon', 'Looney', 'Role Player', 7.0, 7, FALSE),
('Otto', 'Porter Jr.', 'Bench Player', 6.8, 9, FALSE),
('Gary', 'Payton II', 'Bench Player', 6.9, 5, FALSE),
('Andre', 'Iguodala', 'Bench Player', 6.5, 18, FALSE),
('Nemanja', 'Bjelica', 'Bench Player', 6.7, 8, FALSE),
('Juan', 'Toscano-Anderson', 'Bench Player', 6.6, 3, FALSE),
('Damion', 'Lee', 'Bench Player', 6.5, 5, FALSE),
-- Suns (13 giocatori)
('Devin', 'Booker', 'Superstar', 9.5, 7, FALSE),
('Chris', 'Paul', 'All-Star', 8.8, 17, FALSE),
('Deandre', 'Ayton', 'All-Star', 8.3, 4, FALSE),
('Mikal', 'Bridges', 'Role Player', 7.8, 4, FALSE),
('Jae', 'Crowder', 'Role Player', 7.5, 10, FALSE),
('Cameron', 'Johnson', 'Role Player', 7.2, 3, FALSE),
('Landry', 'Shamet', 'Bench Player', 6.8, 4, FALSE),
('JaVale', 'McGee', 'Bench Player', 6.7, 14, FALSE),
('Cameron', 'Payne', 'Bench Player', 6.9, 6, FALSE),
('Abdel', 'Nader', 'Bench Player', 6.5, 5, FALSE),
('Elfrid', 'Payton', 'Bench Player', 6.4, 8, FALSE),
('Frank', 'Kaminsky', 'Bench Player', 6.6, 7, FALSE),
('Ish', 'Wainright', 'Bench Player', 6.3, 1, FALSE),
-- Clippers (12 giocatori)
('Paul', 'George', 'Superstar', 9.3, 12, FALSE),
('Kawhi', 'Leonard', 'Superstar', 9.4, 11, FALSE),
('Reggie', 'Jackson', 'Role Player', 7.5, 11, FALSE),
('Marcus', 'Morris Sr.', 'Role Player', 7.3, 10, FALSE),
('Ivica', 'Zubac', 'Role Player', 7.0, 6, FALSE),
('Luke', 'Kennard', 'Bench Player', 6.8, 5, FALSE),
('Nicolas', 'Batum', 'Bench Player', 6.9, 14, FALSE),
('Terance', 'Mann', 'Bench Player', 6.7, 4, FALSE),
('Amir', 'Coffey', 'Bench Player', 6.5, 3, FALSE),
('Isaiah', 'Hartenstein', 'Bench Player', 6.6, 4, FALSE),
('Brandon', 'Boston Jr.', 'Bench Player', 6.4, 1, FALSE),
('Jason', 'Preston', 'Bench Player', 6.3, 1, FALSE),
-- Kings (14 giocatori)
('De\'Aaron', 'Fox', 'All-Star', 8.7, 5, FALSE),
('Domantas', 'Sabonis', 'All-Star', 8.5, 6, FALSE),
('Harrison', 'Barnes', 'Role Player', 7.5, 10, FALSE),
('Davion', 'Mitchell', 'Role Player', 7.2, 1, FALSE),
('Richaun', 'Holmes', 'Role Player', 7.0, 7, FALSE),
('Buddy', 'Hield', 'Role Player', 7.3, 6, FALSE),
('Tyrese', 'Haliburton', 'Role Player', 7.8, 2, FALSE),
('Marvin', 'Bagley III', 'Bench Player', 6.7, 4, FALSE),
('Tristan', 'Thompson', 'Bench Player', 6.5, 11, FALSE),
('Alex', 'Len', 'Bench Player', 6.4, 9, FALSE),
('Maurice', 'Harkless', 'Bench Player', 6.6, 10, FALSE),
('Chimezie', 'Metu', 'Bench Player', 6.3, 4, FALSE),
('Terence', 'Davis', 'Bench Player', 6.5, 3, FALSE),
('Neemias', 'Queta', 'Bench Player', 6.2, 1, FALSE);

-- Free agents (20 giocatori)
INSERT INTO GIOCATORE (nome, cognome, categoria, valutazione, anni_esperienza, freeagent) VALUES
('Isaiah', 'Thomas', 'Role Player', 7.0, 10, TRUE),
('DeMarcus', 'Cousins', 'Role Player', 7.2, 11, TRUE),
('J.R.', 'Smith', 'Bench Player', 6.5, 16, TRUE),
('Jamal', 'Crawford', 'Bench Player', 6.8, 20, TRUE),
('Jeremy', 'Lin', 'Role Player', 6.9, 9, TRUE),
('Lance', 'Stephenson', 'Bench Player', 6.7, 10, TRUE),
('Michael', 'Beasley', 'Bench Player', 6.6, 12, TRUE),
('Joe', 'Johnson', 'Bench Player', 6.5, 18, TRUE),
('Nick', 'Young', 'Bench Player', 6.4, 11, TRUE),
('Lou', 'Williams', 'Role Player', 7.0, 17, TRUE),
('Trevor', 'Ariza', 'Bench Player', 6.5, 18, TRUE),
('Pau', 'Gasol', 'Bench Player', 6.3, 19, TRUE),
('Tony', 'Parker', 'Bench Player', 6.2, 18, TRUE),
('Manu', 'Ginobili', 'Bench Player', 6.1, 16, TRUE),
('Vince', 'Carter', 'Bench Player', 6.0, 22, TRUE),
('Dirk', 'Nowitzki', 'Bench Player', 6.4, 21, TRUE),
('Tim', 'Duncan', 'Bench Player', 6.3, 19, TRUE),
('Ray', 'Allen', 'Bench Player', 6.2, 18, TRUE),
('Paul', 'Pierce', 'Bench Player', 6.1, 19, TRUE),
('Kevin', 'Garnett', 'Bench Player', 6.0, 21, TRUE);

-- Inserimento dati nella tabella CONTRATTO
-- Contratti attivi e scaduti
INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio) VALUES
-- Lakers
(1, 1, '2020-12-03', 4, 40000000.00), -- LeBron James
(1, 2, '2020-12-03', 5, 35000000.00), -- Anthony Davis
(1, 3, '2021-08-06', 2, 44000000.00), -- Russell Westbrook
(1, 4, '2021-08-03', 1, 2600000.00), -- Carmelo Anthony
(1, 5, '2021-08-03', 1, 2600000.00), -- Dwight Howard
(1, 6, '2021-08-03', 2, 1400000.00), -- Austin Reaves
(1, 7, '2021-08-03', 3, 9500000.00), -- Talen Horton-Tucker
(1, 8, '2021-08-03', 2, 5000000.00), -- Kendrick Nunn
(1, 9, '2021-08-03', 1, 1800000.00), -- Malik Monk
(1, 10, '2021-08-03', 1, 2600000.00), -- Stanley Johnson
(1, 11, '2021-08-03', 1, 2600000.00), -- Wayne Ellington
(1, 12, '2021-08-03', 1, 2600000.00), -- Trevor Ariza
(1, 13, '2021-08-03', 1, 2600000.00), -- Kent Bazemore
(1, 14, '2021-08-03', 1, 2600000.00), -- DeAndre Jordan
-- Warriors
(2, 15, '2021-08-03', 4, 45000000.00), -- Stephen Curry
(2, 16, '2019-07-01', 5, 38000000.00), -- Klay Thompson
(2, 17, '2019-07-01', 4, 25000000.00), -- Draymond Green
(2, 18, '2020-11-22', 4, 30000000.00), -- Andrew Wiggins
(2, 19, '2020-11-18', 4, 9000000.00), -- James Wiseman
(2, 20, '2021-08-03', 4, 2000000.00), -- Jordan Poole
(2, 21, '2021-08-03', 4, 5000000.00), -- Jonathan Kuminga
(2, 22, '2021-08-03', 4, 3000000.00), -- Moses Moody
(2, 23, '2021-08-03', 3, 5000000.00), -- Kevon Looney
(2, 24, '2021-08-03', 2, 2400000.00), -- Otto Porter Jr.
(2, 25, '2021-08-03', 2, 2000000.00), -- Gary Payton II
(2, 26, '2021-08-03', 1, 2600000.00), -- Andre Iguodala
(2, 27, '2021-08-03', 1, 2600000.00), -- Nemanja Bjelica
(2, 28, '2021-08-03', 1, 2600000.00), -- Juan Toscano-Anderson
(2, 29, '2021-08-03', 1, 2600000.00), -- Damion Lee
-- Suns
(3, 30, '2021-08-03', 4, 35000000.00), -- Devin Booker
(3, 31, '2021-08-03', 4, 30000000.00), -- Chris Paul
(3, 32, '2021-08-03', 4, 25000000.00), -- Deandre Ayton
(3, 33, '2021-08-03', 4, 20000000.00), -- Mikal Bridges
(3, 34, '2021-08-03', 4, 15000000.00), -- Jae Crowder
(3, 35, '2021-08-03', 4, 10000000.00), -- Cameron Johnson
(3, 36, '2021-08-03', 3, 5000000.00), -- Landry Shamet
(3, 37, '2021-08-03', 2, 5000000.00), -- JaVale McGee
(3, 38, '2021-08-03', 2, 5000000.00), -- Cameron Payne
(3, 39, '2021-08-03', 1, 2600000.00), -- Abdel Nader
(3, 40, '2021-08-03', 1, 2600000.00), -- Elfrid Payton
(3, 41, '2021-08-03', 1, 2600000.00), -- Frank Kaminsky
(3, 42, '2021-08-03', 1, 2600000.00), -- Ish Wainright
-- Clippers
(4, 43, '2021-08-03', 4, 40000000.00), -- Paul George
(4, 44, '2021-08-03', 4, 35000000.00), -- Kawhi Leonard
(4, 45, '2021-08-03', 4, 15000000.00), -- Reggie Jackson
(4, 46, '2021-08-03', 4, 15000000.00), -- Marcus Morris Sr.
(4, 47, '2021-08-03', 4, 10000000.00), -- Ivica Zubac
(4, 48, '2021-08-03', 3, 5000000.00), -- Luke Kennard
(4, 49, '2021-08-03', 2, 5000000.00), -- Nicolas Batum
(4, 50, '2021-08-03', 2, 5000000.00), -- Terance Mann
(4, 51, '2021-08-03', 1, 2600000.00), -- Amir Coffey
(4, 52, '2021-08-03', 1, 2600000.00), -- Isaiah Hartenstein
(4, 53, '2021-08-03', 1, 2600000.00), -- Brandon Boston Jr.
(4, 54, '2021-08-03', 1, 2600000.00), -- Jason Preston
-- Kings
(5, 55, '2021-08-03', 4, 30000000.00), -- De'Aaron Fox
(5, 56, '2021-08-03', 4, 25000000.00), -- Domantas Sabonis
(5, 57, '2021-08-03', 4, 20000000.00), -- Harrison Barnes
(5, 58, '2021-08-03', 4, 10000000.00), -- Davion Mitchell
(5, 59, '2021-08-03', 4, 10000000.00), -- Richaun Holmes
(5, 60, '2021-08-03', 4, 20000000.00), -- Buddy Hield
(5, 61, '2021-08-03', 4, 10000000.00), -- Tyrese Haliburton
(5, 62, '2021-08-03', 3, 5000000.00), -- Marvin Bagley III
(5, 63, '2021-08-03', 2, 5000000.00), -- Tristan Thompson
(5, 64, '2021-08-03', 2, 5000000.00), -- Alex Len
(5, 65, '2021-08-03', 1, 2600000.00), -- Maurice Harkless
(5, 66, '2021-08-03', 1, 2600000.00), -- Chimezie Metu
(5, 67, '2021-08-03', 1, 2600000.00), -- Terence Davis
(5, 68, '2021-08-03', 1, 2600000.00); -- Neemias Queta

-- Inserimento dati nella tabella SCAMBIO
-- 15 scambi (4 rifiutati, 4 accettati, 5 in corso)
INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data) VALUES
-- Scambi accettati
(3, 18, 'Accettato', '2022-02-10'), -- Westbrook <-> Wiggins
(9, 24, 'Accettato', '2022-03-01'), -- Monk <-> Porter Jr.
(15, 30, 'Accettato', '2022-01-15'), -- Curry <-> Booker
(21, 35, 'Accettato', '2022-02-20'), -- Kuminga <-> Johnson
-- Scambi rifiutati
(4, 19, 'Rifiutato', '2022-01-10'), -- Anthony <-> Wiseman
(10, 25, 'Rifiutato', '2022-02-05'), -- Johnson <-> Payton II
(16, 31, 'Rifiutato', '2022-03-10'), -- Thompson <-> Paul
(22, 36, 'Rifiutato', '2022-01-20'), -- Moody <-> Shamet
-- Scambi in corso
(5, 20, 'In corso', '2022-03-15'), -- Howard <-> Poole
(11, 26, 'In corso', '2022-03-16'), -- Ellington <-> Iguodala
(17, 32, 'In corso', '2022-03-17'), -- Green <-> Ayton
(23, 37, 'In corso', '2022-03-18'), -- Looney <-> McGee
(29, 43, 'In corso', '2022-03-19'); -- Lee <-> George

-- Inserimento dati nella tabella ALLENAMENTO
-- 30 allenamenti (singoli e di gruppo)
INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus) VALUES
-- Lakers
(1, 'Gruppo', NULL, 120, '2023-03-15', 'Difesa'),
(1, 'Singolo', 1, 90, '2023-03-16', 'Tiro da 3 punti'), -- LeBron James
(1, 'Gruppo', NULL, 150, '2023-03-17', 'Attacco'),
(1, 'Singolo', 2, 90, '2023-03-18', 'Movimenti senza palla'), -- Anthony Davis
-- Warriors
(3, 'Gruppo', NULL, 120, '2023-03-15', 'Transizione'),
(3, 'Singolo', 15, 90, '2023-03-16', 'Tiro da 3 punti'), -- Stephen Curry
(3, 'Gruppo', NULL, 150, '2023-03-17', 'Difesa'),
(3, 'Singolo', 16, 90, '2023-03-18', 'Tiro libero'), -- Klay Thompson
-- Suns
(5, 'Gruppo', NULL, 120, '2023-03-15', 'Pick and Roll'),
(5, 'Singolo', 30, 90, '2023-03-16', 'Tiro da 3 punti'), -- Devin Booker
(5, 'Gruppo', NULL, 150, '2023-03-17', 'Attacco'),
(5, 'Singolo', 31, 90, '2023-03-18', 'Palleggio'), -- Chris Paul
-- Clippers
(7, 'Gruppo', NULL, 120, '2023-03-15', 'Difesa a zona'),
(7, 'Singolo', 43, 90, '2023-03-16', 'Tiro da 3 punti'), -- Paul George
(7, 'Gruppo', NULL, 150, '2023-03-17', 'Transizione'),
(7, 'Singolo', 44, 90, '2023-03-18', 'Movimenti senza palla'), -- Kawhi Leonard
-- Kings
(9, 'Gruppo', NULL, 120, '2023-03-15', 'Pick and Pop'),
(9, 'Singolo', 55, 90, '2023-03-16', 'Tiro da 3 punti'), -- De'Aaron Fox
(9, 'Gruppo', NULL, 150, '2023-03-17', 'Attacco'),
(9, 'Singolo', 56, 90, '2023-03-18', 'Palleggio'), -- Domantas Sabonis
-- Allenamenti senza squadra
(11, 'Gruppo', NULL, 120, '2023-03-15', 'Difesa'),
(11, 'Singolo', 69, 90, '2023-03-16', 'Tiro da 3 punti'), -- Isaiah Thomas (free agent)
(12, 'Gruppo', NULL, 150, '2023-03-17', 'Attacco'),
(12, 'Singolo', 70, 90, '2023-03-18', 'Movimenti senza palla'); -- DeMarcus Cousins (free agent)

-- Inserimento dati nella tabella ESERCIZIO
INSERT INTO ESERCIZIO (nome, fondamentale, intensita) VALUES
('Tiro da 3 punti', 'Tiro', 'Alta'),
('Tiro libero', 'Tiro', 'Media'),
('Tiro in palleggio', 'Tiro', 'Media'),
('Tiro dai blocchi', 'Tiro', 'Alta'),
('Tiro contrastato', 'Tiro', 'Alta'),
('Palleggio semplice', 'Palleggio', 'Bassa'),
('Palleggio difficile', 'Palleggio', 'Alta'),
('Difesa a uomo', 'Difesa', 'Alta'),
('Difesa a zona', 'Difesa', 'Media'),
('Pick and Roll', 'Attacco', 'Media'),
('Pick and Pop', 'Attacco', 'Media'),
('3v2 contropiede', 'Contropiede', 'Alta'),
('4v3 contropiede', 'Contropiede', 'Alta'),
('3v0 contropiede', 'Contropiede', 'Media'),
('Schemi attacco', 'Attacco', 'Alta'),
('Transizione sottomano da fermo', 'Transizione', 'Media'),
('Tiro dal post', 'Tiro', 'Media'),
('Sottomano in terzo tempo', 'Tiro', 'Media'),
('Partitella 5v5', 'Partita', 'Alta');

-- Inserimento dati nella tabella ESERCIZIO_IN_ALLENAMENTO
INSERT INTO ESERCIZIO_IN_ALLENAMENTO (idAllenamento, idEsercizio) VALUES
-- Allenamento 1: Difesa
(1, 8), -- Difesa a uomo
(1, 9), -- Difesa a zona
-- Allenamento 2: Tiro da 3 punti
(2, 1), -- Tiro da 3 punti
-- Allenamento 3: Attacco
(3, 10), -- Pick and Roll
(3, 11), -- Pick and Pop
-- Allenamento 4: Movimenti senza palla
(4, 17), -- Tiro dal post
(4, 18), -- Sottomano in terzo tempo
-- Allenamento 5: Transizione
(5, 12), -- 3v2 contropiede
(5, 13), -- 4v3 contropiede
-- Allenamento 6: Tiro da 3 punti
(6, 1), -- Tiro da 3 punti
-- Allenamento 7: Difesa
(7, 8), -- Difesa a uomo
(7, 9), -- Difesa a zona
-- Allenamento 8: Tiro libero
(8, 2), -- Tiro libero
-- Allenamento 9: Pick and Roll
(9, 10), -- Pick and Roll
-- Allenamento 10: Tiro da 3 punti
(10, 1), -- Tiro da 3 punti
-- Allenamento 11: Attacco
(11, 10), -- Pick and Roll
(11, 11), -- Pick and Pop
-- Allenamento 12: Palleggio
(12, 6), -- Palleggio semplice
(12, 7), -- Palleggio difficile
-- Allenamento 13: Difesa a zona
(13, 9), -- Difesa a zona
-- Allenamento 14: Tiro da 3 punti
(14, 1), -- Tiro da 3 punti
-- Allenamento 15: Transizione
(15, 12), -- 3v2 contropiede
(15, 13), -- 4v3 contropiede
-- Allenamento 16: Movimenti senza palla
(16, 17), -- Tiro dal post
(16, 18), -- Sottomano in terzo tempo
-- Allenamento 17: Pick and Pop
(17, 11), -- Pick and Pop
-- Allenamento 18: Tiro da 3 punti
(18, 1), -- Tiro da 3 punti
-- Allenamento 19: Attacco
(19, 10), -- Pick and Roll
(19, 11), -- Pick and Pop
-- Allenamento 20: Palleggio
(20, 6), -- Palleggio semplice
(20, 7); -- Palleggio difficile

-- Inserimento dati nella tabella GIOCATORI_OSSERVATI
INSERT INTO GIOCATORI_OSSERVATI (idGiocatore, idOsservatore) VALUES
-- Lakers
(1, 1), -- LeBron James osservato da Brian Shaw
(2, 2), -- Anthony Davis osservato da Jesse Mermuys
-- Warriors
(15, 3), -- Stephen Curry osservato da Jarron Collins
(16, 4), -- Klay Thompson osservato da Chris DeMarco
-- Suns
(30, 5), -- Devin Booker osservato da Randy Ayers
(31, 6), -- Chris Paul osservato da Mark Bryant
-- Clippers
(43, 7), -- Paul George osservato da Brendan O'Connor
(44, 8), -- Kawhi Leonard osservato da Jeremy Castleberry
-- Kings
(55, 9), -- De'Aaron Fox osservato da Bobby Jackson
(56, 10); -- Domantas Sabonis osservato da Doug Christie