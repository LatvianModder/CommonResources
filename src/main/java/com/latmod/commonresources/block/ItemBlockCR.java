package com.latmod.commonresources.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class ItemBlockCR extends ItemBlock
{
    public ItemBlockCR(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int d)
    {
        return d;
    }
}
