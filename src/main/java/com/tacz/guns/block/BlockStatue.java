package com.tacz.guns.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStatue extends BlockContainer {
    
    public BlockStatue() {
        super(Material.rock);
        setHardness(2.0F);
        setResistance(10.0F);
        setStepSound(soundTypeStone);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        // Return your TileEntity implementation here
        return null; // Replace with your TileEntity
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return -1; // Default render type
    }
}
