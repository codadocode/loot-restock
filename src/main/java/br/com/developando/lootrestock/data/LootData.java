package br.com.developando.lootrestock.data;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Map;

public class LootData implements Serializable {
    private Map<Location, LootInfo> mapLoots;
    public Map<Location, LootInfo> getMapLoots()   {
        return this.mapLoots;
    }
    public void setMapLoots(Map<Location, LootInfo> mapLoots)   {
        this.mapLoots = mapLoots;
    }
}
