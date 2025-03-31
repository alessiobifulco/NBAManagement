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
INSERT INTO SQUADRA (nome, citta, idGM, idAllenatore, idOsservatore, n_giocatori, max_salariale) VALUES
('Los Angeles Lakers', 'Los Angeles', 1, 1, 1, 14, 120000000.00), -- Lakers (Conference Ovest)
('Golden State Warriors', 'San Francisco', 2, 3, 3, 15, 130000000.00), -- Warriors (Conference Ovest)
('Phoenix Suns', 'Phoenix', 3, 5, 5, 13, 110000000.00), -- Suns (Conference Ovest)
('Los Angeles Clippers', 'Los Angeles', 4, 7, 7, 12, 115000000.00), -- Clippers (Conference Ovest)
('Sacramento Kings', 'Sacramento', 5, 9, 9, 14, 125000000.00); -- Kings (Conference Ovest)

-- Inserimento dati nella tabella STADIO
INSERT INTO STADIO (capacita, citta, idSquadra) VALUES
(19000, 'Los Angeles', 1), -- Lakers
(18000, 'San Francisco', 2), -- Warriors
(18000, 'Phoenix', 3), -- Suns
(19000, 'Los Angeles', 4), -- Clippers
(17608, 'Sacramento', 5); -- Kings

-- Lakers (14 giocatori)
INSERT INTO GIOCATORE (nome, cognome, position, categoria, valutazione, anni_esperienza, eta, freeagent) VALUES
('LeBron', 'James', 'SF', 'Superstar', 9.8, 18, 38, FALSE),
('Anthony', 'Davis', 'PF', 'AllStar', 9.0, 9, 30, FALSE),
('Russell', 'Westbrook', 'PG', 'AllStar', 8.5, 13, 35, FALSE),
('Carmelo', 'Anthony', 'SF', 'RolePlayer', 7.5, 19, 39, FALSE),
('Dwight', 'Howard', 'C', 'BenchPlayer', 7.0, 18, 38, FALSE),
('Austin', 'Reaves', 'SG', 'RolePlayer', 7.2, 2, 25, FALSE),
('Talen', 'Horton-Tucker', 'SG', 'BenchPlayer', 6.8, 3, 22, FALSE),
('Kendrick', 'Nunn', 'PG', 'BenchPlayer', 6.5, 4, 24, FALSE),
('Malik', 'Monk', 'SG', 'RolePlayer', 7.3, 5, 27, FALSE),
('Stanley', 'Johnson', 'SF', 'BenchPlayer', 6.7, 6, 28, FALSE),
('Wayne', 'Ellington', 'SG', 'BenchPlayer', 6.5, 13, 35, FALSE),
('Trevor', 'Ariza', 'SF', 'BenchPlayer', 6.4, 18, 38, FALSE),
('Kent', 'Bazemore', 'SG', 'BenchPlayer', 6.6, 10, 32, FALSE),
('DeAndre', 'Jordan', 'C', 'BenchPlayer', 6.3, 14, 36, FALSE),

-- Warriors (15 giocatori)
('Stephen', 'Curry', 'PG', 'Superstar', 9.7, 12, 35, FALSE),
('Klay', 'Thompson', 'SG', 'AllStar', 8.5, 10, 33, FALSE),
('Draymond', 'Green', 'PF', 'RolePlayer', 8.0, 10, 33, FALSE),
('Andrew', 'Wiggins', 'SF', 'AllStar', 8.2, 8, 31, FALSE),
('James', 'Wiseman', 'C', 'BenchPlayer', 6.5, 2, 21, FALSE),
('Jordan', 'Poole', 'SG', 'RolePlayer', 7.8, 3, 26, FALSE),
('Jonathan', 'Kuminga', 'SF', 'BenchPlayer', 6.7, 1, 20, FALSE),
('Moses', 'Moody', 'SG', 'BenchPlayer', 6.6, 1, 20, FALSE),
('Kevon', 'Looney', 'C', 'RolePlayer', 7.0, 7, 28, FALSE),
('Otto', 'Porter Jr.', 'SF', 'BenchPlayer', 6.8, 9, 34, FALSE),
('Gary', 'Payton II', 'SG', 'BenchPlayer', 6.9, 5, 31, FALSE),
('Andre', 'Iguodala', 'SF', 'BenchPlayer', 6.5, 18, 40, FALSE),
('Nemanja', 'Bjelica', 'PF', 'BenchPlayer', 6.7, 8, 35, FALSE),
('Juan', 'Toscano-Anderson', 'SF', 'BenchPlayer', 6.6, 3, 29, FALSE),
('Damion', 'Lee', 'SG', 'BenchPlayer', 6.5, 5, 31, FALSE),

