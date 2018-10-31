package com.mrcrayfish.vehicle.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

/**
 * Author: MrCrayfish
 */
public class BlockWorkstation extends BlockRotatedObject
{
    public BlockWorkstation()
    {
        super(Material.IRON, "workstation");
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return true;
    }
}
