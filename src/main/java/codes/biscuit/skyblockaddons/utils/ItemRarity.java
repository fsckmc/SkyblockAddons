package codes.biscuit.skyblockaddons.utils;

import java.util.List;

import main.java.codes.biscuit.skyblockaddons.utils.Rarity;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRarity {
    private ItemStack item;

    public ItemRarity(ItemStack item) {
        this.item = item;
    }

    public Rarity get() {
        String itemName = item.getDisplayName();
        for(Rarity rarity: Rarity.values()){
            if(itemName.contains(rarity.getRarity())) return rarity;
        }
        return Rarity.UNKNOWN;
    }
}