-- Suns (13 giocatori)
('Devin', 'Booker', 'SG', 'Superstar', 9.5, 7, 26, FALSE),
('Chris', 'Paul', 'PG', 'AllStar', 8.8, 17, 38, FALSE),
('Deandre', 'Ayton', 'C', 'AllStar', 8.3, 4, 25, FALSE),
('Mikal', 'Bridges', 'SF', 'RolePlayer', 7.8, 4, 26, FALSE),
('Jae', 'Crowder', 'PF', 'RolePlayer', 7.5, 10, 33, FALSE),
('Cameron', 'Johnson', 'SF', 'RolePlayer', 7.2, 3, 26, FALSE),
('Landry', 'Shamet', 'SG', 'BenchPlayer', 6.8, 4, 26, FALSE),
('JaVale', 'McGee', 'C', 'BenchPlayer', 6.7, 14, 35, FALSE),
('Cameron', 'Payne', 'PG', 'BenchPlayer', 6.9, 6, 28, FALSE),
('Abdel', 'Nader', 'SF', 'BenchPlayer', 6.5, 5, 29, FALSE),
('Elfrid', 'Payton', 'PG', 'BenchPlayer', 6.4, 8, 32, FALSE),
('Frank', 'Kaminsky', 'C', 'BenchPlayer', 6.6, 7, 29, FALSE),
('Ish', 'Wainright', 'PF', 'BenchPlayer', 6.3, 1, 24, FALSE),

-- Clippers (12 giocatori)
('Paul', 'George', 'SF', 'Superstar', 9.3, 12, 33, FALSE),
('Kawhi', 'Leonard', 'SF', 'Superstar', 9.4, 11, 32, FALSE),
('Reggie', 'Jackson', 'PG', 'RolePlayer', 7.5, 11, 33, FALSE),
('Marcus', 'Morris Sr.', 'PF', 'RolePlayer', 7.3, 10, 33, FALSE),
('Ivica', 'Zubac', 'C', 'RolePlayer', 7.0, 6, 27, FALSE),
('Luke', 'Kennard', 'SG', 'BenchPlayer', 6.8, 5, 27, FALSE),
('Nicolas', 'Batum', 'SF', 'BenchPlayer', 6.9, 14, 35, FALSE),
('Terance', 'Mann', 'SG', 'BenchPlayer', 6.7, 4, 26, FALSE),
('Amir', 'Coffey', 'SF', 'BenchPlayer', 6.5, 3, 25, FALSE),
('Isaiah', 'Hartenstein', 'C', 'BenchPlayer', 6.6, 4, 26, FALSE),
('Brandon', 'Boston Jr.', 'SG', 'BenchPlayer', 6.4, 1, 22, FALSE),
('Jason', 'Preston', 'PG', 'BenchPlayer', 6.3, 1, 22, FALSE),

-- Kings (14 giocatori)
('De\'Aaron', 'Fox', 'PG', 'AllStar', 8.7, 5, 25, FALSE),
('Domantas', 'Sabonis', 'C', 'AllStar', 8.5, 6, 27, FALSE),
('Harrison', 'Barnes', 'SF', 'RolePlayer', 7.5, 10, 33, FALSE),
('Davion', 'Mitchell', 'PG', 'RolePlayer', 7.2, 1, 24, FALSE),
('Richaun', 'Holmes', 'C', 'RolePlayer', 7.0, 7, 29, FALSE),
('Keegan', 'Murray', 'SF', 'BenchPlayer', 6.8, 1, 22, FALSE),
('Malik', 'Monk', 'SG', 'RolePlayer', 7.3, 6, 25, FALSE),
('Terrence', 'Davis', 'SG', 'BenchPlayer', 6.5, 2, 23, FALSE),
('KZ', 'Okpala', 'SF', 'BenchPlayer', 6.7, 2, 24, FALSE),
('Trey', 'Lyles', 'PF', 'BenchPlayer', 6.5, 7, 28, FALSE),
('Matthew', 'Dellavedova', 'PG', 'BenchPlayer', 6.6, 9, 34, FALSE),
('Chimezie', 'Metu', 'PF', 'BenchPlayer', 6.7, 3, 26, FALSE),
('Neemias', 'Queta', 'C', 'BenchPlayer', 6.8, 1, 24, FALSE),
('Jaden', 'Ivey', 'PG', 'BenchPlayer', 6.5, 1, 21, FALSE);


