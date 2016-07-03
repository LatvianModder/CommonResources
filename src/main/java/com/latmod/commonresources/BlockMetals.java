package com.latmod.commonresources;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class BlockMetals extends Block
{
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

    public enum EnumType implements IStringSerializable
    {
        ORE_COPPER(0, "oreCopper"),
        ORE_TIN(1, "oreTin"),
        ORE_SILVER(2, "oreSilver"),
        ORE_LEAD(3, "oreLead"),
        ORE_RUBY(4, "oreRuby"),
        ORE_SAPPHIRE(5, "oreSapphire"),
        ORE_PERIDOT(6, "orePeridot"),
        BLOCK_STEEL(7, "blockSteel"),
        BLOCK_COPPER(8, "blockCopper"),
        BLOCK_TIN(9, "blockTin"),
        BLOCK_SILVER(10, "blockSilver"),
        BLOCK_LEAD(11, "blockLead"),
        BLOCK_BRONZE(12, "blockBronze"),
        BLOCK_RUBY(13, "blockRuby"),
        BLOCK_SAPPHIRE(14, "blockSapphire"),
        BLOCK_PERIDOT(15, "blockPeridot");

        public static final EnumType[] VALUES = values();
        public static final EnumType[] META_MAP = new EnumType[16];

        static
        {
            for(EnumType v : VALUES)
            {
                META_MAP[v.meta] = v;
            }
        }

        public final int meta;
        public final String name;
        public final String uname;
        public final String oreName;

        EnumType(int m, String ore)
        {
            meta = m;
            name = name().toLowerCase();
            uname = "commonresources.tile." + name.replace('_', '.');
            oreName = ore;
        }

        public static EnumType byMetadata(int meta)
        {
            if(meta < 0 || meta > 15 || META_MAP[meta] == null)
            {
                return ORE_COPPER;
            }

            return META_MAP[meta];
        }

        @Nonnull
        @Override
        public String getName()
        {
            return name;
        }

        public ItemStack stack(int q)
        {
            return new ItemStack(CommonResources.metals, q, meta);
        }
    }

    public BlockMetals()
    {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumType.ORE_COPPER));
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(3F);
        setResistance(5F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumType t : EnumType.VALUES)
        {
            list.add(new ItemStack(itemIn, 1, t.meta));
        }
    }

    public void init()
    {
        for(EnumType t : EnumType.VALUES)
        {
            OreDictionary.registerOre(t.oreName, t.stack(1));
        }
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        Item item = Item.getItemFromBlock(this);

        for(EnumType t : EnumType.VALUES)
        {
            ModelLoader.setCustomModelResourceLocation(item, t.meta, new ModelResourceLocation(getRegistryName(), "variant=" + t.getName()));
        }
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }

    /*
    @Override
    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockPlanks.EnumType) state.getValue(VARIANT)).getMapColor();
    }
    */

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).meta;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT);
    }
}
