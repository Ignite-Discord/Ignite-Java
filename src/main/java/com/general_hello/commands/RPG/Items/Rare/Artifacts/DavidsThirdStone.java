package com.general_hello.commands.RPG.Items.Rare.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

import java.util.ArrayList;

public class DavidsThirdStone extends Artifacts {
    private final String collectionName;
    private final ArrayList<Artifacts> collectionNeeded = new ArrayList<>();
    public DavidsThirdStone() {
        super(Rarity.RARE, true, "David's Third Stone", AttainableBy.CRAFTING, "");
        collectionName = "David's Stones";
        collectionNeeded.add(new DavidsThirdStone());
    }

    public String getCollectionName() {
        return collectionName;
    }

    public ArrayList<Artifacts> getCollectionNeeded() {
        return collectionNeeded;
    }
}