-- Free agents (30 giocatori con età)
INSERT INTO GIOCATORE (nome, cognome, position, categoria, valutazione, anni_esperienza, freeagent, eta) VALUES
('Isaiah', 'Thomas', 'PG', 'RolePlayer', 7.0, 10, TRUE, 34),
('DeMarcus', 'Cousins', 'C', 'RolePlayer', 7.2, 11, TRUE, 33),
('J.R.', 'Smith', 'SG', 'BenchPlayer', 6.5, 16, TRUE, 39),
('Jamal', 'Crawford', 'SG', 'BenchPlayer', 6.8, 20, TRUE, 43),
('Jeremy', 'Lin', 'PG', 'RolePlayer', 6.9, 9, TRUE, 35),
('Lance', 'Stephenson', 'SG', 'BenchPlayer', 6.7, 10, TRUE, 34),
('Michael', 'Beasley', 'PF', 'BenchPlayer', 6.6, 12, TRUE, 35),
('Joe', 'Johnson', 'SF', 'BenchPlayer', 6.5, 18, TRUE, 42),
('Nick', 'Young', 'SG', 'BenchPlayer', 6.4, 11, TRUE, 39),
('Lou', 'Williams', 'SG', 'RolePlayer', 7.0, 17, TRUE, 38),
('Trevor', 'Ariza', 'SF', 'BenchPlayer', 6.5, 18, TRUE, 39),
('Pau', 'Gasol', 'C', 'BenchPlayer', 6.3, 19, TRUE, 44),
('Tony', 'Parker', 'PG', 'BenchPlayer', 6.2, 18, TRUE, 43),
('Manu', 'Ginobili', 'SG', 'BenchPlayer', 6.1, 16, TRUE, 47),
('Vince', 'Carter', 'SG', 'BenchPlayer', 6.0, 22, TRUE, 46),
('Dirk', 'Nowitzki', 'PF', 'BenchPlayer', 6.4, 21, TRUE, 45),
('Tim', 'Duncan', 'PF', 'BenchPlayer', 6.3, 19, TRUE, 49),
('Ray', 'Allen', 'SG', 'BenchPlayer', 6.2, 18, TRUE, 49),
('Paul', 'Pierce', 'SF', 'BenchPlayer', 6.1, 19, TRUE, 47),
('Kevin', 'Garnett', 'PF', 'BenchPlayer', 6.0, 21, TRUE, 48),
('James', 'Harden', 'SG', 'Superstar', 9.6, 13, TRUE, 34),
('Russell', 'Westbrook', 'PG', 'AllStar', 8.5, 13, TRUE, 35),
('Chris', 'Paul', 'PG', 'Superstar', 9.0, 17, TRUE, 38),
('Blake', 'Griffin', 'PF', 'AllStar', 8.3, 12, TRUE, 35),
('Carmelo', 'Anthony', 'SF', 'AllStar', 8.2, 19, TRUE, 40),
('Carmelo', 'Anthony', 'SF', 'AllStar', 8.1, 18, TRUE, 40),
('Dwight', 'Howard', 'C', 'AllStar', 8.0, 18, TRUE, 38),
('Al', 'Horford', 'PF', 'AllStar', 7.8, 16, TRUE, 37),
('Jamal', 'Murray', 'PG', 'AllStar', 8.4, 8, TRUE, 27),
('Gordon', 'Hayward', 'SF', 'AllStar', 8.0, 11, TRUE, 34);


