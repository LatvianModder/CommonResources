package com.latmod.commonresources.block;

import com.latmod.commonresources.CRCommon;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 06.07.2016.
 */
public enum EnumGemType implements IStringSerializable
{
    RUBY(0, "Ruby", true),
    SAPPHIRE(1, "Sapphire", true),
    PERIDOT(2, "Peridot", true),
    AMETHYST(3, "Amethyst", true);

    public static final EnumGemType[] BLOCKS = values();
    public static final EnumGemType[] ORES;
    public static final EnumGemType[] META_LOOKUP = new EnumGemType[16];

    static
    {
        for(EnumGemType t : BLOCKS)
        {
            META_LOOKUP[t.meta] = t;
        }

        List<EnumGemType> list = new ArrayList<>();

        for(EnumGemType t : BLOCKS)
        {
            if(t.hasOre)
            {
                list.add(t);
            }
        }

        ORES = list.toArray(new EnumGemType[list.size()]);
    }

    public final int meta;
    public final String name;
    public final boolean hasOre;
    public final String oreName;

    EnumGemType(int m, String n, boolean o)
    {
        meta = m;
        name = name().toLowerCase();
        hasOre = o;
        oreName = n;
    }

    public static EnumGemType byMetadata(int meta)
    {
        return (meta >= 0 && meta < BLOCKS.length) ? BLOCKS[meta] : null;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }

    public ItemStack stack(boolean block, int q)
    {
        return new ItemStack(block ? CRCommon.GEM_BLOCKS : CRCommon.GEM_ORES, q, meta);
    }
}