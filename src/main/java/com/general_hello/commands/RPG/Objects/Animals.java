package com.general_hello.commands.RPG.Objects;

import com.general_hello.commands.RPG.Items.Uncommon.FishingPole;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Animals {
    private final Rarity rarity;
    private final int rewardForCooking;
    private final String name;
    private Level level;
    private final AttainableBy retrieveBy;
    private final Tools toolsToUse;
    private final String emojiOfItem;

    public Animals(Rarity rarity, int rewardForCooking, String name, Level level, AttainableBy retrieveBy, String emojiOfItem) {
        this.rarity = rarity;
        this.rewardForCooking = rarity.getMultipliedValue(rewardForCooking);
        this.name = name;
        this.level = level;
        this.retrieveBy = retrieveBy;
        // TODO: 11/10/2021 FIX THIS
        this.toolsToUse = new FishingPole();
        this.emojiOfItem = emojiOfItem;
    }

    public AttainableBy getRetrieveBy() {
        return retrieveBy;
    }

    public Level getLevel() {
        return level;
    }

    public Animals setLevel(Level level) {
        this.level = level;
        return this;
    }

    public String getEmojiOfItem() {
        return emojiOfItem;
    }

    public Tools getToolsToUse() {
        return toolsToUse;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getRewardForCooking() {
        return rewardForCooking;
    }

    public String getName() {
        return name;
    }
}
