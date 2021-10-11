package com.general_hello.commands.RPG.Items.Uncommon.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class BoxOfSand extends SeaAnimals {
    public BoxOfSand() {
        super(Rarity.UNCOMMON, 1, "Box of Sand", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
