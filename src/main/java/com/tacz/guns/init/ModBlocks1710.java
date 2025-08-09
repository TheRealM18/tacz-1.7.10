package com.tacz.guns.init;

import com.tacz.guns.GunMod1710;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks1710 {
    // Blocks
    public static Block gunSmithTable;
    public static Block workbench111;
    public static Block workbench211;
    public static Block workbench121;
    public static Block target;
    public static Block statue;
    
    public static void init() {
        // Initialize blocks
        gunSmithTable = new BlockGunSmithTable()
            .setBlockName("gun_smith_table")
            .setBlockTextureName(GunMod1710.MODID + ":gun_smith_table");
            
        workbench111 = new BlockWorkbench()
            .setBlockName("workbench_a")
            .setBlockTextureName(GunMod1710.MODID + ":workbench_a");
            
        workbench211 = new BlockWorkbench()
            .setBlockName("workbench_b")
            .setBlockTextureName(GunMod1710.MODID + ":workbench_b");
            
        workbench121 = new BlockWorkbench()
            .setBlockName("workbench_c")
            .setBlockTextureName(GunMod1710.MODID + ":workbench_c");
            
        target = new BlockTarget()
            .setBlockName("target")
            .setBlockTextureName(GunMod1710.MODID + ":target");
            
        statue = new BlockStatue()
            .setBlockName("statue")
            .setBlockTextureName(GunMod1710.MODID + ":statue");
        
        // Register blocks
        registerBlock(gunSmithTable, "gun_smith_table");
        registerBlock(workbench111, "workbench_a");
        registerBlock(workbench211, "workbench_b");
        registerBlock(workbench121, "workbench_c");
        registerBlock(target, "target");
        registerBlock(statue, "statue");
    }
    
    private static void registerBlock(Block block, String name) {
        GameRegistry.registerBlock(block, name);
    }
    
    // Helper class for blocks with metadata
    public static class BlockWithMetadata extends Block {
        public BlockWithMetadata(Material material) {
            super(material);
        }
        
        @Override
        public int damageDropped(int meta) {
            return meta;
        }
    }
}
