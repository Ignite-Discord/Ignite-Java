package com.general_hello.commands.RPG.Items.Common.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Dog extends LandAnimals {
    public Dog() {
        super(Rarity.COMMON, 4, "Dog", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
