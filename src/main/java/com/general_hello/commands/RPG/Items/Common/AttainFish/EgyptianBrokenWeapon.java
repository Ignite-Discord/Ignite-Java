package com.general_hello.commands.RPG.Items.Common.AttainFish;

import com.general_hello.commands.RPG.Objects.Weapons;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class EgyptianBrokenWeapon extends Weapons {
    public EgyptianBrokenWeapon() {
        super(Rarity.COMMON, 0, "Egyptian's Broken Weapon", Level.LEVELONE, AttainableBy.FISHING, "");
    }
}
