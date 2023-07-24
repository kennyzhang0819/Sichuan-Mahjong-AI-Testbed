package application;

import application.core.Game;
import application.core.validation.PlayerStatusChecker;
import model.OutputData;
import config.Config;
import model.basic.Entity;
import model.players.Player;
import model.basic.Tile;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static utils.TileUtils.*;

public class GamePanel extends JPanel implements Runnable {

    Thread gameThread;
    private Game game;
    private PlayerStatusChecker checker;
    private OutputData outputData;
    private Player player;
    private Tile hoveredTile = null;
    private Entity playerHand;
    private Entity playerTable;
    private Entity ai1Table;
    private Entity ai2Table;
    private Entity ai3Table;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    public GamePanel() {
        setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        this.game = new Game();
        this.player = this.game.getPlayers().get(0);
        this.outputData = this.game.next();
        this.initBoxes();
        this.initButtons();


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (hoveredTile != null) {
                    player.plays(hoveredTile);
                    hoveredTile = null;
                    for (int i = 0; i < 4; i++) {
                        outputData = game.next();
                    }
                    this.mouseMoved(e);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Tile newHoveredTile = getTileAt(e.getX(), e.getY(), new ArrayList<Tile>() {{
                    this.addAll(outputData.getPlayerHand());
                    this.add(outputData.getPlayerNewTile());
                }});
                if (newHoveredTile != hoveredTile) {
                    if (hoveredTile != null) {
                        moveTileDown(hoveredTile);
                    }
                    if (newHoveredTile != null) {
                        moveTileUp(newHoveredTile);
                    }
                    hoveredTile = newHoveredTile;
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 1 was clicked!");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 2 was clicked!");
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 3 was clicked!");
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 4 was clicked!");
            }
        });
        add(button1);
        add(button2);
        add(button3);
        add(button4);

    }

    public void initBoxes() {
        this.playerHand = new Entity(Config.PLAYER_HAND_X, Config.PLAYER_HAND_Y, Config.PLAYER_HAND_WIDTH, Config.PLAYER_HAND_HEIGHT);
        this.playerTable = new Entity(Config.PLAYER_TABLE_X, Config.PLAYER_TABLE_Y, Config.PLAYER_TABLE_WIDTH, Config.PLAYER_TABLE_HEIGHT);
        this.ai1Table = new Entity(Config.AI1_TABLE_X, Config.AI1_TABLE_Y, Config.AI1_TABLE_WIDTH, Config.AI1_TABLE_HEIGHT);
        this.ai2Table = new Entity(Config.AI2_TABLE_X, Config.AI2_TABLE_Y, Config.AI2_TABLE_WIDTH, Config.AI2_TABLE_HEIGHT);
        this.ai3Table = new Entity(Config.AI3_TABLE_X, Config.AI3_TABLE_Y, Config.AI3_TABLE_WIDTH, Config.AI3_TABLE_HEIGHT);
    }

    public void initButtons() {
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Button 3");
        button4 = new JButton("Button 4");
    }

    public void start() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
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
        if (this.game.isOver()) {
            this.gameThread = null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Drawer drawer = new Drawer(g2, getWidth(), getHeight());
        drawer.drawBackground();
        drawer.drawLogs(this.game.getLog().getLastXMessages(39));
        drawer.drawRect(playerHand);
        drawer.drawRect(playerTable);
        drawer.drawRect(ai1Table);
        drawer.drawRect(ai2Table);
        drawer.drawRect(ai3Table);
        for (Tile tile : outputData.getTilesToDraw()) {
            drawer.drawTile(tile);
        }

        g2.dispose();
    }






}
