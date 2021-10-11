package com.general_hello.commands.RPG.Items.SuperRare.AttainHunt;

import com.general_hello.commands.RPG.Objects.LandAnimals;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Level;
import com.general_hello.commands.RPG.Types.Rarity;

public class Dinosaur extends LandAnimals {
    public Dinosaur() {
        super(Rarity.SUPERRARE, 20, "Dinosaur", Level.LEVELONE, AttainableBy.HUNTING, "");
    }
}
