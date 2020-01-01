package br.com.developando.lootrestock.commands;

import br.com.developando.lootrestock.LootRestock;
import br.com.developando.lootrestock.data.LootInfo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class LrCommand implements CommandExecutor {
    //private Map<Location, LootInfo> lootList;
    private Map<String, LootInfo> lootList;
    private LootRestock plugin;
    private Server server;
    private YamlConfiguration lootsFile;
    public LrCommand(Server server, LootRestock plugin, YamlConfiguration lootsFile)   {
        //this.lootList = new HashMap<Location, LootInfo>();
        this.lootList = new HashMap<String, LootInfo>();
        this.server = server;
        this.plugin = plugin;
        this.lootsFile = lootsFile;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player)   {
            if (strings.length > 0)   {
                Player cmdPlayer = (Player)commandSender;
                if (strings[0].equals("create"))   {
                    Block tmpBlock = cmdPlayer.getTargetBlock(null,5);
                    if (strings.length > 1)   { //Create Chest With Timer
                        if (tmpBlock.getType() == Material.CHEST)   {
                            Long resetTime = new Long(strings[1]);
                            Chest selectedChest = (Chest) tmpBlock.getState();
                            this.saveChestStateWithTimer(selectedChest, resetTime);
                            chestRestock(selectedChest);
                            server.getLogger().info("[Loot Restock]: Chest has been created with cooldown reset!");
                        }
                    }else   { //Create Chest With No Timer
                        if (tmpBlock.getType() == Material.CHEST)   {
                            Chest selectedChest = (Chest) tmpBlock.getState();
                            this.saveChestState(selectedChest);
                            chestRestock(selectedChest);
                            server.getLogger().info("[Loot Restock]: Chest has been created!");
                        }
                    }
                }else if (strings[0].equals("restock"))   { //Instantly Restock a Chest
                    Block tmpBlock = cmdPlayer.getTargetBlock(null, 5);
                    if (tmpBlock.getType() == Material.CHEST)   {
                        Chest selectedChest = (Chest) tmpBlock.getState();
                        chestRestock(selectedChest);
                    }
                }
            }
        }
        return true;
    }
    //Save Chest State
    private void saveChestState(Chest chest)   {
        String stringChestLocation = getBlockPositionIntoString(chest.getBlock());
        this.lootList.put(stringChestLocation, new LootInfo(chest.getBlockInventory().getStorageContents().clone()));
        this.lootsFile.createSection("loot-info", this.lootList);
        this.saveLootsFile();
    }
    //Save Chest State With Timer
    private void saveChestStateWithTimer(Chest chest, Long resetTime)   {
        Long tmpResetTime = resetTime * 20;
        String stringChestLocation = getBlockPositionIntoString(chest.getBlock());
        this.lootList.put(stringChestLocation, new LootInfo(chest.getBlockInventory().getStorageContents().clone(), tmpResetTime));
        this.lootsFile.createSection("loot-info", this.lootList);
        this.saveLootsFile();
    } //Restock Chest
    private void chestRestock(Chest chest)   {
        String stringPosArray = getBlockPositionIntoString(chest.getBlock());
        if (this.lootList.containsKey(stringPosArray))   {
            LootInfo tmpLootInfo = this.lootList.get(stringPosArray);
            chest.getBlockInventory().setStorageContents(tmpLootInfo.getItemStacks());
            tmpLootInfo.turnOffCooldown();
            server.getLogger().info("[Loot Restock]: Chest has been restocked!");
        }
    } //Start Chest Timer Cooldown
    public void setChestReset(final Chest chest)   {
        if (this.lootList.containsKey(chest.getLocation()))   {
            Chest tmpChest = chest;
            LootInfo lootInfo = this.lootList.get(chest.getLocation());
            if (!lootInfo.isOnCooldown())   {
                if (lootInfo.getResetTime() > 0)   {
                    server.getLogger().info("Passou isOnCooldown!");
                    server.getScheduler().runTaskLater(this.plugin, new Runnable() {
                        @Override
                        public void run() {
                            chestRestock(chest);
                        }
                    }, lootInfo.getResetTime());
                }
                lootInfo.turnOnCooldown();
            }
        }
    }
    private String getBlockPositionIntoString(Block block)   {
        Integer[] intArray = new Integer[] {block.getState().getLocation().getBlockX(), block.getState().getLocation().getBlockY(), block.getState().getLocation().getBlockZ()};
        String stringPos = new String(intArray[0].toString() + "/" + intArray[1].toString() + "/" + intArray[2].toString());
        return stringPos;
    }
    private void saveLootsFile()   {
        try   {
            this.lootsFile.save(plugin.getDataFolder().getAbsolutePath() + "/" + "loots.yml");
            server.getLogger().info("Loots File Has Been Saved!");
        }catch (Exception e)   {

        }
    }
}
