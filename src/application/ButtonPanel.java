package application;

import javax.swing.*;

public class ButtonPanel extends JPanel {

    public ButtonPanel() {
        super();
        this.setLayout(null); // Use null layout for absolute positioning

        JButton button1 = createButton("Hu");
        button1.setBounds(300, 800, 100, 30);
        button1.addActionListener(e -> {
            System.out.println("Hu");
        });
        JButton button2 = createButton("Chi");
        button2.setBounds(500, 800, 100, 30);
        JButton button3 = createButton("Pung");
        button3.setBounds(700, 800, 100, 30);
        JButton button4 = createButton("Kong");
        button4.setBounds(900, 800, 100, 30);

        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
    }

    private static JButton createButton(String text) {
        return new JButton(text);
    }
}
