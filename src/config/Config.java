package config;

public class Config {
    //Settings
    public static final int FPS = 30;

    //Frame
    public static final int ORIGINAL_TILE_SIZE = 16;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * 4;
    public static final int SCREEN_COLUMNS = 25;
    public static final int SCREEN_ROWS = 15;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COLUMNS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;


    //GameTile Sizes
    private static final double TILE_HEIGHT_MULTIPLIER = 1.3;
    public static final int TILE_WIDTH = TILE_SIZE;
    public static final double TILE_HEIGHT = TILE_WIDTH * TILE_HEIGHT_MULTIPLIER;


    //Specific Tile Sizes
    //PlayerHand
    public static final int PLAYER_HAND_LEFT_INDENT = 200;
    public static final int PLAYER_HAND_TOP_INDENT = 650;
    public static final int FOURTEENTH_TILE_INDENT = 80;
    public static final int TILE_PADDING = 10;

    //PlayerTable
    public static final int PLAYER_TABLE_LEFT_INDENT = 250;
    public static final int PLAYER_TABLE_TOP_INDENT = 500;
    public static final double SHRINK_MULTIPLIER = 0.5;
    public static final double TABLE_TILE_PADDING = TILE_PADDING * SHRINK_MULTIPLIER;
    public static final double TABLE_TILE_WIDTH = TILE_WIDTH * SHRINK_MULTIPLIER;
    public static final double TABLE_TILE_HEIGHT = TILE_HEIGHT * SHRINK_MULTIPLIER;





}