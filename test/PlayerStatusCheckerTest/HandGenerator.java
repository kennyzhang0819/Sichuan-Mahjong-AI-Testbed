package PlayerStatusCheckerTest;

import model.basic.Tile;
import model.basic.TileTypeEnum;
import utils.NumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HandGenerator {

    private static final TileTypeEnum[] TILE_TYPES = {TileTypeEnum.B, TileTypeEnum.C, TileTypeEnum.D};
    private static final int HAND_SIZE = 14;
    private static final int TILE_NUMBER_RANGE = 9;
    private static final int TRIPLET_SIZE = 3;
    private static final int SEQUENCE_SIZE = 3;
    private static final int PAIR_SIZE = 2;
    private final Random random = new Random();

    public List<Tile> generateRandomHand() {
        List<Tile> hand = new ArrayList<>();
        TileTypeEnum[] twoTypes = {TILE_TYPES[random.nextInt(TILE_TYPES.length)], TILE_TYPES[random.nextInt(TILE_TYPES.length)]};
        for (int i = 0; i < HAND_SIZE; i++) {
            TileTypeEnum type = twoTypes[random.nextInt(twoTypes.length)];
            int number = random.nextInt(TILE_NUMBER_RANGE) + 1;
            hand.add(new Tile(type, number));
        }

        return hand;
    }

    public List<Tile> generateLikelyStandardHuHand() {
        List<Tile> hand = new ArrayList<>();
        TileTypeEnum[] twoTypes = {TILE_TYPES[random.nextInt(TILE_TYPES.length)], TILE_TYPES[random.nextInt(TILE_TYPES.length)]};
        // First, generate a pair
        TileTypeEnum pairType = twoTypes[random.nextInt(twoTypes.length)];
        int pairNumber = random.nextInt(TILE_NUMBER_RANGE) + 1;
        for (int i = 0; i < PAIR_SIZE; i++) {
            hand.add(new Tile(pairType, pairNumber));
        }

        int[] nm = NumberGenerator.generateNumbers(2, 4);

        // Then generate two sequences
        for (int i = 0; i < nm[0]; i++) {
            TileTypeEnum sequenceType = twoTypes[random.nextInt(twoTypes.length)];
            int sequenceStart = random.nextInt(TILE_NUMBER_RANGE - 2) + 1;
            for (int j = sequenceStart; j < sequenceStart + SEQUENCE_SIZE; j++) {
                hand.add(new Tile(sequenceType, j));
            }
        }

        for (int i = 0; i < nm[1]; i++) {
            TileTypeEnum tripleType = twoTypes[random.nextInt(twoTypes.length)];
            int tripleNumber = random.nextInt(TILE_NUMBER_RANGE) + 1;
            for (int j = 0; j < TRIPLET_SIZE; j++) {
                hand.add(new Tile(tripleType, tripleNumber));
            }
        }


        return hand;
    }

}
