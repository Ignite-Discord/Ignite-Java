package com.general_hello.commands.RPG.Items.Rare.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Camel extends LandAnimals {
    public Camel() {
        super(Rarity.RARE, 10, "Camel", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
