package com.latmod.commonresources.block;

import com.latmod.commonresources.CommonResources;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
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
    RUBY(4, "Ruby", true),
    SAPPHIRE(5, "Sapphire", true),
    PERIDOT(6, "Peridot", true),
    BRONZE(7, "Bronze", false),
    STEEL(8, "Steel", false);

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
        return (meta >= 0 && meta < BLOCKS.length) ? BLOCKS[meta] : null;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }

    public boolean isGem()
    {
        return this == RUBY || this == SAPPHIRE || this == PERIDOT;
    }

    public ItemStack stack(boolean block, int q)
    {
        return new ItemStack(block ? CommonResources.blocks : CommonResources.ores, q, meta);
    }
}