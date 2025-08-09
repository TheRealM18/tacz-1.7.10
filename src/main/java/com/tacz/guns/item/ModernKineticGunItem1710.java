package com.tacz.guns.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ModernKineticGunItem1710 extends Item {
    
    private IIcon[] icons;
    private String[] gunTypes = {"pistol", "rifle", "sniper"}; // Example gun types
    
    public ModernKineticGunItem1710() {
        super();
        setMaxStackSize(1);
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[gunTypes.length];
        for (int i = 0; i < gunTypes.length; i++) {
            icons[i] = register.registerIcon("tacz:gun_" + gunTypes[i]);
        }
    }
    
    @Override
    public IIcon getIconFromDamage(int meta) {
        return meta < icons.length ? icons[meta] : icons[0];
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        return super.getUnlocalizedName() + "." + (meta < gunTypes.length ? gunTypes[meta] : "unknown");
    }
    
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < gunTypes.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000; // Max use duration
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
        // Handle shooting logic here
        if (!world.isRemote) {
            // Server-side shooting logic
            // Create and spawn bullet entity
        }
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
        // Update logic for the gun
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            // Handle cooldowns, reloading, etc.
        }
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        // Add tooltip information
        list.add("Damage: 5.0"); // Example damage
        list.add("Ammo: 30/30"); // Example ammo counter
    }
}
