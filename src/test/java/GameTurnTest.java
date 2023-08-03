import application.game.GameTurn;
import model.players.AI1;
import model.players.AI2;
import model.players.AI3;
import model.players.Player;

import java.util.ArrayList;
import java.util.List;

public class GameTurnTest  {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<Player>() {{
            add(new Player("player", new ArrayList<>(), 0));
            add(new AI1("ai1", new ArrayList<>(), 1));
            add(new AI2("ai2", new ArrayList<>(), 2));
            add(new AI3("ai3", new ArrayList<>(), 3));
        }};

        GameTurn gameTurn = new GameTurn(players, players.get(1));
        System.out.println(gameTurn.getRoundsUntilPlayer());
    }
}
