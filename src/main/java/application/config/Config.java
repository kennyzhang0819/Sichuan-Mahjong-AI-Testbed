package application.config;

public class Config {
    //Settings
    public static final int FPS = 60;
    public static final int LOG_ITEMS = 39;

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
    public static final int TILE_HEIGHT = (int) (TILE_WIDTH * TILE_HEIGHT_MULTIPLIER);

    //Specific Tile Sizes


    //PlayerHand
    public static final int PLAYER_HAND_X = 200;
    public static final int PLAYER_HAND_Y = 600;
    public static final int PLAYER_HAND_WIDTH = 1100;
    public static final int PLAYER_HAND_HEIGHT = 200;
    public static final int PLAYER_HAND_TOP_INDENT = PLAYER_HAND_Y + 50;
    public static final int FOURTEENTH_TILE_INDENT = 920;
    public static final int PLAYER_HAND_TILE_PADDING = 10;

    //Table Commons
    private static final double SHRINK_MULTIPLIER = 0.65;
    public static final int TABLE_TILE_PADDING = (int) (PLAYER_HAND_TILE_PADDING * SHRINK_MULTIPLIER);
    public static final int TABLE_TILE_WIDTH = (int) (TILE_WIDTH * SHRINK_MULTIPLIER);
    public static final int TABLE_TILE_HEIGHT = (int) (TILE_HEIGHT * SHRINK_MULTIPLIER);

    //PlayerTable
    public static final int PLAYER_TABLE_X = 200;
    public static final int PLAYER_TABLE_Y = 500;
    public static final int PLAYER_TABLE_WIDTH = PLAYER_HAND_WIDTH;
    public static final int PLAYER_TABLE_HEIGHT = 100;

    //AITable
    public static final int AI_TABLE_PADDING = 200;
    public static final int AI_TABLE_Y = 100;
    public static final int AI_TABLE_WIDTH = 250;
    public static final int AI_TABLE_HEIGHT = 300;
    public static final int AI_TABLE_NUM_TILES_PER_LINE = 5;


    //AI1Table
    public static final int AI1_TABLE_X = 100;
    public static final int AI1_TABLE_Y = AI_TABLE_Y;
    public static final int AI1_TABLE_WIDTH = AI_TABLE_WIDTH;
    public static final int AI1_TABLE_HEIGHT = AI_TABLE_HEIGHT;

    //AI2Table
    public static final int AI2_TABLE_X = AI1_TABLE_X + AI1_TABLE_WIDTH + AI_TABLE_PADDING;
    public static final int AI2_TABLE_Y = AI_TABLE_Y;
    public static final int AI2_TABLE_WIDTH = AI_TABLE_WIDTH;
    public static final int AI2_TABLE_HEIGHT = AI_TABLE_HEIGHT;

    //AI3Table
    public static final int AI3_TABLE_X = AI2_TABLE_X + AI2_TABLE_WIDTH + AI_TABLE_PADDING;
    public static final int AI3_TABLE_Y = AI_TABLE_Y;
    public static final int AI3_TABLE_WIDTH = AI_TABLE_WIDTH;
    public static final int AI3_TABLE_HEIGHT = AI_TABLE_HEIGHT;




}