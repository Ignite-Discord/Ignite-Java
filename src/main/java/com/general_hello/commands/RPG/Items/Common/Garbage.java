package com.general_hello.commands.RPG.Items.Common;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Garbage extends SeaAnimals {
    public Garbage() {
        super(Rarity.COMMON, 1, "Garbage", Level.LEVELONE, AttainableBy.FISHING);
    }
}
