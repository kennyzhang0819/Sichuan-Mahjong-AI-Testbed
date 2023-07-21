package application;

import model.Tiles.Tile;
import config.Config;
import model.Player;
import model.Tiles.Tiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import static utils.TileUtils.*;

public class GamePanel extends JPanel implements Runnable {

    Thread gameThread;
    private final List<Tile> tilesToDraw;
    private Tile hoveredTile = null;
    private Player player;

    public GamePanel() {
        setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        Game game = new Game();
        this.player = game.getPlayers().get(0);
        game.shuffleTiles();
        game.deal();
        Tiles playerHand = player.getHand();
        List<Tile> playerHandList = playerHand.toList();
        tilesToDraw = playerHandList;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (hoveredTile != null) {
                    player.plays(hoveredTile);
                    game.update(hoveredTile);
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Check if the mouse is over a tile
                Tile newHoveredTile = getTileAt(e.getX(), e.getY(), playerHandList);

                if (newHoveredTile != hoveredTile) {
                    if (hoveredTile != null) {
                        moveTileDown(hoveredTile);
                    }
                    if (newHoveredTile != null) {
                        moveTileUp(newHoveredTile);
                    }
                    hoveredTile = newHoveredTile;
                    repaint();
                }
            }
        });
    }


    @Override
    public void run() {
        double interval = 1000000000.0 / Config.FPS;
        double nextTime = System.nanoTime() + interval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double wait = (nextTime - System.nanoTime()) / 1000000;
                if (wait < 0) {
                    wait = 0;
                }
                Thread.sleep((long) wait);
                nextTime += interval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Tile tile : tilesToDraw) {
            this.drawTile(tile, g2);
        }
        g2.dispose();
    }

    public void drawTile(Tile tile, Graphics2D g2) {
        Image image = tile.getImage();
        Image scaled = image.getScaledInstance(tile.width, tile.height, Image.SCALE_SMOOTH);

        g2.setColor(Color.WHITE);
        g2.fillRect(tile.x, tile.y, tile.width, tile.height);
        g2.drawString(String.valueOf(tile.getIndex() + 1), tile.x, tile.y + tile.height + 20);
        g2.drawImage(scaled, tile.x, tile.y, null);
    }


}
