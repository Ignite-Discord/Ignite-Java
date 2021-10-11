package com.general_hello.commands.RPG.Items.Common.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Gnat extends LandAnimals {
    public Gnat() {
        super(Rarity.COMMON, 1, "Gnat", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
