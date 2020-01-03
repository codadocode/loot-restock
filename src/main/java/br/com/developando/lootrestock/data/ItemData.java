package br.com.developando.lootrestock.data;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemData {
    private MetaData itemMetaData;
    private int amount;
    private Material type;
    private Map<Enchantment, Integer> enchantmentMap;
    public ItemData(ItemStack item)   {
        this.itemMetaData = new MetaData(item.getItemMeta());
        this.amount = item.getAmount();
        this.type = item.getType();
        this.enchantmentMap = item.getEnchantments();
    }
    public ItemStack getItemStack()   {
        ItemStack itemStack = new ItemStack(this.type, this.amount);
        itemStack.setItemMeta(this.itemMetaData.getItemMeta(itemStack.getItemMeta()));
        itemStack.addEnchantments(this.enchantmentMap);
        return itemStack;
    }

    public MetaData getItemMetaData() {
        return itemMetaData;
    }

    public void setItemMetaData(MetaData itemMetaData) {
        this.itemMetaData = itemMetaData;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Material getType() {
        return type;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public Map<Enchantment, Integer> getEnchantmentMap() {
        return enchantmentMap;
    }

    public void setEnchantmentMap(Map<Enchantment, Integer> enchantmentMap) {
        this.enchantmentMap = enchantmentMap;
    }
}
