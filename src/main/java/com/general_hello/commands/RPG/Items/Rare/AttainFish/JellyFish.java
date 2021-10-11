package com.general_hello.commands.RPG.Items.Rare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class JellyFish extends SeaAnimals {
    public JellyFish() {
        super(Rarity.RARE, 8, "Jelly Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
