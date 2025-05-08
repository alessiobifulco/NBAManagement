USE NBA_Management;

-- Inserimento dati nella tabella GM
INSERT INTO GM (mail, password, nome, cognome) VALUES
('rob.pelinka@lakers.com', 'lakers', 'Rob',' Pelinka'), -- Lakers
('bob.myers@warriors.com', 'warriors', 'Bob',' Myers'), -- Warriors
('pat.riley@miami.com', 'miami', 'Pat',' Riley' ), -- Miami
('dallasgm@mavs.com', 'password123', 'Mark', 'Cuban'), -- Dallas

('john.doe@nba.com', 'password6', 'John',' Doe'), -- Senza squadra
('jane.smith@nba.com', 'password7', 'Jane',' Smith'), -- Senza squadra
('mike.johnson@nba.com', 'password8', 'Mike ','Johnson'), -- Senza squadra
('chris.brown@nba.com', 'password9', 'Chris',' Brown'), -- Senza squadra
('anna.white@nba.com', 'password10', 'Anna ','White'); -- Senza squadra

-- Inserimento dati nella tabella ALLENATORE
INSERT INTO ALLENATORE (nome, cognome, stipendio, anni_esperienza, free) VALUES
-- Lakers
('Darvin', 'Ham', 5000000.00, 5, FALSE), -- Allenatore 
-- Warriors
('Steve', 'Kerr', 8000000.00, 10, FALSE), -- Allenatore 
-- Miami
('Erik', 'Spoelstra', '8000000', 16, FALSE), -- Allenatore
-- Dallas
('Jason', 'Kidd', 8000000.00, 10, FALSE), -- Allenatore

-- Senza squadra
('Mark', 'Jackson', 3000000.00, 10, TRUE), -- Allenatore senza squadra
('Jeff', 'Van Gundy', 3500000.00, 15, TRUE); -- Allenatore senza squadra

-- Inserimento dati nella tabella OSSERVATORE
INSERT INTO OSSERVATORE (nome, cognome, stipendio, anni_esperienza, free) VALUES
-- Lakers
('Brian', 'Shaw', 1000000.00, 12, FALSE), -- Osservatore 
-- Warriors
('Jarron', 'Collins', 1100000.00, 9, FALSE), -- Osservatore 
-- Miami
('Adam', 'Simon', '500000', 11, FALSE), -- Osservatore 
-- Dallas
 ('John', 'Smith', 500000.00, 5, FALSE), -- Osservatore 

-- Senza squadra
('Sam', 'Cassell', 800000.00, 10, TRUE), -- Osservatore senza squadra
('Patrick', 'Ewing', 850000.00, 15, TRUE); -- Osservatore senza squadra

-- Inserimento dati nella tabella SQUADRA
INSERT INTO SQUADRA (nome, citta, idGM, idAllenatore, idOsservatore, n_giocatori, max_salariale) VALUES
('Lakers', 'Los Angeles', 1, 1, 1, 16, 120000000.00), -- Lakers 
('Warriors', 'San Francisco', 2, 2, 2, 14, 130000000.00), -- Warriors 
('Heat', 'Miami', 3, 3, 3, 15, '136000000.00'), -- Miami
 ('Mavericks', 'Dallas', 4, 4, 4, 12, 125000000); -- Dallas

-- Inserimento dati nella tabella STADIO
INSERT INTO STADIO (nome, capacita, citta, idSquadra) VALUES
('Crypto Arena', 19000, 'Los Angeles', 1), -- Lakers
('Oracle Arena', 18000, 'San Francisco', 2), -- Warriors
('Kaseya Center', 19600, 'Miami', 3), -- Heat
('American Airlines Center', 20000, 'Dallas', 4); -- Dallas

