package model.basic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Tile extends Entity {
    private int index;
    private TileCategoryEnum category;
    private int number;
    private BufferedImage image;

    public Tile(TileCategoryEnum category, int number) {
        super(0,0,0,0);
        this.category = category;
        this.number = number;
        try {
            String path = "C:\\Users\\DELL\\Desktop\\Mahjong\\MahjongGame\\img\\" +
                    category + "\\0" + number + ".png";
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TileCategoryEnum getCategory() {
        return category;
    }

    public int getNumber() {
        return number;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return category + " " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return number == tile.number && Objects.equals(category, tile.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, number);
    }
}
