CREATE TABLE users
(
    user_id   BIGINT      NOT NULL PRIMARY KEY,
    user_name VARCHAR(60) NOT NULL,
    password  VARCHAR(60) NOT NULL,
    role      INT NULL,
    team_id   BIGINT NULL,
    enabled   TINYINT(1) NULL,
    CONSTRAINT user_name
        UNIQUE (user_name)
);

CREATE TABLE teams
(
    team_id    BIGINT NOT NULL PRIMARY KEY,
    captain_id BIGINT NULL,
    confirmed  TINYINT(1) NULL,
    team_name  VARCHAR(255) NULL,
    CONSTRAINT team_name
        UNIQUE (team_name),
    CONSTRAINT FK_captain
        FOREIGN KEY (captain_id) REFERENCES users (user_id)
);

CREATE TABLE captains
(
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT FK_team
        FOREIGN KEY (team_id) REFERENCES teams (team_id),
    CONSTRAINT FK_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE quizzes
(
    quiz_id           BIGINT NOT NULL PRIMARY KEY,
    category          INT NULL,
    datetime          datetime(6) NULL,
    quiz_name         VARCHAR(60) NULL,
    short_description VARCHAR(255) NULL,
    address_id        BIGINT NULL,
    state             INT DEFAULT 0,
    team_id           BIGINT NULL,
    CONSTRAINT FK_team
        FOREIGN KEY (team_id) REFERENCES teams (team_id),
    CONSTRAINT FK_address
        FOREIGN KEY (address_id) REFERENCES locations (location_id)
);

CREATE TABLE enrolled_quizzes
(
    team_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    CONSTRAINT FK_team
        FOREIGN KEY (team_id) REFERENCES teams (team_id),
    CONSTRAINT FK_quiz
        FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id)
);

CREATE TABLE participants
(
    participant_id BIGINT NOT NULL PRIMARY KEY,
    firstname      VARCHAR(60) NULL,
    lastname       VARCHAR(60) NULL,
    team_id        BIGINT NULL,
    CONSTRAINT FK_team
        FOREIGN KEY (team_id) REFERENCES teams (team_id)
);

CREATE TABLE locations
(
    location_id   BIGINT NOT NULL PRIMARY KEY,
    city          VARCHAR(60) NULL,
    street        VARCHAR(60) NULL,
    house_number  VARCHAR(10) NULL,
    location_name VARCHAR(60) NULL,
    zip_code      VARCHAR(20) NULL
);

CREATE TABLE results
(
    id          BIGINT NOT NULL PRIMARY KEY,
    quiz_id     BIGINT NULL,
    team_id     BIGINT NULL,
    score       INT NULL,
    total_score INT NULL,
    CONSTRAINT FK_quiz
        FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    CONSTRAINT FK_team
        FOREIGN KEY (team_id) REFERENCES teams (team_id)
);