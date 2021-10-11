package com.general_hello.commands.RPG.Items.Common.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Dove extends LandAnimals {
    public Dove() {
        super(Rarity.COMMON, 3, "Dove", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
