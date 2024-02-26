
CREATE TABLE game_results (
    gameID INT AUTO_INCREMENT PRIMARY KEY,
    Mafia1 VARCHAR(255) NOT NULL,
    Mafia2 VARCHAR(255) NOT NULL,
    Citizen1 VARCHAR(255) NOT NULL,
    Citizen2 VARCHAR(255) NOT NULL,
    Citizen3 VARCHAR(255) NOT NULL,
    Doctor VARCHAR(255) NOT NULL,
    Police VARCHAR(255) NOT NULL,
    MafiaWin BOOLEAN NOT NULL,
    CivilWin BOOLEAN NOT NULL
);

select * from game_results;

drop database mafia;
