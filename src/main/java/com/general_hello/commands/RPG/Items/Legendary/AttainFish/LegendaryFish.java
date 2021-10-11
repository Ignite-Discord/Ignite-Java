package com.general_hello.commands.RPG.Items.Legendary.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class LegendaryFish extends SeaAnimals {
    public LegendaryFish() {
        super(Rarity.LEGENDARY, 20, "Legendary Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
