package br.com.developando.lootrestock.commands;

import br.com.developando.lootrestock.LootRestock;
import br.com.developando.lootrestock.data.ItemData;
import br.com.developando.lootrestock.data.LootInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class LrCommand implements CommandExecutor {
    private Map<String, LootInfo> lootInfo;
    private LootRestock plugin;
    private Server server;
    private File lootsFile;
    private String pluginDirPath;
    private String lootsFileName = "loots.json";
    public LrCommand(Server server, LootRestock plugin)   {
        this.server = server;
        this.plugin = plugin;
        this.pluginDirPath = plugin.getDataFolder().getAbsolutePath();
        this.lootInfo = new HashMap<String, LootInfo>();
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
                            saveChestState(selectedChest, resetTime);
                            printOnConsole("[Loot Restock]: Chest has been created with cooldown reset!");
                        }
                    }
                }else if (strings[0].equals("restock"))   {
                    Block tmpBlock = cmdPlayer.getTargetBlock(null,5);
                    if (tmpBlock.getType() == Material.CHEST)   {
                        Chest selectedChest = (Chest) tmpBlock.getState();
                        restockChest(selectedChest);
                    }
                }
            }
        }
        return true;
    }
    private void saveChestState(Chest chest, Long timeReset)   {
        Location chestLocation = chest.getLocation();
        ItemStack[] itemStacks = chest.getInventory().getContents().clone();
        Long chestResetTime = (timeReset * 20);
        this.lootInfo.put(getLocationInString(chestLocation), new LootInfo(Arrays.asList(itemStacks), chestResetTime));
    }
    private void restockChest(Chest chest)   {
        String chestLocationString = getLocationInString(chest.getLocation());
        if (this.lootInfo.containsKey(chestLocationString))   {
            printOnConsole("This chest exists in lootinfo.");
            chest.getInventory().setContents(this.lootInfo.get(chestLocationString).getItemStack());
            printOnConsole("The chest has been restocked!");
        }
    }
    private String getLocationInString(Location location)   {
        Integer posX = new Integer(location.getBlockX());
        Integer posY = new Integer(location.getBlockY());
        Integer posZ = new Integer(location.getBlockZ());
        return new String(posX.toString() + "/" + posY.toString() + "/" + posZ.toString());
    }
    private void printOnConsole(String msg)   {
        plugin.getLogger().info(msg);
    }
    public void test(Chest chest)   {
        try   {
            File pluginDir = new File(this.pluginDirPath);
            if (!pluginDir.exists())   {
                pluginDir.mkdir();
            }
            File lootsFile = new File(pluginDir.getAbsolutePath() + "/" + "loots.json");
            if (!lootsFile.exists())   {
                lootsFile.createNewFile();
            }
            Writer writer = new FileWriter(lootsFile);
            List<ItemData> itemDataList = new ArrayList<ItemData>();

            ItemStack[] itemStacks = chest.getInventory().getContents();
            List<ItemStack> listItemStack = Arrays.asList(itemStacks);
            for (int i = 0; i < listItemStack.size(); i++)   {
                if (listItemStack.get(i) != null)   {
                    itemDataList.add(new ItemData(listItemStack.get(i)));
                }
            }
            System.out.println(itemDataList.toString());
            LootInfo info = new LootInfo(listItemStack, 200L);
            Map<String, LootInfo> lootInfo = new HashMap<String, LootInfo>();
            lootInfo.put("-7/140/-20", info);

            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(lootInfo, writer);
            writer.close();
        }catch (Exception e)   {
            e.printStackTrace();
        }
    }
}
