package com.latmod.commonresources.block;

import com.latmod.commonresources.CRCommon;
import com.latmod.commonresources.CommonResources;
import com.latmod.commonresources.item.GroupMatType;
import com.latmod.commonresources.item.ItemMaterials;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by LatvianModder on 06.07.2016.
 */
public class BlockGemOres extends BlockCR
{
    public static final PropertyEnum<EnumGemType> VARIANT = PropertyEnum.create("variant", EnumGemType.class, EnumGemType.ORES);

    public BlockGemOres()
    {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumGemType.RUBY));
        setHardness(3F);
        setResistance(5F);
        setSoundType(SoundType.STONE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumGemType t : EnumGemType.ORES)
        {
            list.add(new ItemStack(itemIn, 1, t.meta));
        }
    }

    public void init()
    {
        for(EnumGemType t : EnumGemType.ORES)
        {
            OreDictionary.registerOre("ore" + t.oreName, t.stack(false, 1));
        }
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        Item item = Item.getItemFromBlock(this);

        for(EnumGemType t : EnumGemType.ORES)
        {
            ModelLoader.setCustomModelResourceLocation(item, t.meta, new ModelResourceLocation(new ResourceLocation(CommonResources.MOD_ID, t.name + "_ore"), "inventory"));
        }
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(VARIANT, EnumGemType.byMetadata(meta));
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
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        ItemMaterials.NewGem gem = CRCommon.MATERIALS.new_gems.get(state.getValue(VARIANT));
        return (gem == null) ? Item.getItemFromBlock(this) : gem.map.get(GroupMatType.ITEM).getItem();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        ItemMaterials.NewGem gem = CRCommon.MATERIALS.new_gems.get(state.getValue(VARIANT));
        return (gem == null) ? getMetaFromState(state) : gem.map.get(GroupMatType.ITEM).getMeta();
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return 1 + random.nextInt(2);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if(fortune > 0 && Item.getItemFromBlock(this) != getItemDropped(getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if(i < 0)
            {
                i = 0;
            }

            return quantityDropped(random) * (i + 1);
        }
        else
        {
            return quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        return MathHelper.getRandomIntegerInRange(world instanceof World ? ((World) world).rand : new Random(), 2, 5);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, getMetaFromState(state));
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockCR(this)
        {
            @Override
            public String getUnlocalizedName(ItemStack stack)
            {
                EnumGemType t = EnumGemType.byMetadata(stack.getMetadata());

                if(t != null)
                {
                    return "tile." + t.name + ".ore";
                }

                return super.getUnlocalizedName(stack);
            }
        };
    }
}