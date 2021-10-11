package com.general_hello.commands.RPG.Objects;

import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

public class Tools {
    private final Rarity rarity;
    private final AttainableBy toBeUsedOn;
    private final String name;
    private final AttainableBy retrieveBy;
    private final String emoji;


    public Tools(Rarity rarity, AttainableBy toBeUsedOn, String name, AttainableBy retrieveBy, String emoji) {
        this.rarity = rarity;
        this.name = name;
        this.toBeUsedOn = toBeUsedOn;
        this.retrieveBy = retrieveBy;
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

    public AttainableBy getRetrieveBy() {
        return retrieveBy;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public AttainableBy getToBeUsedOn() {
        return toBeUsedOn;
    }

    public String getName() {
        return name;
    }
}
