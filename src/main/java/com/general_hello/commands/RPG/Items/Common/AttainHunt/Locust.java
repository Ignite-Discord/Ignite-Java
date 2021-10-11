package com.general_hello.commands.RPG.Items.Common.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Locust extends LandAnimals {
    public Locust() {
        super(Rarity.COMMON, 1, "Locust", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
