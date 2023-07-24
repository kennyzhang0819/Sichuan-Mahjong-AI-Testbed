package application;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameButton extends JButton {

    public GameButton(String buttonText, ActionListener actionListener) {
        super(buttonText);
        addActionListener(actionListener);
    }
}
