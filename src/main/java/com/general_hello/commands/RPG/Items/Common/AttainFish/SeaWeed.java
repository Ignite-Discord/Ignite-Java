package com.general_hello.commands.RPG.Items.Common.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class SeaWeed extends SeaAnimals {
    public SeaWeed() {
        super(Rarity.COMMON, 4, "Seaweed", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
