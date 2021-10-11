package com.general_hello.commands.RPG.Objects;

import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Weapons {
    private final Rarity rarity;
    private final int damage;
    private final String name;
    private Level level;
    private final AttainableBy retrieveBy;
    private final String emoji;

    public Weapons(Rarity rarity, int damage, String name, Level level, AttainableBy retrieveBy, String emoji) {
        this.rarity = rarity;
        this.damage = level.getMultipliedValue(rarity.getMultipliedValue(damage));
        this.name = name;
        this.level = level;
        this.retrieveBy = retrieveBy;
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

    public AttainableBy getRetrieveBy() {
        return retrieveBy;
    }

    public Level getLevel() {
        return level;
    }

    public Weapons setLevel(Level level) {
        this.level = level;
        return this;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}
