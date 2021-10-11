package com.general_hello.commands.RPG.Items.Uncommon.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class FishUncommon extends SeaAnimals {
    public FishUncommon() {
        super(Rarity.UNCOMMON, 5, "Uncommon Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