-- Inserimento dati nella tabella CONTRATTO
-- Contratti attivi e scaduti
INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio, stato) VALUES
-- Lakers
(1, 1, '2020-12-03', 4, 40000000.00, TRUE), -- LeBron James
(1, 2, '2020-12-03', 5, 35000000.00, TRUE), -- Anthony Davis
(1, 3, '2021-08-06', 2, 44000000.00, TRUE), -- Russell Westbrook
(1, 4, '2021-08-03', 1, 2600000.00, TRUE), -- Carmelo Anthony (scaduto)
(1, 5, '2021-08-03', 1, 2600000.00, TRUE), -- Dwight Howard (scaduto)
(1, 6, '2021-08-03', 2, 1400000.00, TRUE), -- Austin Reaves
(1, 7, '2021-08-03', 3, 9500000.00, TRUE), -- Talen Horton-Tucker
(1, 8, '2021-08-03', 2, 5000000.00, TRUE), -- Kendrick Nunn
(1, 9, '2021-08-03', 1, 1800000.00, TRUE), -- Malik Monk (scaduto)
(1, 10, '2021-08-03', 1, 2600000.00, TRUE), -- Stanley Johnson (scaduto)
(1, 11, '2021-08-03', 1, 2600000.00, TRUE), -- Wayne Ellington (scaduto)
(1, 12, '2021-08-03', 1, 2600000.00, TRUE), -- Trevor Ariza (scaduto)
(1, 13, '2021-08-03', 1, 2600000.00, TRUE), -- Kent Bazemore (scaduto)
(1, 14, '2021-08-03', 1, 2600000.00, TRUE), -- DeAndre Jordan (scaduto)
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
(2, 26, '2021-08-03', 1, 2600000.00, TRUE), -- Andre Iguodala (scaduto)
(2, 27, '2021-08-03', 1, 2600000.00, TRUE), -- Nemanja Bjelica (scaduto)
(2, 28, '2021-08-03', 1, 2600000.00, TRUE), -- Juan Toscano-Anderson (scaduto)
(2, 29, '2021-08-03', 1, 2600000.00, TRUE), -- Damion Lee (scaduto)
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
(3, 39, '2021-08-03', 1, 2600000.00, TRUE), -- Abdel Nader (scaduto)
(3, 40, '2021-08-03', 1, 2600000.00, TRUE), -- Elfrid Payton (scaduto)
(3, 41, '2021-08-03', 1, 2600000.00, TRUE), -- Frank Kaminsky (scaduto)
(3, 42, '2021-08-03', 1, 2600000.00, TRUE), -- Ish Wainright (scaduto)
-- Clippers
(4, 43, '2021-08-03', 4, 40000000.00, TRUE), -- Paul George
(4, 44, '2021-08-03', 4, 35000000.00, TRUE), -- Kawhi Leonard
(4, 45, '2021-08-03', 4, 15000000.00, TRUE), -- Reggie Jackson
(4, 46, '2021-08-03', 4, 15000000.00, TRUE), -- Marcus Morris Sr.
(4, 47, '2021-08-03', 4, 10000000.00, TRUE), -- Ivica Zubac
(4, 48, '2021-08-03', 3, 5000000.00, TRUE), -- Luke Kennard
(4, 49, '2021-08-03', 2, 5000000.00, TRUE), -- Nicolas Batum
(4, 50, '2021-08-03', 2, 5000000.00, TRUE), -- Terance Mann
(4, 51, '2021-08-03', 1, 2600000.00, TRUE), -- Amir Coffey (scaduto)
(4, 52, '2021-08-03', 1, 2600000.00, TRUE), -- Isaiah Hartenstein (scaduto)
(4, 53, '2021-08-03', 1, 2600000.00, TRUE), -- Brandon Boston Jr. (scaduto)
(4, 54, '2021-08-03', 1, 2600000.00, TRUE), -- Jason Preston (scaduto)
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
(5, 65, '2021-08-03', 1, 2600000.00, TRUE), -- Maurice Harkless (scaduto)
(5, 66, '2021-08-03', 1, 2600000.00, TRUE), -- Chimezie Metu (scaduto)
(5, 67, '2021-08-03', 1, 2600000.00, TRUE), -- Terence Davis (scaduto)
(5, 68, '2021-08-03', 1, 2600000.00, TRUE); -- Neemias Queta (scaduto)

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

INSERT INTO PARTITA (idSquadra1, idSquadra2, idStadio, risultato, data) VALUES
(1, 2, 1, '102-98', '2023-03-01'),
(5, 4, 5, '110-115', '2023-03-02'),
(1, 3, 1, '99-101', '2023-03-03'),
(5, 2, 5, '120-105', '2023-03-04'),
(2, 5, 2, '95-89', '2023-03-05'),
(3, 1, 3, '108-112', '2023-03-06'),
(4, 1, 4, '101-99', '2023-03-07'),
(5, 1, 5, '90-85', '2023-03-08'),
(4, 3, 4, '115-118', '2023-03-09'),
(3, 1, 3, '105-102', '2023-03-10');
