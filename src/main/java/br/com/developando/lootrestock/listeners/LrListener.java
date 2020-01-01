package br.com.developando.lootrestock.listeners;

import br.com.developando.lootrestock.commands.LrCommand;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LrListener implements Listener {
    private LrCommand lrCommand;
    public LrListener(LrCommand lrCommand)   {
        this.lrCommand = lrCommand;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)   {
        Block tmpBlock = event.getClickedBlock();
        Player tmpPlayer = event.getPlayer();
        if (tmpBlock.getType() == Material.CHEST)   {
            Chest tmpChest = (Chest) tmpBlock.getState();
            lrCommand.setChestReset(tmpChest);
        }
    }
}
