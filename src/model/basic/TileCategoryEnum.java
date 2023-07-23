package model.basic;

public enum TileCategoryEnum {
    BAMBOO("Bamboo", "条"),
    CHARACTER("Character", "万"),
    DOT("Dot", "筒");

    private final String english;
    private final String chinese;

    TileCategoryEnum(String english, String chinese) {
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
