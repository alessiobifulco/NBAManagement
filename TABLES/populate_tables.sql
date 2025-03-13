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
INSERT INTO ALLENATORE (nome, cognome, stipendio, anni_esperienza, free) VALUES
-- Lakers
('Darvin', 'Ham', 5000000.00, 5, FALSE), -- Allenatore principale
('Phil', 'Handy', 1500000.00, 10, FALSE), -- Vice allenatore
-- Warriors
('Steve', 'Kerr', 8000000.00, 10, FALSE), -- Allenatore principale
('Ron', 'Adams', 1200000.00, 15, FALSE), -- Vice allenatore
-- Suns
('Monty', 'Williams', 6000000.00, 7, FALSE), -- Allenatore principale
('Kevin', 'Young', 1000000.00, 8, FALSE), -- Vice allenatore
-- Clippers
('Tyronn', 'Lue', 7000000.00, 8, FALSE), -- Allenatore principale
('Jeremy', 'Castleberry', 900000.00, 6, FALSE), -- Vice allenatore
-- Kings
('Mike', 'Brown', 5000000.00, 12, FALSE), -- Allenatore principale
('Doug', 'Christie', 1100000.00, 9, FALSE), -- Vice allenatore
-- Senza squadra
('Mark', 'Jackson', 3000000.00, 10, TRUE), -- Allenatore senza squadra
('Jeff', 'Van Gundy', 3500000.00, 15, TRUE); -- Allenatore senza squadra

-- Inserimento dati nella tabella OSSERVATORE
INSERT INTO OSSERVATORE (nome, cognome, stipendio, anni_esperienza, free) VALUES
-- Lakers
('Brian', 'Shaw', 1000000.00, 12, FALSE), -- Osservatore 1
('Jesse', 'Mermuys', 900000.00, 8, FALSE), -- Osservatore 2
-- Warriors
('Jarron', 'Collins', 1100000.00, 9, FALSE), -- Osservatore 1
('Chris', 'DeMarco', 950000.00, 7, FALSE), -- Osservatore 2
-- Suns
('Randy', 'Ayers', 1000000.00, 10, FALSE), -- Osservatore 1
('Mark', 'Bryant', 900000.00, 8, FALSE), -- Osservatore 2
-- Clippers
('Brendan', 'O\'Connor', 1100000.00, 9, FALSE), -- Osservatore 1
('Jeremy', 'Castleberry', 950000.00, 6, FALSE), -- Osservatore 2
-- Kings
('Bobby', 'Jackson', 1000000.00, 12, FALSE), -- Osservatore 1
('Doug', 'Christie', 1100000.00, 9, FALSE), -- Osservatore 2
-- Senza squadra
('Sam', 'Cassell', 800000.00, 10, TRUE), -- Osservatore senza squadra
('Patrick', 'Ewing', 850000.00, 15, TRUE); -- Osservatore senza squadra

-- Inserimento dati nella tabella SQUADRA
INSERT INTO SQUADRA (nome, citta, idGM, idAllenatore, idOsservatore, n_giocatori, max_salariale, conference) VALUES
('Los Angeles Lakers', 'Los Angeles', 1, 1, 1, 14, 120000000.00, TRUE), -- Lakers (Conference Ovest)
('Golden State Warriors', 'San Francisco', 2, 3, 3, 15, 130000000.00, TRUE), -- Warriors (Conference Ovest)
('Phoenix Suns', 'Phoenix', 3, 5, 5, 13, 110000000.00, TRUE), -- Suns (Conference Ovest)
('Los Angeles Clippers', 'Los Angeles', 4, 7, 7, 12, 115000000.00, TRUE), -- Clippers (Conference Ovest)
('Sacramento Kings', 'Sacramento', 5, 9, 9, 14, 125000000.00, TRUE); -- Kings (Conference Ovest)

-- Inserimento dati nella tabella STADIO
INSERT INTO STADIO (capacita, citta, idSquadra) VALUES
(19000, 'Los Angeles', 1), -- Lakers
(18000, 'San Francisco', 2), -- Warriors
(18000, 'Phoenix', 3), -- Suns
(19000, 'Los Angeles', 4), -- Clippers
(17608, 'Sacramento', 5); -- Kings

