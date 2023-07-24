package application;

import application.core.Game;
import model.OutputData;
import model.basic.Tile;

public class GameLogic {
    private Game game;
    private OutputData outputData;

    public GameLogic() {
        this.game = new Game();
        this.outputData = this.game.next();
    }

    public void performPlayerAction(Tile hoveredTile) {
        if (hoveredTile != null) {
            game.getPlayers().get(0).plays(hoveredTile);
            for (int i = 0; i < 4; i++) {
                outputData = game.next();
            }
        }
    }

    public boolean isGameOver() {
        return game.isOver();
    }

    public OutputData getOutputData() {
        return outputData;
    }

    // Add other methods related to the game logic if necessary
}
