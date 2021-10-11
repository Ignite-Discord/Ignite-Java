package com.general_hello.commands.RPG.Items.Uncommon.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Sparrow extends LandAnimals {
    public Sparrow() {
        super(Rarity.UNCOMMON, 5, "Sparrow", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
