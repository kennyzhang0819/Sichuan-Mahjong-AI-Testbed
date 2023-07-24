import application.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Mahjong");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mahjong");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.start();
    }

    private static JPanel createButtonPanel() {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Use null layout for absolute positioning

        JButton button1 = createButton("Play"); // Set your own text
        button1.setBounds(300, 800, 100, 50); // Set your own position and size
        JButton button2 = createButton("Pause"); // Set your own text
        button2.setBounds(500, 800, 100, 50); // Set your own position and size
        JButton button3 = createButton("Stop"); // Set your own text
        button3.setBounds(700, 800, 100, 50); // Set your own position and size
        JButton button4 = createButton("Reset"); // Set your own text
        button4.setBounds(900, 800, 100, 50); // Set your own position and size

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        return buttonPanel;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> System.out.println(text + " clicked"));
        return button;
    }
}