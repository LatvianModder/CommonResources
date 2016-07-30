package com.latmod.commonresources.block;

import com.latmod.commonresources.CRItems;
import com.latmod.commonresources.CommonResources;
import com.latmod.commonresources.item.GroupMatType;
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
 * Created by LatvianModder on 06.07.2016.
 */
public class BlockOres extends Block
{
    public static final PropertyEnum<EnumMetalType> VARIANT = PropertyEnum.create("variant", EnumMetalType.class, EnumMetalType.ORES);

    public BlockOres()
    {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumMetalType.COPPER));
        setCreativeTab(CommonResources.creativeTab);
        setHardness(3F);
        setResistance(5F);
        setSoundType(SoundType.STONE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumMetalType t : EnumMetalType.ORES)
        {
            list.add(new ItemStack(itemIn, 1, t.meta));
        }
    }

    public void init()
    {
        for(EnumMetalType t : EnumMetalType.ORES)
        {
            OreDictionary.registerOre("ore" + t.oreName, t.stack(false, 1));
        }
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        Item item = Item.getItemFromBlock(this);

        for(EnumMetalType t : EnumMetalType.ORES)
        {
            ModelLoader.setCustomModelResourceLocation(item, t.meta, new ModelResourceLocation(getRegistryName(), "variant=" + t.getName()));
        }
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(VARIANT, EnumMetalType.byMetadata(meta));
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
            case RUBY:
                return CRItems.MATERIALS.ruby.map.get(GroupMatType.ITEM).getItem();
            case SAPPHIRE:
                return CRItems.MATERIALS.sapphire.map.get(GroupMatType.ITEM).getItem();
            case PERIDOT:
                return CRItems.MATERIALS.peridot.map.get(GroupMatType.ITEM).getItem();
            default:
                return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, @Nonnull Random random)
    {
        return state.getValue(VARIANT).isGem() ? (1 + random.nextInt(2)) : 1;
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
        if(state.getValue(VARIANT).isGem())
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
            case RUBY:
                return CRItems.MATERIALS.ruby.map.get(GroupMatType.ITEM).getMeta();
            case SAPPHIRE:
                return CRItems.MATERIALS.sapphire.map.get(GroupMatType.ITEM).getMeta();
            case PERIDOT:
                return CRItems.MATERIALS.peridot.map.get(GroupMatType.ITEM).getMeta();
            default:
                return getMetaFromState(state);
        }
    }
}