-- Lakers 
INSERT INTO GIOCATORE (nome, cognome, position, categoria, valutazione, anni_esperienza, eta, freeagent) VALUES
('LeBron', 'James', 'SF', 'Superstar', 9.8, 21, 39, FALSE),
('Anthony', 'Davis', 'PF', 'AllStar', 9.0, 12, 31, False), -- Dallas
('Austin', 'Reaves', 'SG', 'RolePlayer', 7.2, 3, 26, FALSE),
('Rui', 'Hachimura', 'PF', 'RolePlayer', 7.0, 5, 26, FALSE),
('DAngelo', 'Russell', 'PG', 'RolePlayer', 7.5, 9, 28, TRUE),
('Jarred', 'Vanderbilt', 'PF', 'RolePlayer', 7.0, 5, 25, FALSE),
('Christian', 'Wood', 'C', 'RolePlayer', 7.3, 8, 29, TRUE),
('Jaxson', 'Hayes', 'C', 'BenchPlayer', 6.8, 5, 24, FALSE),
('Taurean', 'Prince', 'SF', 'RolePlayer', 7.0, 8, 30, TRUE),
('Gabe', 'Vincent', 'PG', 'RolePlayer', 7.1, 5, 28, FALSE),
('Cam', 'Reddish', 'SF', 'BenchPlayer', 6.7, 5, 25, TRUE),
('Max', 'Christie', 'SG', 'BenchPlayer', 6.4, 2, 21, TRUE),
('Luka', 'Doncic', 'PG', 'Superstar', 9.9, 6, 25, FALSE),
('Alex', 'Len', 'C', 'BenchPlayer', 6.5, 11, 31, FALSE),
('Jordan', 'Goodwin', 'PG', 'BenchPlayer', 6.4, 4, 26, FALSE),
('Dalton', 'Knecht', 'SF', 'RolePlayer', 7.0, 1, 23, FALSE),
('Christian', 'Koloko', 'C', 'BenchPlayer', 6.3, 2, 24, FALSE),
('Shake', 'Milton', 'SG', 'BenchPlayer', 6.9, 5, 28, FALSE),
('Maxi', 'Kleber', 'PF', 'RolePlayer', 7.2, 7, 33, FALSE),
('Markieff', 'Morris', 'PF', 'BenchPlayer', 6.8, 13, 35, FALSE),
('Wenyen', 'Gabriel', 'PF', 'BenchPlayer', 6.7, 4, 27, TRUE),
('Dorian', 'Finney-Smith', 'SF', 'RolePlayer', 7.0, 8, 31, FALSE),
('Bronny', 'James', 'SG', 'BenchPlayer', 6.5, 1, 20, FALSE),

-- Golden State Warriors
('Stephen', 'Curry', 'PG', 'Superstar', 9.8, 15, 36, FALSE),
('Klay', 'Thompson', 'SG', 'AllStar', 8.2, 14, 34, FALSE), -- Dallas
('Draymond', 'Green', 'PF', 'RolePlayer', 7.5, 13, 34, FALSE),
('Jonathan', 'Kuminga', 'PF', 'RolePlayer', 7.2, 3, 22, FALSE),
('Kevon', 'Looney', 'C', 'RolePlayer', 7.0, 9, 28, FALSE),
('Chris', 'Paul', 'PG', 'AllStar', 8.0, 19, 39, TRUE),
('Brandin', 'Podziemski', 'SG', 'BenchPlayer', 6.9, 1, 21, FALSE),
('Moses', 'Moody', 'SG', 'BenchPlayer', 6.8, 3, 22, FALSE),
('Trayce', 'Jackson-Davis', 'C', 'BenchPlayer', 6.7, 1, 24, FALSE),
('Dario', 'Saric', 'PF', 'RolePlayer', 7.1, 9, 30, TRUE),
('Gary', 'Payton II', 'SG', 'BenchPlayer', 6.9, 8, 31, FALSE),
('Cory', 'Joseph', 'PG', 'BenchPlayer', 6.5, 13, 33, TRUE),
('Rudy', 'Gay', 'SF', 'BenchPlayer', 6.6, 18, 38, TRUE),
('Jerome', 'Robinson', 'SG', 'BenchPlayer', 6.3, 5, 27, TRUE),
('Jimmy', 'Butler', 'SF', 'Superstar', 9.6, 13, 34, FALSE), 
('Buddy', 'Hield', 'SG', 'RolePlayer', 7.4, 8, 31, FALSE), 
('Gui', 'Santos', 'SF', 'BenchPlayer', 6.5, 2, 22, FALSE),
('Kevin', 'Knox', 'SF', 'BenchPlayer', 6.4, 6, 25, FALSE),
('Reggie', 'Key', 'SG', 'BenchPlayer', 6.2, 1, 23, FALSE),
('Nathan', 'Rowe', 'C', 'BenchPlayer', 6.1, 1, 22, FALSE),

