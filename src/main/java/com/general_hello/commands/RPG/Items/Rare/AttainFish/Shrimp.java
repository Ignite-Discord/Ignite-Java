package com.general_hello.commands.RPG.Items.Rare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Shrimp extends SeaAnimals {
    public Shrimp() {
        super(Rarity.RARE, 11, "Shrimp", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
