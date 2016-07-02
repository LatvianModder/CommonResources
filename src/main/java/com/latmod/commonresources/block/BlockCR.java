package com.latmod.commonresources.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public abstract class BlockCR extends Block
{
    public BlockCR(Material m)
    {
        super(m);
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
    }

    public ItemBlock createItemBlock()
    {
        return new ItemBlockCR(this);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }
}
