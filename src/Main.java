import application.GamePanel;
import config.Config;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Mahjong");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        gamePanel.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        window.add(gamePanel);
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel.start();
    }

}