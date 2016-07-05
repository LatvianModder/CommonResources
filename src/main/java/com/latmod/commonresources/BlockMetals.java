package com.latmod.commonresources;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class BlockMetals extends Block
{
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

    public enum EnumType implements IStringSerializable
    {
        COPPER_ORE(0, "oreCopper"),
        COPPER_BLOCK(8, "blockCopper"),
        TIN_ORE(1, "oreTin"),
        TIN_BLOCK(9, "blockTin"),
        SILVER_ORE(2, "oreSilver"),
        SILVER_BLOCK(10, "blockSilver"),
        LEAD_ORE(3, "oreLead"),
        LEAD_BLOCK(11, "blockLead"),
        RUBY_ORE(4, "oreRuby"),
        RUBY_BLOCK(13, "blockRuby"),
        SAPPHIRE_ORE(5, "oreSapphire"),
        SAPPHIRE_BLOCK(14, "blockSapphire"),
        PERIDOT_ORE(6, "orePeridot"),
        PERIDOT_BLOCK(15, "blockPeridot"),
        BRONZE_BLOCK(12, "blockBronze"),
        STEEL_BLOCK(7, "blockSteel");

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
            uname = "tile." + name.replace('_', '.');
            oreName = ore;
        }

        public static EnumType byMetadata(int meta)
        {
            if(meta < 0 || meta > 15 || META_MAP[meta] == null)
            {
                return COPPER_ORE;
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

        public boolean isGemOre()
        {
            return this == RUBY_ORE || this == SAPPHIRE_ORE || this == PERIDOT_ORE;
        }
    }

    public BlockMetals()
    {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumType.COPPER_ORE));
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(3F);
        setResistance(5F);
        setSoundType(SoundType.STONE);
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

    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch(state.getValue(VARIANT))
        {
            case RUBY_ORE:
                return CommonResources.materials.ruby.map.get(ItemMaterials.GroupMatType.ITEM).getItem();
            case SAPPHIRE_ORE:
                return CommonResources.materials.sapphire.map.get(ItemMaterials.GroupMatType.ITEM).getItem();
            case PERIDOT_ORE:
                return CommonResources.materials.peridot.map.get(ItemMaterials.GroupMatType.ITEM).getItem();
            default:
                return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, @Nonnull Random random)
    {
        return state.getValue(VARIANT).isGemOre() ? (1 + random.nextInt(2)) : 1;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, @Nonnull Random random)
    {
        if(fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if(i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        if(state.getValue(VARIANT).isGemOre())
        {
            return MathHelper.getRandomIntegerInRange(world instanceof World ? ((World) world).rand : new Random(), 2, 5);
        }

        return 0;
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, getMetaFromState(state));
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        switch(state.getValue(VARIANT))
        {
            case RUBY_ORE:
                return CommonResources.materials.ruby.map.get(ItemMaterials.GroupMatType.ITEM).getMeta();
            case SAPPHIRE_ORE:
                return CommonResources.materials.sapphire.map.get(ItemMaterials.GroupMatType.ITEM).getMeta();
            case PERIDOT_ORE:
                return CommonResources.materials.peridot.map.get(ItemMaterials.GroupMatType.ITEM).getMeta();
            default:
                return getMetaFromState(state);
        }
    }
}
