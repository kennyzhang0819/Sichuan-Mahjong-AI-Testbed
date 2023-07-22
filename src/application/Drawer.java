package application;

import model.log.Log;
import model.tiles.Tile;

import java.awt.*;
import java.util.List;

public class Drawer {

    private final Graphics2D g2;
    private final int width;
    private final int height;

    public Drawer(Graphics2D g2, int width, int height) {
        this.g2 = g2;
        this.width = width;
        this.height = height;
    }

    public void drawTile(Tile tile) {
        Image image = tile.getImage();
        Image scaled = image.getScaledInstance(tile.width, tile.height, Image.SCALE_SMOOTH);

        g2.setColor(Color.WHITE);
        g2.fillRect(tile.x, tile.y, tile.width, tile.height);
        g2.drawString(String.valueOf(tile.getIndex() + 1), tile.x, tile.y + tile.height + 20);
        g2.drawImage(scaled, tile.x, tile.y, null);
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
        g2.setColor(Color.WHITE); // Choose a color for the text
        int lineHeight = g2.getFontMetrics().getHeight(); // The height of a line of text
        int padding = 20; // Padding around the log window
        int logWindowHeight = logs.size() * lineHeight + padding * 2; // Height of the log window
        int logWindowY = this.height - logWindowHeight; // Y coordinate of the log window

        g2.fillRect(padding, logWindowY, this.width - padding * 2, logWindowHeight); // Draw the log window
        g2.setColor(Color.BLACK); // Change color for the text

        // Draw each log line
        for (int i = 0; i < logs.size(); i++) {
            String log = logs.get(i);
            g2.drawString(log, padding, logWindowY + padding + i * lineHeight);
        }
    }
}
