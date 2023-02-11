package org.phgdzlk.grippy_ape;

public enum Difficulty {
    UNDEFINED(0),
    EASY(3),
    MEDIUM(4),
    HARD(6);
    private final int difficulty;

    Difficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
