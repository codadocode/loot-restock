package br.com.developando.lootrestock;

import br.com.developando.lootrestock.commands.LrCommand;
import br.com.developando.lootrestock.listeners.LrListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class LootRestock extends JavaPlugin {
    private String fileName = "loots.yml";
    @Override
    public void onEnable()   {
        getLogger().info("Chest Restock is loading...");
        YamlConfiguration lootsFile = loadFiles();
        LrCommand lrCommand = new LrCommand(this.getServer(), this, lootsFile);
        LrListener lrListener = new LrListener(lrCommand);
        getServer().getPluginManager().registerEvents(lrListener, this);
        this.getCommand("lr").setExecutor(lrCommand);
    }
    @Override
    public void onDisable()   {
        getLogger().info("Chest Restock is unloading...");
    }
    public YamlConfiguration loadFiles()   {
        File tmpFile = new File(this.getDataFolder().getAbsolutePath() + "/" + fileName);
        YamlConfiguration tmpYml = new YamlConfiguration();
        if (!tmpFile.exists())   { //Create loots.yml if does not exits
            try   {
                tmpYml.save(tmpFile);
            }catch (Exception e)   {

            }
        }
        try   { //Load loots file
            tmpYml.load(tmpFile);
        }catch (Exception e)   {

        }
        return tmpYml;
    }
}
