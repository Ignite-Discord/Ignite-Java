package com.general_hello.commands.RPG.Items.Uncommon.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Sheep extends LandAnimals {
    public Sheep() {
        super(Rarity.UNCOMMON, 5, "Sheep", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
