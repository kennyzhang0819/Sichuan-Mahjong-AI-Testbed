package config;

public class Config {
    //Settings
    public static final int FPS = 60;

    //Frame
    public static final int ORIGINAL_TILE_SIZE = 16;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * 4;
    public static final int SCREEN_COLUMNS = 30;
    public static final int SCREEN_ROWS = 15;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COLUMNS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;


    //GameTile Sizes
    private static final double TILE_HEIGHT_MULTIPLIER = 1.4;
    public static final int TILE_WIDTH = TILE_SIZE;
    public static final double TILE_HEIGHT = TILE_WIDTH * TILE_HEIGHT_MULTIPLIER;

    //Specific Tile Sizes


    //PlayerHand
    public static final int PLAYER_HAND_X = 200;
    public static final int PLAYER_HAND_Y = 600;
    public static final int PLAYER_HAND_WIDTH = SCREEN_WIDTH - 700;
    public static final int PLAYER_HAND_HEIGHT = 200;
    public static final int PLAYER_HAND_LEFT_INDENT = PLAYER_HAND_X + 50;
    public static final int PLAYER_HAND_TOP_INDENT = PLAYER_HAND_Y + 50;
    public static final int FOURTEENTH_TILE_INDENT = 80;
    public static final int PLAYER_HAND_TILE_PADDING = 10;

    //PlayerTable
    public static final int PLAYER_TABLE_X = 200;
    public static final int PLAYER_TABLE_Y = 500;
    public static final int PLAYER_TABLE_WIDTH = SCREEN_WIDTH - 700;
    public static final int PLAYER_TABLE_HEIGHT = 100;
    public static final double SHRINK_MULTIPLIER = 0.65;
    public static final double PLAYER_TABLE_TILE_PADDING = PLAYER_HAND_TILE_PADDING * SHRINK_MULTIPLIER;
    public static final double TABLE_TILE_WIDTH = TILE_WIDTH * SHRINK_MULTIPLIER;
    public static final double TABLE_TILE_HEIGHT = TILE_HEIGHT * SHRINK_MULTIPLIER;



}