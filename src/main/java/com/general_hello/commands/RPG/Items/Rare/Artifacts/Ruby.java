package com.general_hello.commands.RPG.Items.Rare.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

public class Ruby extends Artifacts {
    public Ruby() {
        super(Rarity.RARE, false, "Ruby", AttainableBy.CRAFTING, "");
    }
}