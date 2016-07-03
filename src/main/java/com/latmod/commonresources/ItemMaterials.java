package com.latmod.commonresources;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class ItemMaterials extends Item
{
    public class Mat implements Comparable<Mat>
    {
        public final int meta;
        public final String id;
        public final String uname;
        public final String oreName;
        public Consumer<Mat> recipesHandler;

        public Mat(int m, String s, String ore)
        {
            meta = m;
            id = s;
            oreName = ore;
            uname = "commonresources.item." + s.replace('_', '.');
        }

        public ItemStack stack(int q)
        {
            return new ItemStack(ItemMaterials.this, q, meta);
        }

        public Mat setRecipesHandler(Consumer<Mat> r)
        {
            recipesHandler = r;
            return this;
        }

        public int hashCode()
        {
            return meta;
        }

        public String toString()
        {
            return id;
        }

        @Override
        public int compareTo(@Nonnull Mat o)
        {
            return id.compareTo(o.id);
        }
    }

    public class Metal
    {
        public final Mat ingot, nugget, gear, dust;
        public ItemStack oreBlock, block;

        public Metal(int i, String s, String ore, ItemStack is1, ItemStack is2)
        {
            ingot = addMaterial(new Mat(i, "ingot_" + s, "ingot" + ore));
            nugget = addMaterial(new Mat(i + 1, "nugget_" + s, "nugget" + ore));
            gear = addMaterial(new Mat(i + 2, "gear_" + s, "gear" + ore));
            dust = addMaterial(new Mat(i + 3, "dust_" + s, "dust" + ore));
            oreBlock = is1;
            block = is2;
        }

        public void loadRecipes()
        {
            addRecipe(ingot.stack(1), "SSS", "SSS", "SSS", 'S', nugget.oreName);
            addShapelessRecipe(nugget.stack(9), ingot.oreName);

            GameRegistry.addSmelting(dust.stack(1), ingot.stack(1), 0F);

            addRecipe(gear.stack(1), " I ", "IGI", " I ", 'I', ingot.oreName, 'G', "gearStone");

            if(oreBlock != null)
            {
                GameRegistry.addSmelting(oreBlock, ingot.stack(1), 1F);
            }

            if(block != null)
            {
                addRecipe(block, "SSS", "SSS", "SSS", 'S', ingot.stack(1));
                addShapelessRecipe(ingot.stack(9), block);
            }
        }
    }

    public class Gem
    {
        public final Mat gem, shard;
        public ItemStack oreBlock, block;

        public Gem(int i, String s, String ore, ItemStack is1, ItemStack is2)
        {
            gem = addMaterial(new Mat(i, "gem_" + s, "gem" + ore));
            shard = addMaterial(new Mat(i + 5, "shard_" + s, "shard" + ore));
            oreBlock = is1;
            block = is2;
        }

        public void loadRecipes()
        {
            addRecipe(gem.stack(1), "SSS", "SSS", "SSS", 'S', shard.oreName);
            addShapelessRecipe(shard.stack(9), gem.oreName);

            if(oreBlock != null)
            {
                GameRegistry.addSmelting(oreBlock, gem.stack(1), 1F);
            }

            if(block != null)
            {
                addRecipe(block, "SSS", "SSS", "SSS", 'S', gem.stack(1));
                addShapelessRecipe(gem.stack(9), block);
            }
        }
    }

    public final Map<Integer, Mat> materials;

    public final Metal copper, tin, silver, lead, bronze, steel;
    public final Gem ruby, sapphire, peridot;
    public final Mat gear_stone, gear_iron, gear_gold, gear_diamond;
    public final Mat dust_stone, dust_iron, dust_gold, dust_diamond, dust_lapis;
    public final Mat rod_iron;

    public ItemMaterials()
    {
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.MATERIALS);

        materials = new LinkedHashMap<>();

        copper = new Metal(0, "copper", "Copper", BlockMetals.EnumType.ORE_COPPER.stack(1), BlockMetals.EnumType.BLOCK_COPPER.stack(1));
        tin = new Metal(10, "tin", "Tin", BlockMetals.EnumType.ORE_TIN.stack(1), BlockMetals.EnumType.BLOCK_TIN.stack(1));
        silver = new Metal(20, "silver", "Silver", BlockMetals.EnumType.ORE_SILVER.stack(1), BlockMetals.EnumType.BLOCK_SILVER.stack(1));
        lead = new Metal(30, "lead", "Lead", BlockMetals.EnumType.ORE_LEAD.stack(1), BlockMetals.EnumType.BLOCK_LEAD.stack(1));
        bronze = new Metal(40, "bronze", "Bronze", null, BlockMetals.EnumType.BLOCK_BRONZE.stack(1));
        steel = new Metal(50, "steel", "Steel", null, BlockMetals.EnumType.BLOCK_STEEL.stack(1));

        ruby = new Gem(100, "ruby", "Ruby", BlockMetals.EnumType.ORE_RUBY.stack(1), BlockMetals.EnumType.BLOCK_RUBY.stack(1));
        sapphire = new Gem(101, "sapphire", "Sapphire", BlockMetals.EnumType.ORE_SAPPHIRE.stack(1), BlockMetals.EnumType.BLOCK_SAPPHIRE.stack(1));
        peridot = new Gem(102, "peridot", "Peridot", BlockMetals.EnumType.ORE_PERIDOT.stack(1), BlockMetals.EnumType.BLOCK_PERIDOT.stack(1));

        gear_stone = addMaterial(new Mat(110, "gear_stone", "gearStone"));
        gear_iron = addMaterial(new Mat(111, "gear_iron", "gearIron"));
        gear_gold = addMaterial(new Mat(112, "gear_gold", "gearGold"));
        gear_diamond = addMaterial(new Mat(113, "gear_diamond", "gearDiamond"));

        dust_stone = addMaterial(new Mat(120, "dust_stone", "dustStone"));
        dust_iron = addMaterial(new Mat(121, "dust_iron", "dustIron"));
        dust_gold = addMaterial(new Mat(122, "dust_gold", "dustGold"));
        dust_diamond = addMaterial(new Mat(123, "dust_diamond", "dustDiamond"));
        dust_lapis = addMaterial(new Mat(124, "dust_lapis", "dustGold"));

        rod_iron = addMaterial(new Mat(125, "rod_iron", "rodIron"));
    }

    public static void addRecipe(ItemStack out, Object... o)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(out, o));
    }

    public static void addShapelessRecipe(ItemStack out, Object... o)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(out, o));
    }

    private Mat addMaterial(Mat m)
    {
        materials.put(m.meta, m);
        return m;
    }

    public void init()
    {
        for(Mat m : materials.values())
        {
            if(m.oreName != null)
            {
                OreDictionary.registerOre(m.oreName, new ItemStack(this, 1, m.meta));
            }
        }
    }

    public void loadRecipes()
    {
        copper.loadRecipes();
        tin.loadRecipes();
        silver.loadRecipes();
        lead.loadRecipes();
        bronze.loadRecipes();
        steel.loadRecipes();

        ruby.loadRecipes();
        sapphire.loadRecipes();
        peridot.loadRecipes();

        addRecipe(gear_stone.stack(1), " S ", "SBS", " S ", 'S', "stickWood", 'B', "cobblestone");
        addRecipe(gear_iron.stack(1), " I ", "IGI", " I ", 'I', "ingotIron", 'G', "gearStone");
        addRecipe(gear_gold.stack(1), " I ", "IGI", " I ", 'I', "ingotGold", 'G', "gearStone");
        addRecipe(gear_diamond.stack(1), " I ", "IGI", " I ", 'I', "gemDiamond", 'G', "gearStone");

        addRecipe(rod_iron.stack(4), "I", "I", 'I', "ingotIron");
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        for(Mat m : materials.values())
        {
            ModelLoader.setCustomModelResourceLocation(this, m.meta, new ModelResourceLocation(getRegistryName(), "variant=" + m.id));
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        Mat m = materials.get(stack.getMetadata());

        if(m != null)
        {
            return m.uname;
        }

        return "item.null";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for(Mat m : materials.values())
        {
            subItems.add(new ItemStack(itemIn, 1, m.meta));
        }
    }
}