--  Miami Heat
('Bam', 'Adebayo', 'C', 'Allstar', 8.0, 13, 26, FALSE),  -- Bam Adebayo
('Tyler', 'Herro', 'SG', 'AllStar', 8.0, 14, 23, FALSE),  -- Tyler Herro
('Duncan', 'Robinson', 'SG', 'RolePlayer', 8.0, 55, 29, FALSE),  -- Duncan Robinson
('Victor', 'Oladipo', 'SG', 'AllStar', 8.0, 5, 31, FALSE),  -- Victor Oladipo
('Omer', 'Yurtseven', 'C', 'RolePlayer', 5.0, 77, 25, FALSE),  -- Omer Yurtseven
('P.J.', 'Tucker', 'PF', 'RolePlayer', 6.0, 17, 37, FALSE),  -- P.J. Tucker
('Haywood', 'Highsmith', 'SF', 'RolePlayer', 5.0, 24, 25, FALSE),  -- Haywood Highsmith
('Dewayne', 'Dedmon', 'C', 'RolePlayer', 4.0, 21, 34, FALSE),  -- Dewayne Dedmon
('Udonis', 'Haslem', 'PF', 'BenchPlayer', 3.0, 40, 43, TRUE),  -- Udonis Haslem
('Alec', 'Burks', 'SG', 'RolePlayer', 7.0, 23, 32, FALSE),  -- Alec Burks
('Jaime', 'Jacquez Jr', 'SF', 'RolePlayer', 7.0, 55, 23, FALSE),  -- Jaime Jacquez Jr.
('Nikola', 'Jovic', 'PF', 'RolePlayer', 7.0, 30, 20, FALSE),  -- Nikola Jovic
('Kevin', 'Love', 'PF', 'RolePlayer', 2.0, 9, 35, FALSE),  -- Kevin Love
('Terry', 'Rozier', 'PG', 'BenchPlayer', 7.0, 3, 29, FALSE),  -- Terry Rozier
('Andrew', 'Wiggins', 'SF', 'BenchPlayer', 7.0, 22, 29, FALSE),  -- Andrew Wiggins
('Kyle', 'Anderson', 'SF', 'RolePlayer', 7.2, 5, 31, FALSE),  -- Kyle Anderson
('Davon', 'Mitchell', 'SF', 'BenchPlayer', 6.5, 5, 31, TRUE ), -- Davon Mithcell

-- Dallas
('Kyrie', 'Irving', 'PG', 'Superstar', 8.8, 11, 31, FALSE),
('Jaden', 'Hardy', 'SG', 'RolePlayer', 7.6, 2, 22, FALSE),
('Dante', 'Exum', 'PG', 'RolePlayer', 7.4, 6, 28, FALSE),
('Dwight', 'Powell', 'C', 'RolePlayer', 7.3, 10, 32, FALSE),
('Grant', 'Williams', 'PF', 'RolePlayer', 7.7, 4, 25, FALSE),
('Naji', 'Marshall', 'SF', 'RolePlayer', 7.5, 4, 26, FALSE),
('Caleb', 'Martin', 'SF', 'RolePlayer', 7.8, 5, 28, FALSE),
('Kai', 'Jones', 'C', 'BenchPlayer', 7.0, 2, 23, FALSE),
('P.J.', 'Washington', 'PF', 'RolePlayer', 7.9, 5, 26, FALSE),
('Seth', 'Curry', 'SG', 'RolePlayer', 7.4, 10, 33, TRUE),
('Josh', 'Green', 'SG', 'RolePlayer', 7.6, 4, 24, TRUE),
('Daniel', 'Gafford', 'C', 'RolePlayer', 7.8, 5, 25, FALSE);



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
('Pau', 'Gasol', 'C', 'BenchPlayer', 6.3, 19, TRUE, 44);

-- Inserimento dati nella tabella CONTRATTO CORRETTI
INSERT INTO CONTRATTO (idSquadra, idGiocatore, data, durata, stipendio, stato) VALUES
-- Lakers (idSquadra = 1) - Contratti ATTIVI
(1, 1, '2022-07-01', 1, 47600000.00, TRUE),  -- LeBron James
(1, 3, '2023-07-06', 3, 13000000.00, TRUE),  -- Austin Reaves
(1, 4, '2023-07-06', 2, 17000000.00, TRUE),  -- Rui Hachimura
(1, 6, '2023-07-06', 3, 11200000.00, TRUE),  -- Jarred Vanderbilt
(1, 8, '2023-07-06', 1, 2500000.00, TRUE),   -- Jaxson Hayes
(1, 10, '2023-07-06', 2, 11000000.00, TRUE), -- Gabe Vincent
(1, 13, '2025-02-07', 4, 46000000.00, TRUE), -- Luka Dončić
(1, 14, '2025-02-12', 1, 1500000.00, TRUE),  -- Alex Len
(1, 15, '2025-02-07', 1, 1200000.00, TRUE),  -- Jordan Goodwin
(1, 16, '2024-07-01', 4, 6000000.00, TRUE),  -- Dalton Knecht
(1, 17, '2024-08-01', 2, 2000000.00, TRUE),  -- Christian Koloko
(1, 22, '2025-02-07', 2, 14000000.00, TRUE), -- Dorian Finney-Smith
(1, 23, '2024-07-01', 4, 1500000.00, TRUE),  -- Bronny James

