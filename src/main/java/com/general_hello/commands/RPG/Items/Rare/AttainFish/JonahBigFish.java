package com.general_hello.commands.RPG.Items.Rare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class JonahBigFish extends SeaAnimals {
    public JonahBigFish() {
        super(Rarity.RARE, 15, "Jonah's Big Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
