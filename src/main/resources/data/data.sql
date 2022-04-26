INSERT INTO users(user_id, user_name, password, role)
VALUES (1, 'Nadiia', '$2b$10$ZDpLQEq4i.ELlzx6OFcoSuWpTPIq0KPXYZwfm7qtIF9DQgyvW1dOW', 0),
       (2, 'captain0', '$2b$10$FQKU13Eom4SxaiNGmrtereuIvOkobfFCyIUwSGL54zEMz9sUbRgX6', 1),
       (3, 'captain1', '$2b$10$vtpxxRusAXB2RFdc4cGd9uk2sExZQrkkEB/rtx7BBKIrBvOsbnE0e', 1),
       (4, 'captain2', '$2b$10$1.n.dyAOMnHLhdnZ.v9she10AViYaAUa8TACn7W6UBleom8IyAcz.', 1),
       (5, 'captain3', '$2b$10$3vs1UT.67BjxjYMqDBB4z.iS07gMxUEngDk4ekUaV2tSlHgBHHPUS', 1),
       (6, 'captain4', '$2b$10$NGnPQDlXvH3X6nal1phi0.6prlMixeiUZC6CajmCWCnKm2ApRtbMa', 1),
       (7, 'captain5', '$2b$10$kDt4ZDJ2QXmSCm2wlEF2Q.AUEpEZf82o.AMi7BZu33/LFIhDUT8nW', 1);

INSERT INTO teams(team_id, captain_id, confirmed, team_name)
VALUES (1, 2, 1, 'team1'),
       (5, 3, 1, 'team2'),
       (3, 4, 1, 'team3'),
       (4, 5, 1, 'team4'),
       (5, 6, 1, 'team5'),
       (6, 7, 1, 'team6');

INSERT INTO participants(participant_id, firstname, lastname, team_id)
VALUES (1, 'name11', 'surname11', 1),
       (2, 'name12', 'surname12', 1),
       (3, 'name13', 'surname13', 1),
       (4, 'name14', 'surname14', 1),
       (5, 'name15', 'surname15', 1),
       (6, 'name16', 'surname16', 1),
       (7, 'name17', 'surname17', 1),

       (8, 'name21', 'surname21', 2),
       (9, 'name22', 'surname22', 2),
       (10, 'name23', 'surname23', 2),
       (11, 'name24', 'surname24', 2),
       (12, 'name25', 'surname25', 2),
       (13, 'name26', 'surname26', 2),
       (14, 'name27', 'surname27', 2),

       (15, 'name31', 'surname31', 3),
       (16, 'name32', 'surname32', 3),
       (17, 'name33', 'surname33', 3),
       (18, 'name34', 'surname34', 3),
       (19, 'name35', 'surname35', 3),
       (20, 'name36', 'surname36', 3),
       (21, 'name37', 'surname37', 3),

       (22, 'name41', 'surname41', 4),
       (23, 'name42', 'surname42', 4),
       (24, 'name43', 'surname43', 4),
       (25, 'name44', 'surname44', 4),
       (26, 'name45', 'surname45', 4),
       (27, 'name46', 'surname46', 4),
       (28, 'name47', 'surname47', 4),

       (29, 'name51', 'surname51', 5),
       (30, 'name52', 'surname52', 5),
       (31, 'name53', 'surname53', 5),
       (32, 'name54', 'surname54', 5),
       (33, 'name55', 'surname55', 5),
       (34, 'name56', 'surname56', 5),
       (35, 'name57', 'surname57', 5),

       (36, 'name61', 'surname61', 6),
       (37, 'name62', 'surname62', 6),
       (38, 'name63', 'surname63', 6),
       (39, 'name64', 'surname64', 6),
       (40, 'name65', 'surname65', 6),
       (41, 'name66', 'surname66', 6),
       (42, 'name67', 'surname67', 6);

INSERT INTO locations(location_id, city, street, house_number, location_name, zip_code)
VALUES (1, 'city1', 'street1', 'number1', 'name1', 'zip1'),
       (2, 'city2', 'street2', 'number2', 'name2', 'zip2');


INSERT INTO quizzes(quiz_id, category, datetime, quiz_name, short_description, address_id, state)
VALUES (1, 0, '2022-08-15 15:00:00', 'quiz1', 'short1', 1, 2),
       (2, 1, '2022-01-15 15:00:00', 'quiz2', 'short2', 2, 2),
       (3, 1, '2022-02-15 15:00:00', 'quiz3', 'short3', 2, 2),
       (4, 1, '2022-07-15 15:00:00', 'quiz4', 'short4', 2, 2),
       (5, 0, '2022-08-15 15:00:00', 'quiz5', 'short5', 1, 2);


INSERT INTO enrolled_quizzes(quiz_id, team_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),

       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),

       (3, 1),
       (3, 2),
       (3, 3),
       (3, 5),
       (3, 6),

       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6),

       (5, 2),
       (5, 5),
       (5, 6);

INSERT INTO results(id, quiz_id, team_id, score)
VALUES (1, 1, 1, 20),
       (2, 1, 2, 11),
       (3, 1, 3, 14),
       (4, 1, 4, 13),
       (5, 1, 5, 7),
       (6, 1, 6, 10),

       (8, 2, 2, 10),
       (9, 2, 3, 19),
       (10, 2, 4, 18),
       (11, 2, 5, 3),

       (13, 3, 1, 18),
       (14, 3, 2, 16),
       (15, 3, 3, 17),
       (16, 3, 5, 16),
       (17, 3, 6, 17),

       (19, 4, 1, 13),
       (20, 4, 2, 19),
       (21, 4, 3, 14),
       (22, 4, 4, 20),
       (23, 4, 5, 17),
       (24, 4, 6, 10),

       (26, 5, 2, 15),
       (27, 5, 5, 10),
       (29, 5, 6, 18);