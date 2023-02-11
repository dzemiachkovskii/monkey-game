package org.phgdzlk.grippy_ape;

public enum MenuChoice {
    EASY(3),
    MEDIUM(4),
    HARD(6),
    QUIT(-1);
    private final int option;

    MenuChoice(int option) {
        this.option = option;
    }

    public int get() {
        return option;
    }
}
