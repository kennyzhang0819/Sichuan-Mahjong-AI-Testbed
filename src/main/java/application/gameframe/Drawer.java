package application.gameframe;

import application.config.Config;
import model.basic.Entity;
import model.basic.Tile;
import model.tiles.Group;
import model.tiles.GroupEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Drawer {

    private final Graphics2D g2;
    private final int width;
    private final int height;
    private final TileImageLoader imageLoader;

    public Drawer(Graphics2D g2, int width, int height, TileImageLoader imageLoader) {
        this.g2 = g2;
        this.width = width;
        this.height = height;
        this.imageLoader = imageLoader;
    }

    public void drawBackground() {
        Color baseGreen = new Color(30, 100, 60);
        Color highlightGreen = new Color(60, 130, 80);

        // Draw the base background
        g2.setColor(baseGreen);
        g2.fillRect(0, 0, this.width, this.height);

        // Draw "felt" texture
        int rectSize = 100; // Size of the small rectangles used for texture
        g2.setColor(highlightGreen);

        for (int y = 0; y < this.height; y += rectSize) {
            for (int x = y % (2 * rectSize); x < this.width; x += 2 * rectSize) {
                g2.fillRect(x, y, rectSize, rectSize);
            }
        }
    }

    public void drawLogs(List<String> logs) {
        g2.setFont(new Font("Arial", Font.PLAIN, 15)); // Choose the font and its size
        g2.setColor(Color.WHITE); // Choose a color for the text
        int lineHeight = g2.getFontMetrics().getHeight(); // The height of a line of text
        int padding = 100; // Padding around the log window
        int logWindowWidth = 400; // Width of the log window
        int logWindowX = this.width - logWindowWidth; // X coordinate of the log window
        int textPadding = 20;
        g2.setColor(Color.BLACK); // Set the background color of the log window
        g2.fillRect(logWindowX, padding, logWindowWidth, this.height - padding * 2);
        g2.setColor(Color.WHITE); // Change color for the text

        for (int i = 0; i < logs.size(); i++) {
            String log = logs.get(i);
            g2.drawString(log, logWindowX + textPadding, padding + textPadding + i * lineHeight);
        }
    }

    public void drawRect(Entity entity) {
        g2.setColor(Color.WHITE);
        g2.drawRect((int) entity.x, (int) entity.y, entity.width, entity.height);
    }

    public void drawInstructions() {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.drawString("Press 'H' to Hu", 30, 430);
        g2.drawString("Press 'C' to Chi", 30, 450);
        g2.drawString("Press 'P' to Pung", 30, 470);
        g2.drawString("Press 'K' to Kong", 30, 490);
        g2.drawString("Press 'S' to Skip", 30, 510);
    }

    public void drawHelperBoxes() {
        Entity playerHand = new Entity(Config.PLAYER_HAND_X, Config.PLAYER_HAND_Y, Config.PLAYER_HAND_WIDTH, Config.PLAYER_HAND_HEIGHT);
        Entity playerTable = new Entity(Config.PLAYER_TABLE_X, Config.PLAYER_TABLE_Y, Config.PLAYER_TABLE_WIDTH, Config.PLAYER_TABLE_HEIGHT);
        Entity ai1Table = new Entity(Config.AI1_TABLE_X, Config.AI1_TABLE_Y, Config.AI1_TABLE_WIDTH, Config.AI1_TABLE_HEIGHT);
        Entity ai2Table = new Entity(Config.AI2_TABLE_X, Config.AI2_TABLE_Y, Config.AI2_TABLE_WIDTH, Config.AI2_TABLE_HEIGHT);
        Entity ai3Table = new Entity(Config.AI3_TABLE_X, Config.AI3_TABLE_Y, Config.AI3_TABLE_WIDTH, Config.AI3_TABLE_HEIGHT);
        this.drawRect(playerHand);
        this.drawRect(playerTable);
        this.drawRect(ai1Table);
        this.drawRect(ai2Table);
        this.drawRect(ai3Table);
    }

    public void drawTile(Tile tile, Color color) {
        Image image = null;
        if (tile.width == Config.TILE_WIDTH) {
            image = imageLoader.getImage(tile);
        } else if (tile.width == Config.TABLE_TILE_WIDTH) {
            image = imageLoader.getTableImage(tile);
        }
        assert image != null;
        g2.setColor(color);
        g2.fillRect((int) tile.x, (int) tile.y, tile.width, tile.height);
        g2.drawImage(image, (int) tile.x, (int) tile.y, null);
    }

    public void drawTable(List<Tile> tiles, int playerPosition) {
        if (playerPosition == 0) {
            for (int i = 0; i < tiles.size(); i++) {
                Tile tile = tiles.get(i);
                tile.x = Config.PLAYER_TABLE_X + Config.TABLE_TILE_PADDING * i + Config.TABLE_TILE_WIDTH * i;
                tile.y = Config.PLAYER_TABLE_Y;
                tile.width = Config.TABLE_TILE_WIDTH;
                tile.height = Config.TABLE_TILE_HEIGHT;
                this.drawTile(tile, Color.WHITE);
            }
        } else {
            final List<Integer> aiTableX = Arrays.asList(Config.AI1_TABLE_X, Config.AI2_TABLE_X, Config.AI3_TABLE_X);
            int currentLine = 0;
            int currentTile = 0;
            for (Tile tile : tiles) {
                tile.x = aiTableX.get(playerPosition - 1) + Config.TABLE_TILE_PADDING * currentTile + Config.TABLE_TILE_WIDTH * currentTile;
                tile.y = Config.AI_TABLE_Y + Config.TABLE_TILE_HEIGHT * currentLine + Config.TABLE_TILE_PADDING * currentLine;
                tile.width = Config.TABLE_TILE_WIDTH;
                tile.height = Config.TABLE_TILE_HEIGHT;
                this.drawTile(tile, Color.WHITE);
                currentTile++;
                if (currentTile == Config.AI_TABLE_NUM_TILES_PER_LINE) {
                    currentLine++;
                    currentTile = 0;
                }
            }
        }
    }

    public void drawPungKong(List<Group> pungKong, int playerPosition) {
        if (pungKong.size() <= 0) {
            return;
        }
        final List<Integer> pungKongX = Arrays.asList(
                Config.PLAYER_TABLE_X + Config.PLAYER_TABLE_WIDTH,
                Config.AI1_TABLE_X + Config.AI_TABLE_WIDTH,
                Config.AI2_TABLE_X + Config.AI_TABLE_WIDTH,
                Config.AI3_TABLE_X + Config.AI_TABLE_WIDTH);

        final List<Integer> pungKongY = Arrays.asList(
                Config.PLAYER_TABLE_Y, Config.AI1_TABLE_Y, Config.AI2_TABLE_Y, Config.AI3_TABLE_Y);
        Color color;
        for (int i = 0; i < pungKong.size(); i++) {
            Group group = pungKong.get(i);
            if (group.getCategory() == GroupEnum.PUNG) {
                color = Color.YELLOW;
            } else {
                color = Color.ORANGE;
            }
            int totalTilesInGroup = group.toList().size();
            int groupX = pungKongX.get(playerPosition);
            for (int j = 0; j < totalTilesInGroup; j++) {
                Tile tile = group.toList().get(j);
                tile.x = groupX + Config.TABLE_TILE_WIDTH * j;
                tile.y = pungKongY.get(playerPosition) + Config.TABLE_TILE_HEIGHT * i + Config.TABLE_TILE_PADDING * i;
                tile.width = Config.TABLE_TILE_WIDTH;
                tile.height = Config.TABLE_TILE_HEIGHT;
                this.drawTile(tile, color);
            }
        }
    }
    public List<Tile> drawPlayerHand(List<Tile> hand, Tile newTile) {
        for (int i = 0; i < hand.size(); i++) {
            Tile tile = hand.get(i);
            tile.x = Config.PLAYER_HAND_X + Config.PLAYER_HAND_TILE_PADDING * i + Config.TILE_WIDTH * i;
            tile.y = Config.PLAYER_HAND_TOP_INDENT;
            tile.width = Config.TILE_WIDTH;
            tile.height = Config.TILE_HEIGHT;
            this.drawTile(tile, Color.WHITE);
        }

        if (newTile != null) {
            newTile.x = Config.PLAYER_HAND_X + Config.PLAYER_HAND_TILE_PADDING * hand.size()
                    + Config.TILE_WIDTH * hand.size()
                    + Config.FOURTEENTH_TILE_INDENT;
            newTile.y = Config.PLAYER_HAND_TOP_INDENT;
            newTile.width = Config.TILE_WIDTH;
            newTile.height = Config.TILE_HEIGHT;
            this.drawTile(newTile, Color.WHITE);
        }
        return new ArrayList<Tile>() {{
            addAll(hand);
            if (newTile != null) {
                add(newTile);
            }
        }};
    }
}
