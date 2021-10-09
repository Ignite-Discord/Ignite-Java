package com.general_hello.commands.RPG.Items.Common;

import com.general_hello.commands.RPG.Objects.SeaAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class EgyptianBrokenWeapon extends SeaAnimals {
    public EgyptianBrokenWeapon() {
        super(Rarity.COMMON, 0, "Egyptian's Broken Weapon", Level.LEVELONE, AttainableBy.FISHING);
    }
}
