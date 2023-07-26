package application.gameframe;

import config.Config;
import model.basic.Tile;
import model.basic.TileTypeEnum;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TileImageLoader {
    private final Map<TileTypeEnum, Map<Integer, Image>> images = new HashMap<>();
    private final Map<TileTypeEnum, Map<Integer, Image>> tableImages = new HashMap<>();

    public TileImageLoader() {
        for (TileTypeEnum type : TileTypeEnum.values()) {
            Map<Integer, Image> typeImages = new HashMap<>();
            Map<Integer, Image> tableTypeImages = new HashMap<>();
            for (int number = 1; number <= 9; number++) {
                String path = "C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\Mahjong-Game-Java\\img\\" +
                        type.getEnglish() + "\\0" + number + ".png";
                try {
                    Image image = ImageIO.read(new File(path));
                    Image scaled = image.getScaledInstance(Config.TILE_WIDTH, (int) Config.TILE_HEIGHT, Image.SCALE_SMOOTH);
                    typeImages.put(number, scaled);

                    Image tableScaled = image.getScaledInstance((int) Config.TABLE_TILE_WIDTH, (int) Config.TABLE_TILE_HEIGHT, Image.SCALE_SMOOTH);
                    tableTypeImages.put(number, tableScaled);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            images.put(type, typeImages);
            tableImages.put(type, tableTypeImages);
        }
    }

    public Image getImage(Tile tile) {
        return images.get(tile.getType()).get(tile.getNumber());
    }

    public Image getTableImage(Tile tile) {
        return tableImages.get(tile.getType()).get(tile.getNumber());
    }
}
