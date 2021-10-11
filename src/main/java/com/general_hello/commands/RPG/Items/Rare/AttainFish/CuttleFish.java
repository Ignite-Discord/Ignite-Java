package com.general_hello.commands.RPG.Items.Rare.AttainFish;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class CuttleFish extends SeaAnimals {
    public CuttleFish() {
        super(Rarity.RARE, 8, "Cuttle Fish", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
