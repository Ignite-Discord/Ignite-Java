package com.general_hello.commands.RPG.Items.Uncommon.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

import java.util.ArrayList;

public class AmmoriteOldWeapon extends Artifacts {
    public AmmoriteOldWeapon() {
        super(Rarity.UNCOMMON, false, "Wine Skin", "", new ArrayList<>(), AttainableBy.CRAFTING, "");
    }
}