-- Inserimento dati nella tabella GIOCATORE
-- Giocatori con squadra (12-15 per squadra)
INSERT INTO GIOCATORE (nome, cognome, position, categoria, valutazione, anni_esperienza, freeagent) VALUES
-- Lakers (14 giocatori)
('LeBron', 'James', 'SF', 'Superstar', 9.8, 18, FALSE),
('Anthony', 'Davis', 'PF', 'All-Star', 9.0, 9, FALSE),
('Russell', 'Westbrook', 'PG', 'All-Star', 8.5, 13, FALSE),
('Carmelo', 'Anthony', 'SF', 'Role Player', 7.5, 19, FALSE),
('Dwight', 'Howard', 'C', 'Bench Player', 7.0, 18, FALSE),
('Austin', 'Reaves', 'SG', 'Role Player', 7.2, 2, FALSE),
('Talen', 'Horton-Tucker', 'SG', 'Bench Player', 6.8, 3, FALSE),
('Kendrick', 'Nunn', 'PG', 'Bench Player', 6.5, 4, FALSE),
('Malik', 'Monk', 'SG', 'Role Player', 7.3, 5, FALSE),
('Stanley', 'Johnson', 'SF', 'Bench Player', 6.7, 6, FALSE),
('Wayne', 'Ellington', 'SG', 'Bench Player', 6.5, 13, FALSE),
('Trevor', 'Ariza', 'SF', 'Bench Player', 6.4, 18, FALSE),
('Kent', 'Bazemore', 'SG', 'Bench Player', 6.6, 10, FALSE),
('DeAndre', 'Jordan', 'C', 'Bench Player', 6.3, 14, FALSE),
-- Warriors (15 giocatori)
('Stephen', 'Curry', 'PG', 'Superstar', 9.7, 12, FALSE),
('Klay', 'Thompson', 'SG', 'All-Star', 8.5, 10, FALSE),
('Draymond', 'Green', 'PF', 'Role Player', 8.0, 10, FALSE),
('Andrew', 'Wiggins', 'SF', 'All-Star', 8.2, 8, FALSE),
('James', 'Wiseman', 'C', 'Bench Player', 6.5, 2, FALSE),
('Jordan', 'Poole', 'SG', 'Role Player', 7.8, 3, FALSE),
('Jonathan', 'Kuminga', 'SF', 'Bench Player', 6.7, 1, FALSE),
('Moses', 'Moody', 'SG', 'Bench Player', 6.6, 1, FALSE),
('Kevon', 'Looney', 'C', 'Role Player', 7.0, 7, FALSE),
('Otto', 'Porter Jr.', 'SF', 'Bench Player', 6.8, 9, FALSE),
('Gary', 'Payton II', 'SG', 'Bench Player', 6.9, 5, FALSE),
('Andre', 'Iguodala', 'SF', 'Bench Player', 6.5, 18, FALSE),
('Nemanja', 'Bjelica', 'PF', 'Bench Player', 6.7, 8, FALSE),
('Juan', 'Toscano-Anderson', 'SF', 'Bench Player', 6.6, 3, FALSE),
('Damion', 'Lee', 'SG', 'Bench Player', 6.5, 5, FALSE),
-- Suns (13 giocatori)
('Devin', 'Booker', 'SG', 'Superstar', 9.5, 7, FALSE),
('Chris', 'Paul', 'PG', 'All-Star', 8.8, 17, FALSE),
('Deandre', 'Ayton', 'C', 'All-Star', 8.3, 4, FALSE),
('Mikal', 'Bridges', 'SF', 'Role Player', 7.8, 4, FALSE),
('Jae', 'Crowder', 'PF', 'Role Player', 7.5, 10, FALSE),
('Cameron', 'Johnson', 'SF', 'Role Player', 7.2, 3, FALSE),
('Landry', 'Shamet', 'SG', 'Bench Player', 6.8, 4, FALSE),
('JaVale', 'McGee', 'C', 'Bench Player', 6.7, 14, FALSE),
('Cameron', 'Payne', 'PG', 'Bench Player', 6.9, 6, FALSE),
('Abdel', 'Nader', 'SF', 'Bench Player', 6.5, 5, FALSE),
('Elfrid', 'Payton', 'PG', 'Bench Player', 6.4, 8, FALSE),
('Frank', 'Kaminsky', 'C', 'Bench Player', 6.6, 7, FALSE),
('Ish', 'Wainright', 'PF', 'Bench Player', 6.3, 1, FALSE),
-- Clippers (12 giocatori)
('Paul', 'George', 'SF', 'Superstar', 9.3, 12, FALSE),
('Kawhi', 'Leonard', 'SF', 'Superstar', 9.4, 11, FALSE),
('Reggie', 'Jackson', 'PG', 'Role Player', 7.5, 11, FALSE),
('Marcus', 'Morris Sr.', 'PF', 'Role Player', 7.3, 10, FALSE),
('Ivica', 'Zubac', 'C', 'Role Player', 7.0, 6, FALSE),
('Luke', 'Kennard', 'SG', 'Bench Player', 6.8, 5, FALSE),
('Nicolas', 'Batum', 'SF', 'Bench Player', 6.9, 14, FALSE),
('Terance', 'Mann', 'SG', 'Bench Player', 6.7, 4, FALSE),
('Amir', 'Coffey', 'SF', 'Bench Player', 6.5, 3, FALSE),
('Isaiah', 'Hartenstein', 'C', 'Bench Player', 6.6, 4, FALSE),
('Brandon', 'Boston Jr.', 'SG', 'Bench Player', 6.4, 1, FALSE),
('Jason', 'Preston', 'PG', 'Bench Player', 6.3, 1, FALSE),
-- Kings (14 giocatori)
('De\'Aaron', 'Fox', 'PG', 'All-Star', 8.7, 5, FALSE),
('Domantas', 'Sabonis', 'C', 'All-Star', 8.5, 6, FALSE),
('Harrison', 'Barnes', 'SF', 'Role Player', 7.5, 10, FALSE),
('Davion', 'Mitchell', 'PG', 'Role Player', 7.2, 1, FALSE),
('Richaun', 'Holmes', 'C', 'Role Player', 7.0, 7, FALSE),
('Buddy', 'Hield', 'SG', 'Role Player', 7.3, 6, FALSE),
('Tyrese', 'Haliburton', 'PG', 'Role Player', 7.8, 2, FALSE),
('Marvin', 'Bagley III', 'PF', 'Bench Player', 6.7, 4, FALSE),
('Tristan', 'Thompson', 'C', 'Bench Player', 6.5, 11, FALSE),
('Alex', 'Len', 'C', 'Bench Player', 6.4, 9, FALSE),
('Maurice', 'Harkless', 'SF', 'Bench Player', 6.6, 10, FALSE),
('Chimezie', 'Metu', 'PF', 'Bench Player', 6.3, 4, FALSE),
('Terence', 'Davis', 'SG', 'Bench Player', 6.5, 3, FALSE),
('Neemias', 'Queta', 'C', 'Bench Player', 6.2, 1, FALSE);

