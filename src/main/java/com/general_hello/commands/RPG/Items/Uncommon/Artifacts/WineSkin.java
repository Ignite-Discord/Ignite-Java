package com.general_hello.commands.RPG.Items.Uncommon.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

import java.util.ArrayList;

public class WineSkin extends Artifacts {
    public WineSkin() {
        super(Rarity.UNCOMMON, false, "Wine Skin", "", new ArrayList<>(), AttainableBy.CRAFTING, "");
    }
}