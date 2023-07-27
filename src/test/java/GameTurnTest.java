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
            add(new Player("player", new ArrayList<>()));
            add(new AI1("ai1", new ArrayList<>()));
            add(new AI2("ai2", new ArrayList<>()));
            add(new AI3("ai3", new ArrayList<>()));
        }};

        GameTurn gameTurn = new GameTurn(players, players.get(0));
        System.out.println(gameTurn.getPlayerAfter(players.get(0)));
    }
}
