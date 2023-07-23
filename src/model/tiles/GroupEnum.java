package model.tiles;

public enum GroupEnum {

    SEQUENCE("顺子"),
    TRIPLE("刻子"),
    PAIR("对子"),
    PUNG("碰"),
    KONG("杠");

    private final String chinese;

    GroupEnum(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
}