-- Lakers - Contratti SCADUTI
(1, 2, '2020-12-03', 0, 40000000.00, FALSE), -- Anthony Davis
(1, 5, '2023-07-06', 0, 18000000.00, FALSE), -- D'Angelo Russell
(1, 7, '2023-07-06', 0, 2500000.00, FALSE),  -- Christian Wood
(1, 9, '2023-07-06', 0, 4000000.00, FALSE),  -- Taurean Prince
(1, 11, '2023-07-06', 0, 2000000.00, FALSE), -- Cam Reddish
(1, 12, '2023-07-06', 0, 1700000.00, FALSE), -- Max Christie
(1, 18, '2024-07-01', 0, 2500000.00, FALSE), -- Shake Milton
(1, 21, '2024-07-01', 0, 1800000.00, FALSE), -- Wenyen Gabriel

-- Warriors (idSquadra = 2) - Contratti ATTIVI
(2, 24, '2024-07-01', 2, 45000000.00, TRUE),  -- Stephen Curry
(2, 26, '2024-07-01', 1, 22000000.00, TRUE),  -- Draymond Green
(2, 28, '2024-07-01', 2, 10000000.00, TRUE),  -- Jonathan Kuminga
(2, 29, '2024-07-01', 3, 15000000.00, TRUE),  -- Kevon Looney
(2, 31, '2024-07-01', 2, 4000000.00, TRUE),   -- Brandin Podziemski
(2, 32, '2024-07-01', 3, 4500000.00, TRUE),   -- Moses Moody
(2, 33, '2024-07-01', 1, 1000000.00, TRUE),   -- Trayce Jackson-Davis
(2, 35, '2024-07-01', 3, 18000000.00, TRUE),  -- Gary Payton II
(2, 37, '2024-07-01', 2, 45000000.00, TRUE),  -- Jimmy Butler
(2, 38, '2024-07-01', 2, 23000000.00, TRUE),  -- Andrew Wiggins
(2, 39, '2024-07-01', 2, 18000000.00, TRUE),  -- Buddy Hield
(2, 40, '2024-07-01', 1, 4000000.00, TRUE),   -- Gui Santos

-- Warriors - Contratti SCADUTI
(2, 25, '2024-07-01', 0, 25000000.00, FALSE), -- Klay Thompson
(2, 30, '2024-07-01', 0, 35000000.00, FALSE), -- Chris Paul
(2, 34, '2024-07-01', 0, 25000000.00, FALSE), -- Dario Saric

-- Heat (idSquadra = 3) - Contratti ATTIVI
(3, 43, '2024-07-01', 3, 30000000.00, TRUE),  -- Bam Adebayo
(3, 44, '2024-07-01', 4, 28000000.00, TRUE),  -- Tyler Herro
(3, 45, '2024-07-01', 3, 18000000.00, TRUE),  -- Duncan Robinson
(3, 47, '2024-07-01', 1, 5000000.00, TRUE),   -- Omer Yurtseven
(3, 48, '2024-07-01', 1, 12000000.00, TRUE),  -- P.J. Tucker
(3, 50, '2024-07-01', 2, 10000000.00, TRUE),  -- Alec Burks
(3, 51, '2024-07-01', 4, 4000000.00, TRUE),   -- Jaime Jacquez Jr.
(3, 52, '2024-07-01', 3, 4000000.00, TRUE),   -- Nikola Jovic
(3, 53, '2024-07-01', 1, 12000000.00, TRUE),  -- Kevin Love
(3, 54, '2024-07-01', 2, 25000000.00, TRUE),  -- Terry Rozier
(3, 59, '2024-07-01', 1, 2000000.00, TRUE),   -- Davon Mitchell

