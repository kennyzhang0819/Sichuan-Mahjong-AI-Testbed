package application;

import application.core.GameTurn;
import application.core.RoundData;
import config.Config;
import model.players.Player;
import model.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import static utils.TileUtils.*;

public class GamePanel extends JPanel implements Runnable {

    private Thread gameThread;
    private Game game;
    private RoundData roundData;
    private Player player;
    private Tile hoveredTile = null;

    public GamePanel() {
        setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        this.game = new Game();
        this.player = this.game.getPlayers().get(0);
        this.roundData = this.game.next();


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (hoveredTile != null) {
                    player.plays(hoveredTile);
                    hoveredTile = null;
                    repaint();
                    for (int i = 0; i < 4; i++) {
                        roundData = game.next();
                        System.out.println(roundData.getTurnPlayer().getName());
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Tile newHoveredTile = getTileAt(e.getX(), e.getY(), roundData.getPlayerHand());

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
        Drawer drawer = new Drawer(g2, getWidth(), getHeight());
        drawer.drawBackground();
        drawer.drawLogs(this.game.getLog().getLastXMessages(10));
        for (Tile tile : roundData.getTilesToDraw()) {
            drawer.drawTile(tile);
        }
        g2.dispose();
    }






}
