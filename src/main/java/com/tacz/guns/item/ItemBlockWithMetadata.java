package com.tacz.guns.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWithMetadata extends ItemBlock {
    public ItemBlockWithMetadata(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    public ItemBlockWithMetadata(Block block, Block unused) {
        this(block);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
