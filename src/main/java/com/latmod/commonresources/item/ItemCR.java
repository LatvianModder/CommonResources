package com.latmod.commonresources.item;

import com.latmod.commonresources.CommonResources;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 03.08.2016.
 */
public class ItemCR extends Item
{
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab()
    {
        return CommonResources.INST.creativeTab;
    }
}