package br.com.developando.lootrestock.data;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LootInfo implements Serializable {
    private List<ItemData> itemData;
    private Long resetTime = 0L;
    private boolean onCooldown = false;
    public LootInfo(List<ItemStack> itemStacks, Long resetTime)   {
        this.resetTime = resetTime;
        this.itemData = new ArrayList<ItemData>();
        for (int i = 0; i < itemStacks.size(); i++)   {
            if (itemStacks.get(i) != null)   {
                itemData.add(new ItemData(itemStacks.get(i)));
            }
        }
    }
    public ItemStack[] getItemStack()   {
        ItemStack[] itemStacks = new ItemStack[itemData.size()];
        for (int i = 0; i < this.itemData.size(); i++)   {
            itemStacks[i] = this.itemData.get(i).getItemStack();
        }
        return itemStacks;
    }

    public List<ItemData> getItemData() {
        return itemData;
    }

    public void setItemData(List<ItemData> itemData) {
        this.itemData = itemData;
    }

    public Long getResetTime() {
        return resetTime;
    }

    public void setResetTime(Long resetTime) {
        this.resetTime = resetTime;
    }

    public boolean isOnCooldown() {
        return onCooldown;
    }

    public void setOnCooldown(boolean onCooldown) {
        this.onCooldown = onCooldown;
    }
}
