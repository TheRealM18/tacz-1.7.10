package com.tacz.guns.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class AttachmentItem1710 extends Item {
    
    public enum AttachmentType {
        SCOPE("scope", EnumRarity.uncommon),
        SIGHT("sight", EnumRarity.uncommon),
        BARREL("barrel", EnumRarity.common),
        GRIP("grip", EnumRarity.common),
        MAGAZINE("magazine", EnumRarity.common),
        STOCK("stock", EnumRarity.common),
        MUZZLE("muzzle", EnumRarity.uncommon);
        
        private final String name;
        private final EnumRarity rarity;
        
        AttachmentType(String name, EnumRarity rarity) {
            this.name = name;
            this.rarity = rarity;
        }
        
        public String getName() {
            return name;
        }
        
        public EnumRarity getRarity() {
            return rarity;
        }
        
        public static AttachmentType byMetadata(int meta) {
            return values()[meta % values().length];
        }
    }
    
    private IIcon[] icons;
    
    public AttachmentItem1710() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[AttachmentType.values().length];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = register.registerIcon("tacz:attachment_" + AttachmentType.values()[i].getName());
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
        AttachmentType type = AttachmentType.byMetadata(meta);
        return super.getUnlocalizedName() + "." + type.getName();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < AttachmentType.values().length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return AttachmentType.byMetadata(stack.getItemDamage()).getRarity();
    }
    
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1; // Attachments usually don't stack
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, List list, boolean advanced) {
        AttachmentType type = AttachmentType.byMetadata(stack.getItemDamage());
        list.add("Type: " + type.name().substring(0, 1).toUpperCase() + 
                type.name().substring(1).toLowerCase());
        
        // Add attachment-specific stats based on type
        switch (type) {
            case SCOPE:
                list.add("§7+25% Accuracy");
                list.add("§7+2x Zoom");
                break;
            case SIGHT:
                list.add("§7+15% Accuracy");
                list.add("§7+1.5x Zoom");
                break;
            case BARREL:
                list.add("§7+10% Damage");
                list.add("§7+5% Recoil");
                break;
            case GRIP:
                list.add("§7-15% Recoil");
                list.add("§7+5% Accuracy");
                break;
            case MAGAZINE:
                list.add("§7+30% Ammo Capacity");
                break;
            case STOCK:
                list.add("§7-20% Recoil");
                list.add("§7+10% Accuracy");
                break;
            case MUZZLE:
                list.add("§7Silenced");
                list.add("§7-10% Damage");
                break;
        }
    }
    
    public static AttachmentType getAttachmentType(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof AttachmentItem1710)) {
            return null;
        }
        return AttachmentType.byMetadata(stack.getItemDamage());
    }
}
