package com.tacz.guns.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class AmmoItem1710 extends Item {
    
    private IIcon[] icons;
    private String[] ammoTypes = {"pistol", "rifle", "sniper", "shotgun"};
    
    public AmmoItem1710() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[ammoTypes.length];
        for (int i = 0; i < ammoTypes.length; i++) {
            icons[i] = register.registerIcon("tacz:ammo_" + ammoTypes[i]);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return meta < icons.length ? icons[meta] : icons[0];
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        return super.getUnlocalizedName() + "." + (meta < ammoTypes.length ? ammoTypes[meta] : "unknown");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < ammoTypes.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 64; // Stack size for ammo
    }
    
    /**
     * Get the ammo type for the given item stack
     */
    public String getAmmoType(ItemStack stack) {
        int meta = stack.getItemDamage();
        return meta < ammoTypes.length ? ammoTypes[meta] : ammoTypes[0];
    }
    
    /**
     * Get the damage amount for this ammo type
     */
    public float getDamageAmount(ItemStack stack) {
        String type = getAmmoType(stack);
        switch (type) {
            case "pistol": return 4.0F;
            case "rifle": return 6.0F;
            case "sniper": return 15.0F;
            case "shotgun": return 3.0F; // Per pellet
            default: return 1.0F;
        }
    }
    
    /**
     * Get the number of projectiles this ammo type fires (for shotguns)
     */
    public int getProjectileCount(ItemStack stack) {
        String type = getAmmoType(stack);
        return type.equals("shotgun") ? 8 : 1;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, List list, boolean advanced) {
        String type = getAmmoType(stack);
        float damage = getDamageAmount(stack);
        int count = getProjectileCount(stack);
        
        list.add("Type: " + type.substring(0, 1).toUpperCase() + type.substring(1));
        list.add("Damage: " + (count > 1 ? (count + "x" + damage) : String.format("%.1f", damage)));
    }
}
