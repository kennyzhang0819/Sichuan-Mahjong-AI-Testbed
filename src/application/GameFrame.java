package application;

import config.Config;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        // Create the layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));

        // Create the game panel
        GamePanel gamePanel = new GamePanel();
        gamePanel.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        JButton button1 = createButton("Button 1");
        JButton button2 = createButton("Button 2");
        JButton button3 = createButton("Button 3");
        JButton button4 = createButton("Button 4");
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        buttonPanel.setOpaque(false);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> System.out.println(text + " clicked"));
        return button;
    }

}
