package com.general_hello.commands.RPG.Items.Rare.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

public class Diamond extends Artifacts {
    public Diamond() {
        super(Rarity.RARE, false, "Diamond", AttainableBy.CRAFTING, "");
    }
}