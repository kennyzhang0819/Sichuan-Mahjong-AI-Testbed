package application;

import model.Player;

import java.util.ArrayList;
import java.util.List;

public class GameTurn {
    private int count;
    private final List<Player> turns;

    public GameTurn(List<Player> players, Player startingPlayer) {
        this.count = 0;
        int startingIndex = players.indexOf(startingPlayer);
        this.turns = new ArrayList<>();
        this.turns.add(startingPlayer);
        for (int i = 1; i <= 3; i++) {
            int nextIndex = (startingIndex + i) % players.size();
            Player nextPlayer = players.get(nextIndex);
            this.turns.add(nextPlayer);
        }
    }

    public Player getNext() {
        Player player = this.turns.remove(0);
        this.turns.add(player);
        this.count++;
        return player;
    }

    public int getCount() {
        return count;
    }
}
