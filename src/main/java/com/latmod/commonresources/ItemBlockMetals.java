package com.latmod.commonresources;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class ItemBlockMetals extends ItemBlock
{
    public ItemBlockMetals(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int d)
    {
        return d;
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        BlockMetals.EnumType t = BlockMetals.EnumType.byMetadata(stack.getMetadata());

        if(t != null)
        {
            return t.uname;
        }

        return super.getUnlocalizedName(stack);
    }
}
