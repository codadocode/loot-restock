package br.com.developando.lootrestock.data;

import com.google.common.collect.Multimap;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetaData {
    private Multimap<Attribute, AttributeModifier> attributeModifiers;
    private int customModelData;
    private String displayName;
    private Map<Enchantment, Integer> enchantments;
    private Set<ItemFlag> itemFlags;
    private String localizedName;
    private List<String> lore;
    public MetaData (ItemMeta itemMeta)   {
        if (itemMeta.hasAttributeModifiers())   {
            this.attributeModifiers = itemMeta.getAttributeModifiers();
        }
        if (itemMeta.hasCustomModelData())   {
            this.customModelData = itemMeta.getCustomModelData();
        }
        if (itemMeta.hasDisplayName())   {
            this.displayName = itemMeta.getDisplayName();
        }
        if (itemMeta.hasEnchants())   {
            this.enchantments = itemMeta.getEnchants();
        }
        if (itemMeta.hasLocalizedName())   {
            this.localizedName = itemMeta.getLocalizedName();
        }
        if (itemMeta.hasLore())   {
            this.lore = itemMeta.getLore();
        }
        this.itemFlags = itemMeta.getItemFlags();
    }
    public ItemMeta getItemMeta(ItemMeta oldItemMeta)   {
        ItemMeta itemMeta = oldItemMeta;
        itemMeta.setAttributeModifiers(this.attributeModifiers);
        itemMeta.setCustomModelData(this.customModelData);
        itemMeta.setDisplayName(this.displayName);
        Enchantment[] enchantmentsArray = new Enchantment[this.enchantments.size()];
        Integer[] enchantmentsLevelArray = new Integer[this.enchantments.size()];
        this.enchantments.keySet().toArray(enchantmentsArray);
        this.enchantments.keySet().toArray(enchantmentsLevelArray);
        for (int i = 0; i < enchantmentsArray.length; i++)   {
            itemMeta.addEnchant(enchantmentsArray[i], enchantmentsLevelArray[i], false);
        }
        ItemFlag[] flags = new ItemFlag[this.itemFlags.size()];
        for (int i = 0; i < flags.length; i++)   {
            itemMeta.addItemFlags(flags[i]);
        }
        itemMeta.setLocalizedName(this.localizedName);
        itemMeta.setLore(this.lore);
        return itemMeta;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return attributeModifiers;
    }

    public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
        this.attributeModifiers = attributeModifiers;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public Set<ItemFlag> getItemFlags() {
        return itemFlags;
    }

    public void setItemFlags(Set<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
