package com.general_hello.commands.RPG.Objects;

import com.general_hello.commands.RPG.Items.Uncommon.HuntingRifle;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class LandAnimals extends Animals {
    private final Tools toolsToUse;

    public LandAnimals(Rarity rarity, int rewardForCooking, String name, Level level, AttainableBy retrieveBy, String emoji) {
        super(rarity, rewardForCooking, name, level, retrieveBy, emoji);
        // TODO: 10/10/2021 Fix the level here
        this.toolsToUse = new HuntingRifle();
    }

    @Override
    public Tools getToolsToUse() {
        return toolsToUse;
    }
}