-- Heat - Contratti SCADUTI
(3, 46, '2024-07-01', 0, 18000000.00, FALSE), -- Victor Oladipo
(3, 49, '2024-07-01', 0, 3000000.00, TRUE),   -- Udonis Haslem (free agent)

-- Mavericks (idSquadra = 4) - Contratti ATTIVI
(4, 60, '2023-07-01', 3, 38900000.00, TRUE),  -- Kyrie Irving
(4, 61, '2023-07-01', 2, 2000000.00, TRUE),   -- Jaden Hardy
(4, 62, '2023-07-01', 1, 3000000.00, TRUE),   -- Dante Exum
(4, 63, '2023-07-01', 3, 4000000.00, TRUE),   -- Dwight Powell
(4, 64, '2023-07-01', 4, 12400000.00, TRUE),  -- Grant Williams
(4, 65, '2023-07-01', 2, 5200000.00, TRUE),   -- Naji Marshall
(4, 67, '2023-07-01', 2, 7000000.00, TRUE),   -- Caleb Martin
(4, 68, '2023-07-01', 1, 1500000.00, TRUE),   -- Kai Jones
(4, 69, '2023-07-01', 3, 13200000.00, TRUE),  -- P.J. Washington
(4, 71, '2023-07-01', 3, 9400000.00, TRUE),   -- Daniel Gafford
(4, 25, '2023-07-01', 4, 40600000.00, TRUE),  -- Klay Thompson
(4, 2, '2023-07-01', 1, 40000000.00, TRUE),   -- Anthony Davis

-- Mavericks - Contratti SCADUTI
(4, 13, '2020-07-01', 0, 37000000.00, FALSE), -- Luka Dončić (vecchio contratto)
(4, 70, '2023-07-01', 0, 4000000.00, FALSE);  -- Seth Curry

-- SCAMBI COMPLETI
INSERT INTO SCAMBIO (idContratto1, idContratto2, risultato, data) VALUES
-- 1. Anthony Davis (Lakers) <-> Luka Doncic (Mavs)
(2, 13, 'Accettato', '2025-02-07'),

-- 3. Dorian Finney-Smith <-> Taurean Prince
(22, 9, 'Accettato', '2025-02-07'),

-- 4. Jimmy Butler (Heat) <-> Andrew Wiggins (Warriors)
(37, 38, 'Accettato', '2024-02-09'),

-- 5. Chris Paul <-> Jonathan Kuminga
(30, 28, 'Accettato', '2024-02-09'),

-- 6. P.J. Tucker <-> Davon Mitchell
(48, 59, 'Accettato', '2024-02-09');

-- Inserimento dati nella tabella ALLENAMENTO
INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus) VALUES
-- Lakers
(1, 'Gruppo', NULL, 120, '2023-03-15', 'Difesa'),
(1, 'Singolo', 1, 90, '2023-03-16', 'Tiro da 3 punti'), -- LeBron James
(1, 'Gruppo', NULL, 150, '2023-03-17', 'Attacco'),
(1, 'Singolo', 2, 90, '2023-03-18', 'Movimenti senza palla'), -- Anthony Davis
-- Warriors
(2, 'Gruppo', NULL, 120, '2023-03-15', 'Transizione'),
(2, 'Singolo', 15, 90, '2023-03-16', 'Tiro da 3 punti'), -- Stephen Curry
(2, 'Gruppo', NULL, 150, '2023-03-17', 'Difesa'),
(2, 'Gruppo', NULL, 90, '2023-03-18', 'Tiro libero'), 

(3, 'Gruppo', NULL, 120, '2023-03-15', 'Difesa'),
(3, 'Gruppo', Null, 90, '2023-03-16', 'Tiro da 3 punti'), 
(3, 'Gruppo', NULL, 150, '2023-03-17', 'Attacco'),
(3, 'gruppo', NULL, 90, '2023-03-18', 'Movimenti senza palla'); 

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
(9, 10, 4); -- Pick and Roll (4 serie)


INSERT INTO PARTITA (idSquadra1, idSquadra2, idStadio, risultato, data) VALUES
(1, 2, 1, '102-98', '2024-10-15'),
(2, 3, 2, '95-97', '2024-11-12'),
(1, 3, 3, '104-103', '2024-12-20'),
(3, 1, 1, '110-108', '2025-01-17'),
(2, 1, 2, '103-99', '2025-02-14'),
(3, 2, 3, '101-105', '2025-03-09');
