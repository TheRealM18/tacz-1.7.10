package com.tacz.guns.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTarget extends BlockContainer {
    
    public BlockTarget() {
        super(Material.wood);
        setHardness(1.0F);
        setStepSound(soundTypeWood);
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
