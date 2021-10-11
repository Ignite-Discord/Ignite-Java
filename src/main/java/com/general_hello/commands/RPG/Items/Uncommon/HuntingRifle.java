package com.general_hello.commands.RPG.Items.Uncommon;

import com.general_hello.commands.RPG.Objects.Tools;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

public class HuntingRifle extends Tools {
    public HuntingRifle() {
        super(Rarity.UNCOMMON, AttainableBy.FISHING, "Hunting Riffle", AttainableBy.CRAFTING, "");
    }
}
