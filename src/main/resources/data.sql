-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Ten player users, named player1 with passwor 0wn3r
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');
INSERT INTO appusers(id,username,password,authority) VALUES (4,'player1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'player2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'player3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'player4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'player5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'player6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'player7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'player8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'player9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'player10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);

-- === BOARDS ===
insert into chess_board (id, creator_turn, current_turn_start, jaque)
values 
  (100, true,  '2025-01-01 10:00:00', false),
  (101, false, '2025-01-02 12:00:00', false),
  (102, true,  '2025-01-03 09:30:00', true),
  (103, false, '2025-01-04 15:45:00', false),
  (104, true,  '2025-01-05 18:20:00', false);

-- === PROBLEMS ===
insert into chess_problem (id, name, difficulty_level, goal, board_id)
values
  (1, 'Mate in 1 (easy)',           1, 'checkmate in 1', 100),
  (2, 'Fork tactics',               2, 'win material',   101),
  (3, 'Mate in 2',                  3, 'checkmate in 2', 102),
  (4, 'Endgame K+P',                2, 'promote pawn',   103),
  (5, 'Complex middlegame',         4, 'find best move', 104);

-- === PIECES ===
-- Board 100 -> 2 pieces
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (1001, 100, 'K', 'W', 'e', 1),
  (1002, 100, 'k', 'B', 'e', 8);

-- Board 101 -> 5 pieces
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (1011, 101, 'K', 'W', 'g', 1),
  (1012, 101, 'Q', 'W', 'd', 1),
  (1013, 101, 'k', 'B', 'g', 8),
  (1014, 101, 'p', 'B', 'f', 7),
  (1015, 101, 'n', 'B', 'c', 6);

-- Board 102 -> 7 pieces
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (1021, 102, 'K', 'W', 'g', 1),
  (1022, 102, 'R', 'W', 'a', 1),
  (1023, 102, 'B', 'W', 'c', 1),
  (1024, 102, 'k', 'B', 'g', 8),
  (1025, 102, 'q', 'B', 'd', 8),
  (1026, 102, 'p', 'B', 'f', 7),
  (1027, 102, 'p', 'B', 'h', 7);

-- Board 103 -> 0 pieces (empty)

-- Board 104 -> 10 pieces
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (1041, 104, 'K', 'W', 'e', 1),
  (1042, 104, 'Q', 'W', 'd', 1),
  (1043, 104, 'R', 'W', 'a', 1),
  (1044, 104, 'R', 'W', 'h', 1),
  (1045, 104, 'B', 'W', 'c', 1),
  (1046, 104, 'N', 'W', 'g', 1),
  (1047, 104, 'k', 'B', 'e', 8),
  (1048, 104, 'q', 'B', 'd', 8),
  (1049, 104, 'b', 'B', 'c', 8),
  (1050, 104, 'n', 'B', 'g', 8);


-- === CHESS BOARDS FOR ATTEMPTS ===
-- Each attempt gets its own board snapshot

insert into chess_board (id, creator_turn, current_turn_start, jaque)
values
  (200, true,  '2025-03-01 10:00:00', false),
  (201, false, '2025-03-02 11:00:00', true),
  (202, true,  '2025-03-03 09:30:00', false);

-- === PIECES for attempt boards ===
-- Board 200 (Alice's attempt) -> simple mate pattern
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (2001, 200, 'K', 'W', 'e', 1),
  (2002, 200, 'Q', 'W', 'h', 5),
  (2003, 200, 'k', 'B', 'g', 8);

-- Board 201 (Bob's attempt) -> endgame pattern
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (2011, 201, 'K', 'W', 'c', 3),
  (2012, 201, 'P', 'W', 'f', 5),
  (2013, 201, 'k', 'B', 'g', 7);

-- Board 202 (Carol's attempt) -> complex middle game
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (2021, 202, 'K', 'W', 'e', 1),
  (2022, 202, 'Q', 'W', 'd', 3),
  (2023, 202, 'N', 'W', 'f', 3),
  (2024, 202, 'k', 'B', 'e', 8),
  (2025, 202, 'q', 'B', 'd', 8),
  (2026, 202, 'n', 'B', 'f', 6);

-- === CHESS PROBLEM ATTEMPTS ===
insert into chess_problem_attempt (id, name, start, finish, player_id, problem_id, board_id)
values
  (300, 'player 1 - Mate in 1 attempt', '2025-03-01 09:55:00', '2025-03-01 10:05:00', 4, 1, 200),
  (301, 'player 2 - Endgame try',         '2025-03-02 10:50:00', '2025-03-02 11:30:00', 5, 3, 201),
  (302, 'player 3 - Mate in 2 test',    '2025-03-03 09:15:00', '2025-03-03 09:45:00', 6, 4, 202);


-- ========== SOLUTION BOARDS ==========
insert into chess_board (id, creator_turn, current_turn_start, jaque)
values
  (910, false, '2025-01-10 09:10:00', true),   -- final for Mate in 1
  (911, true,  '2025-01-10 09:20:00', true);   -- final for Mate in 2

-- Pieces for solution board 910 (mate in 1)
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (9101, 910, 'K', 'W', 'e', 1),
  (9102, 910, 'Q', 'W', 'h', 5),
  (9103, 910, 'k', 'B', 'g', 8);

-- Pieces for solution board 911 (mate in 2)
insert into piece (id, board_id, type, color, pos_file, pos_rank) values
  (9111, 911, 'K', 'W', 'g', 1),
  (9112, 911, 'R', 'W', 'h', 1),
  (9113, 911, 'Q', 'W', 'd', 5),
  (9114, 911, 'k', 'B', 'g', 8);