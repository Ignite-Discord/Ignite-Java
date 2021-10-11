package com.general_hello.commands.RPG.Items.Rare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class PufferFish extends SeaAnimals {
    public PufferFish() {
        super(Rarity.RARE, 9, "Puffer Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
