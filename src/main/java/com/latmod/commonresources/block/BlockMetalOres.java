package com.latmod.commonresources.block;

import com.latmod.commonresources.CommonResources;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * Created by LatvianModder on 06.07.2016.
 */
public class BlockMetalOres extends BlockCR
{
    public static final PropertyEnum<EnumMetalType> VARIANT = PropertyEnum.create("variant", EnumMetalType.class, EnumMetalType.ORES);

    public BlockMetalOres()
    {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumMetalType.COPPER));
        setHardness(3F);
        setResistance(5F);
        setSoundType(SoundType.STONE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
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
            ModelLoader.setCustomModelResourceLocation(item, t.meta, new ModelResourceLocation(new ResourceLocation(CommonResources.MOD_ID, t.name + "_ore"), "inventory"));
        }
    }

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

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockCR(this)
        {
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
        };
    }
}