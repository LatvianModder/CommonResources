package com.latmod.commonresources.item;

import com.latmod.commonresources.CRConfig;
import com.latmod.commonresources.CRItems;
import com.latmod.commonresources.CommonResources;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 16.05.2016.
 */
public class ItemHammer extends Item
{
    public ItemHammer()
    {
        setCreativeTab(CommonResources.creativeTab);
        setUnlocalizedName("commonresources.hammer");
        setMaxDamage(Math.min(Math.max(0, CRConfig.hammer_max_uses), 16384));
        setMaxStackSize(1);
        setFull3D();
    }

    public void loadRecipes()
    {
        if(CRConfig.hammer_max_uses == 0)
        {
            return;
        }

        CRItems.addRecipe(new ItemStack(this, 1, 0),
                "OBO", " I ", " I ",
                'O', "obsidian",
                'B', "blockIron",
                'I', "ingotIron");

        ItemStack hammerItem = new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE);

        CRItems.addShapelessRecipe(new ItemStack(Blocks.GRAVEL), hammerItem, Blocks.COBBLESTONE);
        CRItems.addShapelessRecipe(new ItemStack(Blocks.SAND), hammerItem, Blocks.GRAVEL);
        CRItems.addShapelessRecipe(CRItems.MATERIALS.silicon.stack(1), hammerItem, "sand");

        Map<String, ItemStack> overrides = new HashMap<>();
        overrides.put("oreCoal", new ItemStack(Items.COAL, 2, 0));
        overrides.put("oreLapis", new ItemStack(Items.DYE, 4, 4));
        overrides.put("oreRedstone", new ItemStack(Items.REDSTONE, 4));
        overrides.put("oreDiamond", new ItemStack(Items.DIAMOND, 2));
        overrides.put("oreEmerald", new ItemStack(Items.EMERALD, 2));

        for(Map.Entry<String, ItemStack> entry : overrides.entrySet())
        {
            CRItems.addShapelessRecipe(entry.getValue(), hammerItem, entry.getKey());
        }

        List<String> list = Arrays.asList(OreDictionary.getOreNames());

        for(String s : list)
        {
            if(!overrides.containsKey(s))
            {
                if(s.startsWith("ingot"))
                {
                    ItemStack is = getFirstOre("dust" + s.substring(5));

                    if(is != null)
                    {
                        ItemStack is1 = is.copy();
                        is1.stackSize = 1;
                        CRItems.addShapelessRecipe(is1, hammerItem, s);
                    }
                }
                else if(s.startsWith("ore"))
                {
                    ItemStack is = getFirstOre("dust" + s.substring(3));

                    if(is != null)
                    {
                        ItemStack is1 = is.copy();
                        is1.stackSize = 2;
                        CRItems.addShapelessRecipe(is1, hammerItem, s);
                    }
                }
            }
        }
    }

    private ItemStack getFirstOre(String s)
    {
        List<ItemStack> stacks = OreDictionary.getOres(s);
        return stacks.isEmpty() ? null : stacks.get(0);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return CRConfig.hammer_max_uses < 0 || stack.getItemDamage() <= getMaxDamage(stack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if(hasContainerItem(itemStack))
        {
            return new ItemStack(this, 1, (CRConfig.hammer_max_uses < 0) ? 0 : (itemStack.getItemDamage() + 1));
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}