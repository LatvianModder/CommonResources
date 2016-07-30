package com.latmod.commonresources;

import com.latmod.commonresources.block.BlockBlocks;
import com.latmod.commonresources.block.BlockOres;
import com.latmod.commonresources.block.ItemBlockBlocks;
import com.latmod.commonresources.block.ItemBlockOres;
import com.latmod.commonresources.item.ItemHammer;
import com.latmod.commonresources.item.ItemMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by LatvianModder on 29.07.2016.
 */
public class CRItems
{
    public static final BlockOres ORES = new BlockOres();
    public static final BlockBlocks BLOCKS = new BlockBlocks();
    public static final ItemMaterials MATERIALS = new ItemMaterials();
    public static final ItemHammer HAMMER = new ItemHammer();

    private static <E extends IForgeRegistryEntry> E register(String id, E e)
    {
        e.setRegistryName(new ResourceLocation(CommonResources.MOD_ID, id));
        GameRegistry.register(e);
        return e;
    }

    public static void init()
    {
        register("ores", ORES);
        register("ores", new ItemBlockOres(ORES));

        register("blocks", BLOCKS);
        register("blocks", new ItemBlockBlocks(BLOCKS));

        register("items", MATERIALS);
        register("hammer", HAMMER);

        ORES.init();
        BLOCKS.init();
        MATERIALS.init();
    }

    public static void addRecipe(ItemStack out, Object... o)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(out, o));
    }

    public static void addShapelessRecipe(ItemStack out, Object... o)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(out, o));
    }
}