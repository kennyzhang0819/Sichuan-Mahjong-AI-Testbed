package application.gameframe;

import application.game.Game;
import model.GameState;
import application.config.Config;
import model.players.Player;
import model.basic.Tile;
import model.players.PlayerStatusEnum;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static utils.TileUtils.*;

public class GamePanel extends JPanel implements Runnable {

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    private final Game game;
    private final Player player;
    private Tile hoveredTile = null;
    private final TileImageLoader imageLoader = new TileImageLoader();
    private List<Tile> interactableTiles;

    public GamePanel() {
        this.game = new Game();
        this.player = this.game.getPlayers().get(0);

        this.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        this.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (hoveredTile != null && player.isPlaying() &&
                        !player.containsChouPungKong()) {
                    player.plays(hoveredTile);
                    hoveredTile = null;
                    game.processPlayed();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                List<Tile> hand = new ArrayList<Tile>() {{
                    this.addAll(interactableTiles);
                }};
                Tile newHoveredTile = getTileAt(e.getX(), e.getY(), hand);
                if (newHoveredTile != hoveredTile) {
                    hoveredTile = newHoveredTile;
                }
            }
        });
    }

    public void start() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void update() {
        if (this.game.isOver()) {
            this.gameThread = null;
        }
        if (keyHandler.hPressed && !keyHandler.hProcessed
                && player.getStatus().contains(PlayerStatusEnum.HU)) {
            this.game.processHu(player);
            keyHandler.hProcessed = true;
        }
        if (keyHandler.cPressed && !keyHandler.cProcessed
                && player.containsChow()) {
            this.game.processChou(player);
            keyHandler.cProcessed = true;
        }
        if (keyHandler.pPressed && !keyHandler.pProcessed
                && player.containsPung()) {
            this.game.processPung(player);
            keyHandler.pProcessed = true;
        }
        if (keyHandler.kPressed && !keyHandler.kProcessed
                && player.containsKong()) {
            this.game.processKong(player);
            keyHandler.kProcessed = true;
        }
        if (keyHandler.sPressed && !keyHandler.sProcessed
                && player.containsChouPungKong()) {
            this.game.processSkip(player);
            keyHandler.sProcessed = true;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Drawer drawer = new Drawer(g2, getWidth(), getHeight(), imageLoader);
        drawer.drawBackground();
        drawer.drawLogs(this.game.getLog().getLastXMessages(Config.LOG_ITEMS));
        drawer.drawHelperBoxes();

        for (Player p : game.getPlayers()) {
            drawer.drawTable(p.getTable().toList(), p.getPosition());
            drawer.drawPungKong(p.getHand().getPungKong(), p.getPosition());
        }
        interactableTiles = drawer.drawPlayerHand(player.getHand().toList(), player.getHand().getNewTile());
        if (hoveredTile != null) {
            drawer.drawTile(hoveredTile, Color.LIGHT_GRAY);
        }
        g2.dispose();
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
}
