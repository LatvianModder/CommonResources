package com.latmod.commonresources.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class ItemBlockOres extends ItemBlock
{
    public ItemBlockOres(Block block)
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
        EnumMetalType t = EnumMetalType.byMetadata(stack.getMetadata());

        if(t != null)
        {
            return "tile." + t.name + ".ore";
        }

        return super.getUnlocalizedName(stack);
    }
}