-- Free agents (20 giocatori)
INSERT INTO GIOCATORE (nome, cognome, position, categoria, valutazione, anni_esperienza, freeagent) VALUES
('Isaiah', 'Thomas', 'PG', 'Role Player', 7.0, 10, TRUE),
('DeMarcus', 'Cousins', 'C', 'Role Player', 7.2, 11, TRUE),
('J.R.', 'Smith', 'SG', 'Bench Player', 6.5, 16, TRUE),
('Jamal', 'Crawford', 'SG', 'Bench Player', 6.8, 20, TRUE),
('Jeremy', 'Lin', 'PG', 'Role Player', 6.9, 9, TRUE),
('Lance', 'Stephenson', 'SG', 'Bench Player', 6.7, 10, TRUE),
('Michael', 'Beasley', 'PF', 'Bench Player', 6.6, 12, TRUE),
('Joe', 'Johnson', 'SF', 'Bench Player', 6.5, 18, TRUE),
('Nick', 'Young', 'SG', 'Bench Player', 6.4, 11, TRUE),
('Lou', 'Williams', 'SG', 'Role Player', 7.0, 17, TRUE),
('Trevor', 'Ariza', 'SF', 'Bench Player', 6.5, 18, TRUE),
('Pau', 'Gasol', 'C', 'Bench Player', 6.3, 19, TRUE),
('Tony', 'Parker', 'PG', 'Bench Player', 6.2, 18, TRUE),
('Manu', 'Ginobili', 'SG', 'Bench Player', 6.1, 16, TRUE),
('Vince', 'Carter', 'SG', 'Bench Player', 6.0, 22, TRUE),
('Dirk', 'Nowitzki', 'PF', 'Bench Player', 6.4, 21, TRUE),
('Tim', 'Duncan', 'PF', 'Bench Player', 6.3, 19, TRUE),
('Ray', 'Allen', 'SG', 'Bench Player', 6.2, 18, TRUE),
('Paul', 'Pierce', 'SF', 'Bench Player', 6.1, 19, TRUE),
('Kevin', 'Garnett', 'PF', 'Bench Player', 6.0, 21, TRUE);

