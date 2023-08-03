import application.gameframe.GamePanel;
import application.config.Config;
import com.github.esrrhs.majiang_algorithm.AITable;
import com.github.esrrhs.majiang_algorithm.AITableFeng;
import com.github.esrrhs.majiang_algorithm.AITableJian;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        AITableJian.load(Files.readAllLines(Paths.get("C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\Mahjong-Game-Java\\probability\\majiang_ai_jian.txt")));
        AITableFeng.load(Files.readAllLines(Paths.get("C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\Mahjong-Game-Java\\probability\\majiang_ai_feng.txt")));
        AITable.load(Files.readAllLines(Paths.get("C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\Mahjong-Game-Java\\probability\\majiang_ai_normal.txt")));
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