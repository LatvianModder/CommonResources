package com.latmod.commonresources.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class BlockMetals extends BlockCR
{
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

    public enum EnumType implements IStringSerializable
    {
        ORE_COPPER(0),
        ORE_TIN(1),
        ORE_SILVER(2),
        ORE_LEAD(3),
        ORE_RUBY(4),
        ORE_SAPPHIRE(5),
        ORE_PERIDOT(6),
        BLOCK_STEEL(7),
        BLOCK_COPPER(8),
        BLOCK_TIN(9),
        BLOCK_SILVER(10),
        BLOCK_LEAD(11),
        BLOCK_BRONZE(12),
        BLOCK_RUBY(13),
        BLOCK_SAPPHIRE(14),
        BLOCK_PERIDOT(15);

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

        EnumType(int m)
        {
            meta = m;
            name = name().toLowerCase();
            uname = "commonresources.tile." + name.replace('_', '.');
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
    public ItemBlock createItemBlock()
    {
        return new ItemBlockCR(this)
        {
            @Nonnull
            @Override
            public String getUnlocalizedName(ItemStack stack)
            {
                EnumType t = EnumType.byMetadata(stack.getMetadata());

                if(t != null)
                {
                    return t.uname;
                }

                return super.getUnlocalizedName(stack);
            }
        };
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

    @Override
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
