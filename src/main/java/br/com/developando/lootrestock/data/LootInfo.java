package br.com.developando.lootrestock.data;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;

public class LootInfo {
    private ItemStack[] itemStacks;
    private Long resetTime = 0L;
    private boolean onCooldown = false;
    public LootInfo(ItemStack[] itemStacks)   {
        this.itemStacks = itemStacks;
    }
    public LootInfo(ItemStack[] itemStacks, Long resetTime)   {
        this.itemStacks = itemStacks;
        this.resetTime = resetTime;
    }
    public ItemStack[] getItemStacks()   {

        return this.itemStacks;
    }
    public void setItemStacks(ItemStack[] itemStacks)   {

        this.itemStacks = itemStacks;
    }
    public void setResetTime(Long resetTime)   {

        this.resetTime = resetTime;
    }
    public Long getResetTime()   {
        return this.resetTime;
    }
    public boolean isOnCooldown() {
        return onCooldown;
    }
    public void turnOnCooldown()   {
        this.onCooldown = true;
    }
    public void turnOffCooldown()   {

        this.onCooldown = false;
    }
}
