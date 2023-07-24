import application.ButtonPanel;
import application.GamePanel;
import config.Config;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Mahjong");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));

        GamePanel gamePanel = new GamePanel();
        gamePanel.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        JPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        buttonPanel.setOpaque(false);

        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        window.add(layeredPane);
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel.start();
    }

}