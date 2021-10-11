package com.general_hello.commands.RPG.Items.Rare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Squid extends SeaAnimals {
    public Squid() {
        super(Rarity.RARE, 10, "Squid", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
