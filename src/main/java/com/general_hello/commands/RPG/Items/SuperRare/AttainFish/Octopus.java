package com.general_hello.commands.RPG.Items.SuperRare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Octopus extends SeaAnimals {
    public Octopus() {
        super(Rarity.SUPERRARE, 15, "Octopus", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
