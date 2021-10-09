package com.general_hello.commands.RPG.Objects;

import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class SeaAnimals {
    private final Rarity rarity;
    private final int rewardForCooking;
    private final String name;
    private Level level;
    private final AttainableBy retrieveBy;

    public SeaAnimals(Rarity rarity, int rewardForCooking, String name, Level level, AttainableBy retrieveBy) {
        this.rarity = rarity;
        this.rewardForCooking = rarity.getMultipliedValue(rewardForCooking);
        this.name = name;
        this.level = level;
        this.retrieveBy = retrieveBy;
    }

    public AttainableBy getRetrieveBy() {
        return retrieveBy;
    }

    public Level getLevel() {
        return level;
    }

    public SeaAnimals setLevel(Level level) {
        this.level = level;
        return this;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getRewardForCooking() {
        return rewardForCooking;
    }

    public String getName() {
        return name;
    }
}
