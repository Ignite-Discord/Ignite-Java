package com.general_hello.commands.RPG.Items.Uncommon.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class PetersFish extends SeaAnimals {
    public PetersFish() {
        super(Rarity.UNCOMMON, 10, "Peter's Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
