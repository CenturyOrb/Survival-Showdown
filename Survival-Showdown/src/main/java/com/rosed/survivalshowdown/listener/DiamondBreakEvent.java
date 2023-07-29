package com.rosed.survivalshowdown.listener;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.concurrent.ThreadLocalRandom;

// Author of this class: death.bringer

public class DiamondBreakEvent implements Listener {

    private final BlockFace[] faces = {BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)   {

        Block broken = e.getBlock();

        if (!MaterialTags.ORES.isTagged(broken)) return;

        for (BlockFace face : faces)   {

            Block relative = broken.getRelative(face);
            Material relativeType = relative.getType();

            if (relativeType != Material.STONE) break;
            if (isCovered(relative) && ThreadLocalRandom.current().nextInt(100) <= 33)   {
                relative.setType(broken.getType());
            }

        }

    }

    private boolean isCovered(Block block)   {

        for (BlockFace face : faces)   {
            Block relative = block.getRelative(face);
            if (relative.getType().isAir()) return false;
        }
        return true;

    }

}
