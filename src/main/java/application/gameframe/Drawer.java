package application.gameframe;

import config.Config;
import model.basic.Entity;
import model.basic.Tile;

import java.awt.*;
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

    public void drawTile(Tile tile) {
        Image image = null;
        if (tile.width == Config.TILE_WIDTH) {
            image = imageLoader.getImage(tile);
        } else if (tile.width == Config.TABLE_TILE_WIDTH) {
            image = imageLoader.getTableImage(tile);
        }
        assert image != null;
        g2.setColor(Color.WHITE);
        g2.fillRect((int) tile.x, (int) tile.y, tile.width, tile.height);
        g2.drawImage(image, (int) tile.x, (int) tile.y, null);
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
        g2.drawString("Press 'H' to Hu", 30, 30);
        g2.drawString("Press 'C' to Chi", 30, 50);
        g2.drawString("Press 'P' to Pung", 30, 70);
        g2.drawString("Press 'K' to Kong", 30, 90);
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
}
