package com.general_hello.commands.RPG.Types;

public enum Rarity {
    COMMON(1),
    UNCOMMON(2),
    RARE(3),
    SUPERRARE(4),
    LEGENDARY(5);
    private final double multiplier;

    Rarity(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public int getMultipliedValue(int valueToBeMultiplied) {
        return (int) (valueToBeMultiplied * getMultiplier());
    }
}
