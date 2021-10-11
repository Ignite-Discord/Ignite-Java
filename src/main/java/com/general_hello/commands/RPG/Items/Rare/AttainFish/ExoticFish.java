package com.general_hello.commands.RPG.Items.Rare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class ExoticFish extends SeaAnimals {
    public ExoticFish() {
        super(Rarity.RARE, 10, "Exotic Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
