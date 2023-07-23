package model.basic;

public enum TileTypeEnum {
    B("Bamboo", "条"),
    C("Character", "万"),
    D("Dot", "筒");

    private final String english;
    private final String chinese;

    TileTypeEnum(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }

    public String getEnglish() {
        return english;
    }
}
