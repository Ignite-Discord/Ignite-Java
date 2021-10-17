package com.general_hello.commands.RPG.Items.Legendary.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

import java.util.ArrayList;

public class SixthCommandment extends Artifacts {
    private final String collectionName;
    private final ArrayList<Artifacts> collectionNeeded = new ArrayList<>();
    public SixthCommandment() {
        super(Rarity.LEGENDARY, true, "Sixth Commandment", AttainableBy.CRAFTING, "");
        collectionName = "The Ten Commandments";
        collectionNeeded.add(new FirstCommandment());
        collectionNeeded.add(new SecondCommandment());
        collectionNeeded.add(new ThirdCommandment());
        collectionNeeded.add(new FourthCommandment());
        collectionNeeded.add(new FifthCommandment());
        collectionNeeded.add(new SixthCommandment());
        collectionNeeded.add(new SeventhCommandment());
        collectionNeeded.add(new EightCommandment());
        collectionNeeded.add(new NinthCommandment());
        collectionNeeded.add(new TenthCommandment());
    }

    public String getCollectionName() {
        return collectionName;
    }

    public ArrayList<Artifacts> getCollectionNeeded() {
        return collectionNeeded;
    }
}