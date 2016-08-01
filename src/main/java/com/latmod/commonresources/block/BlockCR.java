package com.latmod.commonresources.block;

import com.latmod.commonresources.CommonResources;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

/**
 * Created by LatvianModder on 01.08.2016.
 */
public abstract class BlockCR extends Block
{
    public BlockCR(Material m)
    {
        super(m);
        setCreativeTab(CommonResources.creativeTab);
    }

    public abstract ItemBlock createItemBlock();

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }
}
