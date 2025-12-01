    package be.kdg.banditgames.platform.domain;

    import be.kdg.banditgames.common.shared.GameId;

    import java.util.UUID;

    public class Game {
        private final GameId gameId;
        private String name;
        private String description;
        private String rules;
        private String pictureUrl;
        private GameStatus status;
        private String urlGameSession;

        public Game(GameId gameId, String name, String description, String rules, String pictureUrl, GameStatus status, String urlGameSession) {
            this.gameId = gameId;
            this.name = name;
            this.description = description;
            this.rules = rules;
            this.pictureUrl = pictureUrl;
            this.status = status;
            this.urlGameSession = urlGameSession;
        }

        public Game(String name, String description, String rules, String pictureUrl, String urlGameSession) {
            this.name = name;
            this.description = description;
            this.rules = rules;
            this.pictureUrl = pictureUrl;
            this.urlGameSession = urlGameSession;
            this.gameId = GameId.of(UUID.randomUUID());
            this.status = GameStatus.DRAFT;
        }

        public void acceptGame() {
            this.status = GameStatus.PUBLISHED;
        }

        public boolean isPlayable() {
            return status == GameStatus.PUBLISHED;
        }

        public GameId getGameId() {
            return gameId;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getRules() {
            return rules;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public GameStatus getStatus() {
            return status;
        }

        public String getUrlGameSession() {
            return urlGameSession;
        }
        /**
         * For Chess  set:
         * name = "Chess"
         * urlGameSession = "http://localhost:3333" (chess frontend)
         * For Connect Four e.g.:
         * name = "Connect Four"
         * urlGameSession = "/games/connect4" (internal route in your SPA).
         */
    }
