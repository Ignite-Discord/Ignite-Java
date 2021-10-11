package com.general_hello.commands.RPG.Items.Uncommon.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Cow extends LandAnimals {
    public Cow() {
        super(Rarity.UNCOMMON, 10, "Cow", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
