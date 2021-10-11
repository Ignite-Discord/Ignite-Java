package com.general_hello.commands.RPG.Items.Uncommon;

import com.general_hello.commands.RPG.Objects.Tools;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

public class FishingPole extends Tools {
    public FishingPole() {
        super(Rarity.UNCOMMON, AttainableBy.FISHING, "Fishing Pole", AttainableBy.CRAFTING, "");
    }
}
