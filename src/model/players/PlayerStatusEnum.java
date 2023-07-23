package model.players;

public enum PlayerStatusEnum {

    HU("可胡"),
    CHOW("可吃"),
    PUNG("可碰"),
    KONG("可杠"),
    WIN("胡"),
    WAIT("等待"),
    PLAY("出牌");

    private final String chinese;

    PlayerStatusEnum(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
}