-- Inserimento dati nella tabella CONTRATTO
-- Contratti attivi e scaduti
INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio, stato) VALUES
-- Lakers
(1, 1, '2020-12-03', 4, 40000000.00, TRUE), -- LeBron James
(1, 2, '2020-12-03', 5, 35000000.00, TRUE), -- Anthony Davis
(1, 3, '2021-08-06', 2, 44000000.00, TRUE), -- Russell Westbrook
(1, 4, '2021-08-03', 1, 2600000.00, FALSE), -- Carmelo Anthony (scaduto)
(1, 5, '2021-08-03', 1, 2600000.00, FALSE), -- Dwight Howard (scaduto)
(1, 6, '2021-08-03', 2, 1400000.00, TRUE), -- Austin Reaves
(1, 7, '2021-08-03', 3, 9500000.00, TRUE), -- Talen Horton-Tucker
(1, 8, '2021-08-03', 2, 5000000.00, TRUE), -- Kendrick Nunn
(1, 9, '2021-08-03', 1, 1800000.00, FALSE), -- Malik Monk (scaduto)
(1, 10, '2021-08-03', 1, 2600000.00, FALSE), -- Stanley Johnson (scaduto)
(1, 11, '2021-08-03', 1, 2600000.00, FALSE), -- Wayne Ellington (scaduto)
(1, 12, '2021-08-03', 1, 2600000.00, FALSE), -- Trevor Ariza (scaduto)
(1, 13, '2021-08-03', 1, 2600000.00, FALSE), -- Kent Bazemore (scaduto)
(1, 14, '2021-08-03', 1, 2600000.00, FALSE), -- DeAndre Jordan (scaduto)
-- Warriors
(2, 15, '2021-08-03', 4, 45000000.00, TRUE), -- Stephen Curry
(2, 16, '2019-07-01', 5, 38000000.00, TRUE), -- Klay Thompson
(2, 17, '2019-07-01', 4, 25000000.00, TRUE), -- Draymond Green
(2, 18, '2020-11-22', 4, 30000000.00, TRUE), -- Andrew Wiggins
(2, 19, '2020-11-18', 4, 9000000.00, TRUE), -- James Wiseman
(2, 20, '2021-08-03', 4, 2000000.00, TRUE), -- Jordan Poole
(2, 21, '2021-08-03', 4, 5000000.00, TRUE), -- Jonathan Kuminga
(2, 22, '2021-08-03', 4, 3000000.00, TRUE), -- Moses Moody
(2, 23, '2021-08-03', 3, 5000000.00, TRUE), -- Kevon Looney
(2, 24, '2021-08-03', 2, 2400000.00, TRUE), -- Otto Porter Jr.
(2, 25, '2021-08-03', 2, 2000000.00, TRUE), -- Gary Payton II
(2, 26, '2021-08-03', 1, 2600000.00, FALSE), -- Andre Iguodala (scaduto)
(2, 27, '2021-08-03', 1, 2600000.00, FALSE), -- Nemanja Bjelica (scaduto)
(2, 28, '2021-08-03', 1, 2600000.00, FALSE), -- Juan Toscano-Anderson (scaduto)
(2, 29, '2021-08-03', 1, 2600000.00, FALSE), -- Damion Lee (scaduto)
-- Suns
(3, 30, '2021-08-03', 4, 35000000.00, TRUE), -- Devin Booker
(3, 31, '2021-08-03', 4, 30000000.00, TRUE), -- Chris Paul
(3, 32, '2021-08-03', 4, 25000000.00, TRUE), -- Deandre Ayton
(3, 33, '2021-08-03', 4, 20000000.00, TRUE), -- Mikal Bridges
(3, 34, '2021-08-03', 4, 15000000.00, TRUE), -- Jae Crowder
(3, 35, '2021-08-03', 4, 10000000.00, TRUE), -- Cameron Johnson
(3, 36, '2021-08-03', 3, 5000000.00, TRUE), -- Landry Shamet
(3, 37, '2021-08-03', 2, 5000000.00, TRUE), -- JaVale McGee
(3, 38, '2021-08-03', 2, 5000000.00, TRUE), -- Cameron Payne
(3, 39, '2021-08-03', 1, 2600000.00, FALSE), -- Abdel Nader (scaduto)
(3, 40, '2021-08-03', 1, 2600000.00, FALSE), -- Elfrid Payton (scaduto)
(3, 41, '2021-08-03', 1, 2600000.00, FALSE), -- Frank Kaminsky (scaduto)
(3, 42, '2021-08-03', 1, 2600000.00, FALSE), -- Ish Wainright (scaduto)
-- Clippers
(4, 43, '2021-08-03', 4, 40000000.00, TRUE), -- Paul George
(4, 44, '2021-08-03', 4, 35000000.00, TRUE), -- Kawhi Leonard
(4, 45, '2021-08-03', 4, 15000000.00, TRUE), -- Reggie Jackson
(4, 46, '2021-08-03', 4, 15000000.00, TRUE), -- Marcus Morris Sr.
(4, 47, '2021-08-03', 4, 10000000.00, TRUE), -- Ivica Zubac
(4, 48, '2021-08-03', 3, 5000000.00, TRUE), -- Luke Kennard
(4, 49, '2021-08-03', 2, 5000000.00, TRUE), -- Nicolas Batum
(4, 50, '2021-08-03', 2, 5000000.00, TRUE), -- Terance Mann
(4, 51, '2021-08-03', 1, 2600000.00, FALSE), -- Amir Coffey (scaduto)
(4, 52, '2021-08-03', 1, 2600000.00, FALSE), -- Isaiah Hartenstein (scaduto)
(4, 53, '2021-08-03', 1, 2600000.00, FALSE), -- Brandon Boston Jr. (scaduto)
(4, 54, '2021-08-03', 1, 2600000.00, FALSE), -- Jason Preston (scaduto)
-- Kings
(5, 55, '2021-08-03', 4, 30000000.00, TRUE), -- De'Aaron Fox
(5, 56, '2021-08-03', 4, 25000000.00, TRUE), -- Domantas Sabonis
(5, 57, '2021-08-03', 4, 20000000.00, TRUE), -- Harrison Barnes
(5, 58, '2021-08-03', 4, 10000000.00, TRUE), -- Davion Mitchell
(5, 59, '2021-08-03', 4, 10000000.00, TRUE), -- Richaun Holmes
(5, 60, '2021-08-03', 4, 20000000.00, TRUE), -- Buddy Hield
(5, 61, '2021-08-03', 4, 10000000.00, TRUE), -- Tyrese Haliburton
(5, 62, '2021-08-03', 3, 5000000.00, TRUE), -- Marvin Bagley III
(5, 63, '2021-08-03', 2, 5000000.00, TRUE), -- Tristan Thompson
(5, 64, '2021-08-03', 2, 5000000.00, TRUE), -- Alex Len
(5, 65, '2021-08-03', 1, 2600000.00, FALSE), -- Maurice Harkless (scaduto)
(5, 66, '2021-08-03', 1, 2600000.00, FALSE), -- Chimezie Metu (scaduto)
(5, 67, '2021-08-03', 1, 2600000.00, FALSE), -- Terence Davis (scaduto)
(5, 68, '2021-08-03', 1, 2600000.00, FALSE); -- Neemias Queta (scaduto)

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
INSERT INTO ESERCIZIO_IN_ALLENAMENTO (idAllenamento, idEsercizio, serie) VALUES
-- Allenamento 1: Difesa
(1, 8, 3), -- Difesa a uomo (3 serie)
(1, 9, 2), -- Difesa a zona (2 serie)
-- Allenamento 2: Tiro da 3 punti
(2, 1, 5), -- Tiro da 3 punti (5 serie)
-- Allenamento 3: Attacco
(3, 10, 4), -- Pick and Roll (4 serie)
(3, 11, 3), -- Pick and Pop (3 serie)
-- Allenamento 4: Movimenti senza palla
(4, 17, 3), -- Tiro dal post (3 serie)
(4, 18, 2), -- Sottomano in terzo tempo (2 serie)
-- Allenamento 5: Transizione
(5, 12, 4), -- 3v2 contropiede (4 serie)
(5, 13, 3), -- 4v3 contropiede (3 serie)
-- Allenamento 6: Tiro da 3 punti
(6, 1, 5), -- Tiro da 3 punti (5 serie)
-- Allenamento 7: Difesa
(7, 8, 3), -- Difesa a uomo (3 serie)
(7, 9, 2), -- Difesa a zona (2 serie)
-- Allenamento 8: Tiro libero
(8, 2, 5), -- Tiro libero (5 serie)
-- Allenamento 9: Pick and Roll
(9, 10, 4), -- Pick and Roll (4 serie)
-- Allenamento 10: Tiro da 3 punti
(10, 1, 5), -- Tiro da 3 punti (5 serie)
-- Allenamento 11: Attacco
(11, 10, 4), -- Pick and Roll (4 serie)
(11, 11, 3), -- Pick and Pop (3 serie)
-- Allenamento 12: Palleggio
(12, 6, 3), -- Palleggio semplice (3 serie)
(12, 7, 2), -- Palleggio difficile (2 serie)
-- Allenamento 13: Difesa a zona
(13, 9, 3), -- Difesa a zona (3 serie)
-- Allenamento 14: Tiro da 3 punti
(14, 1, 5), -- Tiro da 3 punti (5 serie)
-- Allenamento 15: Transizione
(15, 12, 4), -- 3v2 contropiede (4 serie)
(15, 13, 3), -- 4v3 contropiede (3 serie)
-- Allenamento 16: Movimenti senza palla
(16, 17, 3), -- Tiro dal post (3 serie)
(16, 18, 2), -- Sottomano in terzo tempo (2 serie)
-- Allenamento 17: Pick and Pop
(17, 11, 3), -- Pick and Pop (3 serie)
-- Allenamento 18: Tiro da 3 punti
(18, 1, 5), -- Tiro da 3 punti (5 serie)
-- Allenamento 19: Attacco
(19, 10, 4), -- Pick and Roll (4 serie)
(19, 11, 3), -- Pick and Pop (3 serie)
-- Allenamento 20: Palleggio
(20, 6, 3), -- Palleggio semplice (3 serie)
(20, 7, 2); -- Palleggio difficile (2 serie)

-- Inserimento dati nella tabella GIOCATORI_OSSERVATI
INSERT INTO GIOCATORI_OSSERVATI (idGiocatore, idOsservatore, report) VALUES
-- Lakers
(1, 1, 'Ottime capacità di tiro da 3 punti. Deve migliorare la difesa a uomo.'),
(2, 2, 'Buona visione di gioco, ma deve lavorare sul rimbalzo.'),
-- Warriors
(15, 3, 'Ottimo leader in campo, ma deve migliorare la difesa.'),
(16, 4, 'Tiro da 3 punti eccellente, ma deve migliorare il palleggio.'),
-- Suns
(30, 5, 'Ottimo tiro da 3 punti, ma deve migliorare la difesa.'),
(31, 6, 'Visione di gioco eccellente, ma deve migliorare il tiro libero.'),
-- Clippers
(43, 7, 'Ottimo tiro da 3 punti, ma deve migliorare la difesa.'),
(44, 8, 'Ottimo tiro da 3 punti, ma deve migliorare la difesa.'),
-- Kings
(55, 9, 'Ottimo tiro da 3 punti, ma deve migliorare la difesa.'),
(56, 10, 'Ottimo tiro da 3 punti, ma deve migliorare la difesa.');