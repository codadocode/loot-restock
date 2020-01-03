package br.com.developando.lootrestock;

import br.com.developando.lootrestock.commands.LrCommand;
import br.com.developando.lootrestock.data.LootInfo;
import br.com.developando.lootrestock.listeners.LrListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LootRestock extends JavaPlugin {
    private String fileName = "loots.yml";
    @Override
    public void onEnable()   {
        getLogger().info("Chest Restock is loading...");
        //onTest();
        LrCommand lrCommand = new LrCommand(this.getServer(), this);
        LrListener lrListener = new LrListener(lrCommand);
        getServer().getPluginManager().registerEvents(lrListener, this);
        this.getCommand("lr").setExecutor(lrCommand);
    }
    @Override
    public void onDisable()   {
        getLogger().info("Chest Restock is unloading...");
    }
    public void onTest()   {
        File pluginDir = new File(this.getDataFolder().getAbsolutePath());
        File lootJson = new File(pluginDir.getAbsolutePath() + "/" + "loots.json");
        try   {
            if (!pluginDir.exists())   {
                pluginDir.mkdir();
            }
            if (!lootJson.exists())   {
                lootJson.createNewFile();
            }
            ItemStack item1 = new ItemStack(Material.APPLE, 64);
            ItemStack item2 = new ItemStack(Material.BAKED_POTATO, 32);
            ItemStack item3 = new ItemStack(Material.COOKED_CHICKEN, 16);
            ItemStack[] itemArray = new ItemStack[]{item1, item2, item3};
            LootInfo lootInfo = new LootInfo(Arrays.asList(itemArray), 2L);
            Map<String, LootInfo> map = new HashMap<String, LootInfo>();
            map.put("-3/44/-120", lootInfo);
            Writer writer = new FileWriter(lootJson);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(map, writer);
            writer.close();
        }catch (Exception e)   {
            e.printStackTrace();
        }
    }
}
