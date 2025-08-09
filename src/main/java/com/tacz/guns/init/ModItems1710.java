package com.tacz.guns.init;

import com.tacz.guns.GunMod1710;
import com.tacz.guns.item.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems1710 {
    // Items
    public static Item modernKineticGun;
    public static Item ammo;
    public static Item attachment;
    public static Item gunSmithTable;
    public static Item workbenchA;
    public static Item workbenchB;
    public static Item workbenchC;
    public static Item target;
    public static Item statue;
    public static Item ammoBox;
    public static Item targetMinecart;
    
    public static void init() {
        // Initialize items
        modernKineticGun = new ModernKineticGunItem()
            .setUnlocalizedName("modern_kinetic_gun")
            .setTextureName(GunMod1710.MODID + ":modern_kinetic_gun");
            
        ammo = new AmmoItem()
            .setUnlocalizedName("ammo")
            .setTextureName(GunMod1710.MODID + ":ammo");
            
        attachment = new AttachmentItem()
            .setUnlocalizedName("attachment")
            .setTextureName(GunMod1710.MODID + ":attachment");
            
        // Register items
        registerItem(modernKineticGun, "modern_kinetic_gun");
        registerItem(ammo, "ammo");
        registerItem(attachment, "attachment");
        
        // Register block items
        gunSmithTable = registerBlockItem(ModBlocks1710.gunSmithTable, "gun_smith_table");
        workbenchA = registerBlockItem(ModBlocks1710.workbench111, "workbench_a");
        workbenchB = registerBlockItem(ModBlocks1710.workbench211, "workbench_b");
        workbenchC = registerBlockItem(ModBlocks1710.workbench121, "workbench_c");
        target = registerBlockItem(ModBlocks1710.target, "target");
        statue = registerBlockItem(ModBlocks1710.statue, "statue");
        
        // Special items
        ammoBox = new AmmoBoxItem()
            .setUnlocalizedName("ammo_box")
            .setTextureName(GunMod1710.MODID + ":ammo_box");
        registerItem(ammoBox, "ammo_box");
        
        targetMinecart = new TargetMinecartItem()
            .setUnlocalizedName("target_minecart")
            .setTextureName(GunMod1710.MODID + ":target_minecart");
        registerItem(targetMinecart, "target_minecart");
    }
    
    private static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }
    
    private static Item registerBlockItem(net.minecraft.block.Block block, String name) {
        Item item = new ItemBlockWithMetadata(block, block);
        item.setUnlocalizedName(name);
        item.setTextureName(GunMod1710.MODID + ":" + name);
        GameRegistry.registerItem(item, name);
        return item;
    }
}
