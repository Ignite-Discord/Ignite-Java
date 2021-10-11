package com.general_hello.commands.RPG.Items.Legendary.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Kraken extends SeaAnimals {
    public Kraken() {
        super(Rarity.LEGENDARY, 20, "Kraken", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
