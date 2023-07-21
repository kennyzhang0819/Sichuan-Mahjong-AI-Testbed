package application;

import model.Player;
import model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import static application.TileUtils.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int originalTileSize = 16;
    public static final int TILESIZE = originalTileSize * 4;
    public static final int LEFTINDENT = 200;
    public static final int TOPINDENT = 650;
    final int maxScreenColumns = 25;
    final int maxScreenRows = 15;
    final int screenWidth = TILESIZE * maxScreenColumns;
    final int screenHeight = TILESIZE * maxScreenRows;


    Thread gameThread;
    private final List<Tile> tilesToDraw;
    private Tile hoveredTile = null;
    private Player player;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        Game game = new Game();
        this.player = game.getPlayers().get(0);
        game.shuffleTiles();
        game.deal();
        tilesToDraw = player.getHand();

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
                Tile newHoveredTile = getTileAt(e.getX(), e.getY(), tilesToDraw);

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



    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            try {
                Thread.sleep(100);
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
