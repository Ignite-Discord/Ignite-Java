package com.general_hello.commands.RPG.Types;

public enum Level {
    LEVELONE(1),
    LEVELTWO(2),
    LEVELTHREE(3),
    LEVELFOUR(4),
    LEVELFIVE(5);
    private final double multiplier;

    Level(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public int getMultipliedValue(int valueToBeMultiplied) {
        return (int) (valueToBeMultiplied * getMultiplier());
    }
}
