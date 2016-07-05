package com.latmod.commonresources.block;

import com.latmod.commonresources.CommonResources;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class BlockBlocks extends Block
{
    public static final PropertyEnum<EnumMetalType> VARIANT = PropertyEnum.create("variant", EnumMetalType.class, EnumMetalType.BLOCKS);

    public BlockBlocks()
    {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumMetalType.COPPER));
        setCreativeTab(CommonResources.creativeTab);
        setHardness(5F);
        setResistance(10F);
        setSoundType(SoundType.METAL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumMetalType t : EnumMetalType.BLOCKS)
        {
            list.add(new ItemStack(itemIn, 1, t.meta));
        }
    }

    public void init()
    {
        for(EnumMetalType t : EnumMetalType.BLOCKS)
        {
            OreDictionary.registerOre("block" + t.oreName, t.stack(true, 1));
        }
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        Item item = Item.getItemFromBlock(this);

        for(EnumMetalType t : EnumMetalType.BLOCKS)
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
}
