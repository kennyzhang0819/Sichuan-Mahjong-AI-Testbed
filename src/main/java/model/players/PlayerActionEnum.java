package model.players;

public enum PlayerActionEnum {

    HU("胡"),
    CHOW("吃"),
    PUNG("碰"),
    KONG("杠"),
    SKIP("跳过");


    private final String chinese;

    PlayerActionEnum(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
}
