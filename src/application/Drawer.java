package application;

import model.Entity;
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
        g2.fillRect((int) tile.x, (int) tile.y, tile.width, tile.height);
        g2.drawString(String.valueOf(tile.getIndex() + 1),(int) tile.x,(int) tile.y + tile.height + 20);
        g2.drawImage(scaled,(int) tile.x,(int) tile.y, null);
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



        // Draw each log line
        for (int i = 0; i < logs.size(); i++) {
            String log = logs.get(i);
            g2.drawString(log, logWindowX + textPadding, padding + textPadding + i * lineHeight);
        }
    }

    public void drawRect(Entity entity) {
        g2.setColor(Color.WHITE);
        g2.drawRect((int) entity.x, (int) entity.y, entity.width, entity.height);
    }

}
