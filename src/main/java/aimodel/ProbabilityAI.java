package aimodel;

import com.github.esrrhs.majiang_algorithm.AIUtil;
import com.github.esrrhs.majiang_algorithm.MaJiangDef;
import model.basic.Tile;
import model.basic.TileTypeEnum;
import model.tiles.Group;
import model.tiles.HandTiles;

import java.util.ArrayList;
import java.util.List;

public class ProbabilityAI implements AI {
    private List<Tile> hand;
    private List<Integer> cards;

    @Override
    public void setHand(HandTiles hand) {
        this.hand = hand.toList();
        for (Group group : hand.getPung()) {
            this.hand.addAll(group.toList());
        }
        for (Group group : hand.getKong()) {
            this.hand.addAll(group.toList());
        }
        if (hand.getNewTile() != null) {
            this.hand.add(hand.getNewTile());
        }
        this.cards = this.tilesToCards(this.hand);
    }

    @Override
    public boolean shouldPung(Tile tile) {
        final int card = this.tileToCard(tile);
        return AIUtil.pengAI(cards, new ArrayList<>(), card, 0.00);
    }

    @Override
    public boolean shouldKong(Tile tile) {
        final int card = this.tileToCard(tile);
        return AIUtil.gangAI(cards, new ArrayList<>(), card, 0.00);
    }

    @Override
    public boolean shouldChow(Tile tile) {
        return true;
    }

    @Override
    public boolean shouldSkip(Tile tile) {
        return !this.shouldPung(tile) && !this.shouldKong(tile) && !this.shouldChow(tile);
    }

    @Override
    public Tile getTileToPlay() {
        return this.cardToTile(AIUtil.outAI(cards, new ArrayList<>()));
    }

    private Integer tileToCard(Tile tile) {
        if (tile.getType() == TileTypeEnum.C) {
            return MaJiangDef.toCard(1, tile.getNumber() - 1);
        } else if (tile.getType() == TileTypeEnum.D) {
            return MaJiangDef.toCard(2, tile.getNumber() - 1);
        } else {
            return MaJiangDef.toCard(3, tile.getNumber() - 1);
        }
    }

    private Tile cardToTile(Integer card) {
        if (card >= 1 && card <= 9) {
            return new Tile(TileTypeEnum.C, card);
        } else if (card >= 10 && card <= 18) {
            return new Tile(TileTypeEnum.D, card - 9);
        } else {
            return new Tile(TileTypeEnum.B, card - 18);
        }
    }

    private List<Integer> tilesToCards(List<Tile> tiles) {
        final List<Integer> cards = new ArrayList<>();
        for (Tile tile : tiles) {
            cards.add(this.tileToCard(tile));
        }
        return cards;
    }
}
