package com.general_hello.commands.RPG.Items.Common.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

import java.util.ArrayList;

public class SackCloth extends Artifacts {
    public SackCloth() {
        super(Rarity.COMMON, false, "Sack Cloth", "", new ArrayList<>(), AttainableBy.CRAFTING, "");
    }
}