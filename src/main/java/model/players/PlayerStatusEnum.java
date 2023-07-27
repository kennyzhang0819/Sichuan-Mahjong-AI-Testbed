package model.players;

public enum PlayerStatusEnum {

    HU("可胡"),
    CHOW("可吃"),
    PUNG("可碰"),
    HIDDEN_KONG("可暗杠"),
    ADD_KONG("可加杠"),
    NORMAL_KONG("可普通杠"),
    WIN("胡"),
    WAITING("等待"),
    PLAYING("出牌");

    private final String chinese;

    PlayerStatusEnum(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
}
