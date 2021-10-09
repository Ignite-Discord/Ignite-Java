package com.general_hello.commands.RPG.Items.Common;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class FishCommon extends SeaAnimals {
    public FishCommon() {
        super(Rarity.COMMON, 5, "Common Fish", Level.LEVELONE, AttainableBy.FISHING);
    }
}
