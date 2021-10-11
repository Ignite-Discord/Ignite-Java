package com.general_hello.commands.RPG.Items.Rare.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Lion extends LandAnimals {
    public Lion() {
        super(Rarity.RARE, 10, "Lion", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
