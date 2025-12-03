INSERT INTO games (id, name, description, picture_url, rules, status, url_game_session)
VALUES (
           '8496c496-a884-48ed-9bb3-7c3aa50fb8ca',
           'Chess',
           'Classic strategy board game',
           'https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/ARTy960/phpbTO0Re.jpeg',
           'check online the rules',
           'PUBLISHED',
           'http://localhost:3333'
       );

INSERT INTO games (id, name, description, rules, picture_url, status, url_game_session)
VALUES
    ('f2b3aaf4-6db0-4a94-9d82-123456789abc', 'Connect Four',
     'Classic 2-player connect four game',
     'Connect 4 of your pieces in a row to win.',
     'https://thewashingtonote.com/wp-content/uploads/2023/10/Connect-4-Online-scaled.jpg',
     'PUBLISHED',
     '/games/connect4');

-- Table for players
CREATE TABLE IF NOT EXISTS players (
                                       id UUID PRIMARY KEY,
                                       username VARCHAR(255) NOT NULL UNIQUE
);

-- Table for player favorite games
CREATE TABLE IF NOT EXISTS player_favorite_games (
                                                     player_id UUID NOT NULL,
                                                     game_id UUID NOT NULL,
                                                     PRIMARY KEY (player_id, game_id),
                                                     CONSTRAINT fk_favorite_games_player FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- Table for player achievements
CREATE TABLE IF NOT EXISTS player_achievements (
                                                   player_id UUID NOT NULL,
                                                   achievement_id UUID NOT NULL,
                                                   PRIMARY KEY (player_id, achievement_id),
                                                   CONSTRAINT fk_achievements_player FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- Table for friendships
CREATE TABLE IF NOT EXISTS friendships (
                                           id UUID PRIMARY KEY,
                                           player_a_id UUID NOT NULL,
                                           player_b_id UUID NOT NULL,
                                           status VARCHAR(50) NOT NULL,
                                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                           CONSTRAINT uq_friendships UNIQUE (player_a_id, player_b_id),
                                           CONSTRAINT fk_friendship_player_a FOREIGN KEY (player_a_id) REFERENCES players(id) ON DELETE CASCADE,
                                           CONSTRAINT fk_friendship_player_b FOREIGN KEY (player_b_id) REFERENCES players(id) ON DELETE CASCADE
);
