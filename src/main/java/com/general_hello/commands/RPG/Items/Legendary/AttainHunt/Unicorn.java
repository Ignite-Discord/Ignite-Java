package com.general_hello.commands.RPG.Items.Legendary.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Unicorn extends LandAnimals {
    public Unicorn() {
        super(Rarity.LEGENDARY, 20, "Unicorn", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
