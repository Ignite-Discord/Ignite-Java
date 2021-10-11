package com.general_hello.commands.RPG.Items.Common.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Donkey extends LandAnimals {
    public Donkey() {
        super(Rarity.COMMON, 5, "Donkey", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
