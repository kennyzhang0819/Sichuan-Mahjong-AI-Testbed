import com.github.esrrhs.majiang_algorithm.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestAI {
    public static void main(String[] args) throws IOException {
        AITableJian.load(Files.readAllLines(Paths.get("C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\Mahjong-Game-Java\\probability\\majiang_ai_jian.txt")));
        AITableFeng.load(Files.readAllLines(Paths.get("C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\Mahjong-Game-Java\\probability\\majiang_ai_feng.txt")));
        AITable.load(Files.readAllLines(Paths.get("C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\Mahjong-Game-Java\\probability\\majiang_ai_normal.txt")));

        List<Integer> cards = new ArrayList<Integer>() {{
            add(MaJiangDef.toCard(1, 0));
            add(MaJiangDef.toCard(1, 0));
            add(MaJiangDef.toCard(1, 2));
            add(MaJiangDef.toCard(1, 3));
            add(MaJiangDef.toCard(1, 4));
            add(MaJiangDef.toCard(1, 4));
            add(MaJiangDef.toCard(1, 5));
            add(MaJiangDef.toCard(2, 1));
            add(MaJiangDef.toCard(2, 1));
            add(MaJiangDef.toCard(2, 4));
            add(MaJiangDef.toCard(2, 5));
            add(MaJiangDef.toCard(2, 6));
            add(MaJiangDef.toCard(2, 7));
            add(MaJiangDef.toCard(2, 8));
        }};
        List<Integer> gui = new ArrayList<>();
        System.out.println(MaJiangDef.cardsToString(cards));

        int card = AIUtil.outAI(cards, gui);
        System.out.println(MaJiangDef.cardToString(card));


        boolean isPeng = AIUtil.pengAI(cards, gui, MaJiangDef.toCard(1, 0), 0.00);
        boolean isGang = AIUtil.gangAI(cards, gui, MaJiangDef.toCard(1, 0), 0.00);
        List<Integer> isChi = AIUtil.chiAI(cards, gui, MaJiangDef.toCard(1, 2));
        System.out.println("Peng: " + isPeng + ", Gang: " + isGang + ", Chi: " + isChi);
    }
}
