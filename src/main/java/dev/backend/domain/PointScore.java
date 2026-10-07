package dev.backend.domain;

public enum PointScore {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE("AD"),
    GAME("GAME");

    private String display;
    PointScore(String display) {
        this.display = display;
    }

    public PointScore next() {
        return values()[ordinal() + 1];
    }

    @Override
    public String toString() {
        return this.display;
    }
}
