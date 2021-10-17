package com.general_hello.commands.RPG.Objects;

import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

public class Artifacts {
    private final Rarity rarity;
    private final boolean isCollection;
    private final String name;
    private final AttainableBy retrieveBy;
    private final String emoji;

    public Artifacts(Rarity rarity, boolean isCollection, String name, AttainableBy retrieveBy, String emoji) {
        this.rarity = rarity;
        this.isCollection = isCollection;
        this.name = name;
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

    public boolean getIsCollection() {
        return isCollection;
    }

    public String getName() {
        return name;
    }

    public boolean isCollection() {
        return isCollection;
    }
}
