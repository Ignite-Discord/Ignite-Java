package com.general_hello.commands.RPG.Items.Uncommon.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Eagle extends LandAnimals {
    public Eagle() {
        super(Rarity.UNCOMMON, 5, "Eagle", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
