package com.general_hello.commands.RPG.Items.Legendary.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Leviathan extends SeaAnimals {
    public Leviathan() {
        super(Rarity.LEGENDARY, 20, "Leviathan", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
