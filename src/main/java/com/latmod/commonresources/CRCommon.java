package com.latmod.commonresources;

import com.latmod.commonresources.block.BlockCR;
import com.latmod.commonresources.block.BlockGemBlocks;
import com.latmod.commonresources.block.BlockGemOres;
import com.latmod.commonresources.block.BlockMetalBlocks;
import com.latmod.commonresources.block.BlockMetalOres;
import com.latmod.commonresources.item.ItemHammer;
import com.latmod.commonresources.item.ItemMaterials;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class CRCommon
{
    public static final BlockMetalOres METAL_ORES = register("metal_ores", new BlockMetalOres());
    public static final BlockMetalBlocks METAL_BLOCKS = register("metal_blocks", new BlockMetalBlocks());
    public static final BlockGemOres GEM_ORES = register("gem_ores", new BlockGemOres());
    public static final BlockGemBlocks GEM_BLOCKS = register("gem_blocks", new BlockGemBlocks());
    public static final ItemMaterials MATERIALS = register("items", new ItemMaterials());
    public static final ItemHammer HAMMER = register("hammer", new ItemHammer());

    private static <E extends IForgeRegistryEntry> E register(String id, E e)
    {
        e.setRegistryName(new ResourceLocation(CommonResources.MOD_ID, id));
        GameRegistry.register(e);

        if(e instanceof BlockCR)
        {
            ItemBlock itemBlock = ((BlockCR) e).createItemBlock();
            itemBlock.setRegistryName(e.getRegistryName());
            GameRegistry.register(itemBlock);
        }

        return e;
    }

    public static void addRecipe(ItemStack out, Object... o)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(out, o));
    }

    public static void addShapelessRecipe(ItemStack out, Object... o)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(out, o));
    }

    public void initItems()
    {
        METAL_ORES.init();
        METAL_BLOCKS.init();
        GEM_ORES.init();
        GEM_BLOCKS.init();
        MATERIALS.init();
    }
}