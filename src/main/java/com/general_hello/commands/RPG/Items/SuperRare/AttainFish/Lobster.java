package com.general_hello.commands.RPG.Items.SuperRare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Lobster extends SeaAnimals {
    public Lobster() {
        super(Rarity.SUPERRARE, 15, "Lobster", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
