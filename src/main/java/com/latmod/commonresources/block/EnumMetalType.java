package com.latmod.commonresources.block;

import com.latmod.commonresources.CRCommon;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 06.07.2016.
 */
public enum EnumMetalType implements IStringSerializable
{
    COPPER(0, "Copper", true),
    TIN(1, "Tin", true),
    SILVER(2, "Silver", true),
    LEAD(3, "Lead", true),
    BRONZE(4, "Bronze", false),
    STEEL(5, "Steel", false),
    NICKEL(6, "Nickel", true),
    PLATINUM(7, "Platinum", true),
    INVAR(8, "Invar", false);

    public static final EnumMetalType[] BLOCKS = values();
    public static final EnumMetalType[] ORES;
    public static final EnumMetalType[] META_LOOKUP = new EnumMetalType[16];

    static
    {
        for(EnumMetalType t : BLOCKS)
        {
            META_LOOKUP[t.meta] = t;
        }

        List<EnumMetalType> list = new ArrayList<>();

        for(EnumMetalType t : BLOCKS)
        {
            if(t.hasOre)
            {
                list.add(t);
            }
        }

        ORES = list.toArray(new EnumMetalType[list.size()]);
    }

    public final int meta;
    public final String name;
    public final boolean hasOre;
    public final String oreName;

    EnumMetalType(int m, String n, boolean o)
    {
        meta = m;
        name = name().toLowerCase();
        hasOre = o;
        oreName = n;
    }

    public static EnumMetalType byMetadata(int meta)
    {
        EnumMetalType t = (meta >= 0 && meta < BLOCKS.length) ? BLOCKS[meta] : null;
        return (t == null) ? COPPER : t;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public ItemStack stack(boolean block, int q)
    {
        return new ItemStack(block ? CRCommon.METAL_BLOCKS : CRCommon.METAL_ORES, q, meta);
    }
}