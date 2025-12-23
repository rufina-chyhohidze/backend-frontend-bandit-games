
INSERT INTO games (id, name, description, rules, picture_url, status, url_game_session)
VALUES
    ('f2b3aaf4-6db0-4a94-9d82-123456789abc', 'Connect Four',
     'Classic 2-player connect four game',
     'Connect 4 of your pieces in a row to win.',
     'https://thewashingtonote.com/wp-content/uploads/2023/10/Connect-4-Online-scaled.jpg',
     'DRAFT',
     'http://localhost:8000/connect4/index.html');


INSERT INTO achievements (achievement_id, game_id, name, description, unlock_hint)
VALUES
    ('00000000-0000-0000-0000-000000000011',
     'f2b3aaf4-6db0-4a94-9d82-123456789abc',
     'First Connect',
     'Win your first Connect 4 match on the platform.',
     'Win any Connect 4 match.'),

    ('00000000-0000-0000-0000-000000000012',
     'f2b3aaf4-6db0-4a94-9d82-123456789abc',
     'Vertical Master',
     'Win with a vertical line of four.',
     'Place four of your discs vertically.'),

    ('00000000-0000-0000-0000-000000000013',
     'f2b3aaf4-6db0-4a94-9d82-123456789abc',
     'Diagonal Genius',
     'Win with a diagonal line of four.',
     'Create a diagonal line of four discs.'),

    ('00000000-0000-0000-0000-000000000014',
    'f2b3aaf4-6db0-4a94-9d82-123456789abc',
    'Horizontal Hero',
    'Win with a horizontal line of four.',
    'Place four of your discs horizontally.'),

    ('00000000-0000-0000-0000-000000000015',
     'f2b3aaf4-6db0-4a94-9d82-123456789abc',
     'Speedster',
     'Win the game in under 10 moves.',
     'Achieve victory before either player has placed 10 discs.'),

    ('00000000-0000-0000-0000-000000000016',
     'f2b3aaf4-6db0-4a94-9d82-123456789abc',
     'Late Game Legend',
     'Win after the board is almost full.',
     'Achieve victory when 38 or more discs have been placed.');


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

CREATE TABLE IF NOT EXISTS event_publication (
                                                 id UUID NOT NULL,
                                                 listener_id VARCHAR(512) NOT NULL,
                                                 event_type VARCHAR(512) NOT NULL,
                                                 serialized_event TEXT NOT NULL,
                                                 publication_date TIMESTAMP(6) WITH TIME ZONE NOT NULL,
                                                 completion_date TIMESTAMP(6) WITH TIME ZONE,
                                                 PRIMARY KEY (id)
);

-- DROP TABLE event_publication;