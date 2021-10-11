package com.general_hello.commands.RPG.Items.Rare.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Serpent extends LandAnimals {
    public Serpent() {
        super(Rarity.RARE, 8, "Serpent", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}