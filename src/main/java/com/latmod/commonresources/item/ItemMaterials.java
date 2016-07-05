package com.latmod.commonresources.item;

import com.latmod.commonresources.CommonResources;
import com.latmod.commonresources.block.EnumMetalType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class ItemMaterials extends Item
{
    public class Mat implements Comparable<Mat>
    {
        public final String id;
        public final String uname;
        public final String oreName;
        private final int meta;

        public Mat(int m, String s, String ore)
        {
            meta = m;
            id = s;
            oreName = ore;
            uname = "item." + s.replace('_', '.');
        }

        public Item getItem()
        {
            return ItemMaterials.this;
        }

        public int getMeta()
        {
            return meta;
        }

        public final ItemStack stack(int q)
        {
            return new ItemStack(getItem(), q, getMeta());
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

    public class FakeMat extends Mat
    {
        private final Item item;

        public FakeMat(int m, String s, String ore, Item i)
        {
            super(m, s, ore);
            item = i;
        }

        public FakeMat(String s, String ore, ItemStack is)
        {
            this(is.getMetadata(), s, ore, is.getItem());
        }

        @Override
        public Item getItem()
        {
            return item;
        }
    }

    public class GroupMat
    {
        public final EnumMap<GroupMatType, Mat> map;
        private final int meta0;
        private final String id;
        public ItemStack oreBlock, block;

        public GroupMat(int m0, String s)
        {
            meta0 = m0;
            id = s;
            map = new EnumMap<>(GroupMatType.class);
        }

        public GroupMat setOre(ItemStack is)
        {
            oreBlock = is;
            return this;
        }

        public GroupMat setBlock(ItemStack is)
        {
            block = is;
            return this;
        }

        public GroupMat add(GroupMatType type, String ore)
        {
            map.put(type, addMaterial(new Mat(meta0 + type.ordinal(), id + '_' + type.name, ore)));
            return this;
        }

        public GroupMat add(GroupMatType type, String ore, ItemStack is)
        {
            map.put(type, new FakeMat(id + '_' + type.name, ore, is));
            return this;
        }

        public void loadRecipes()
        {
            Mat item = map.get(GroupMatType.ITEM);

            if(item != null)
            {
                if(map.containsKey(GroupMatType.NUGGET))
                {
                    Mat nugget = map.get(GroupMatType.NUGGET);
                    addRecipe(item.stack(1), "SSS", "SSS", "SSS", 'S', nugget.oreName);
                    addShapelessRecipe(nugget.stack(9), item.oreName);
                }

                if(map.containsKey(GroupMatType.DUST))
                {
                    GameRegistry.addSmelting(map.get(GroupMatType.DUST).stack(1), item.stack(1), 0F);
                }

                if(map.containsKey(GroupMatType.GEAR))
                {
                    if(this == stone)
                    {
                        addRecipe(map.get(GroupMatType.GEAR).stack(1), " S ", "SBS", " S ", 'S', "stickWood", 'B', "cobblestone");
                    }
                    else
                    {
                        addRecipe(map.get(GroupMatType.GEAR).stack(1), " I ", "IGI", " I ", 'I', item.oreName, 'G', "gearStone");
                    }
                }

                if(oreBlock != null)
                {
                    GameRegistry.addSmelting(oreBlock, item.stack(1), 1F);
                }

                if(block != null)
                {
                    addRecipe(block, "SSS", "SSS", "SSS", 'S', item.stack(1));
                    addShapelessRecipe(item.stack(9), block);
                }

                if(map.containsKey(GroupMatType.ROD))
                {
                    addRecipe(map.get(GroupMatType.ROD).stack(4), "I", "I", 'I', item.oreName);
                }
            }
        }
    }

    public class NewMetal extends GroupMat
    {
        public NewMetal(int i, String s, String ore)
        {
            super(i, s);
            add(GroupMatType.ITEM, "ingot" + ore);
            add(GroupMatType.NUGGET, "nugget" + ore);
            add(GroupMatType.DUST, "dust" + ore);
            add(GroupMatType.GEAR, "gear" + ore);
        }
    }

    public class NewGem extends GroupMat
    {
        public NewGem(int i, String s, String ore)
        {
            super(i, s);
            add(GroupMatType.ITEM, "gem" + ore);
            add(GroupMatType.NUGGET, "shard" + ore);
            //add(GroupMatType.DUST, "dust" + ore);
            //add(GroupMatType.GEAR, "gear" + ore);
        }
    }

    public final Map<Integer, Mat> materials;

    public final GroupMat copper, tin, silver, lead, bronze, steel;
    public final GroupMat ruby, sapphire, peridot;
    public final GroupMat stone, iron, gold, diamond, lapis;

    public ItemMaterials()
    {
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(CommonResources.creativeTab);

        materials = new LinkedHashMap<>();

        copper = new NewMetal(0, "copper", "Copper").setOre(EnumMetalType.COPPER.stack(false, 1)).setBlock(EnumMetalType.COPPER.stack(true, 1));
        tin = new NewMetal(10, "tin", "Tin").setOre(EnumMetalType.TIN.stack(false, 1)).setBlock(EnumMetalType.TIN.stack(true, 1));
        silver = new NewMetal(20, "silver", "Silver").setOre(EnumMetalType.SILVER.stack(false, 1)).setBlock(EnumMetalType.SILVER.stack(true, 1));
        lead = new NewMetal(30, "lead", "Lead").setOre(EnumMetalType.LEAD.stack(false, 1)).setBlock(EnumMetalType.LEAD.stack(true, 1));
        bronze = new NewMetal(40, "bronze", "Bronze").setBlock(EnumMetalType.BRONZE.stack(true, 1));
        steel = new NewMetal(50, "steel", "Steel").setBlock(EnumMetalType.STEEL.stack(true, 1));
        //nickel
        //platinum
        //invar

        ruby = new NewGem(200, "ruby", "Ruby").setOre(EnumMetalType.RUBY.stack(false, 1)).setBlock(EnumMetalType.RUBY.stack(true, 1));
        sapphire = new NewGem(210, "sapphire", "Sapphire").setOre(EnumMetalType.SAPPHIRE.stack(false, 1)).setBlock(EnumMetalType.SAPPHIRE.stack(true, 1));
        peridot = new NewGem(220, "peridot", "Peridot").setOre(EnumMetalType.PERIDOT.stack(false, 1)).setBlock(EnumMetalType.PERIDOT.stack(true, 1));

        stone = new GroupMat(350, "stone").add(GroupMatType.ITEM, "stone", new ItemStack(Blocks.STONE)).add(GroupMatType.DUST, "dustStone").add(GroupMatType.GEAR, "gearStone").add(GroupMatType.ROD, "rodStone");
        iron = new GroupMat(360, "iron").add(GroupMatType.ITEM, "ingotIron", new ItemStack(Items.IRON_INGOT)).add(GroupMatType.NUGGET, "nuggetIron").add(GroupMatType.DUST, "dustIron").add(GroupMatType.GEAR, "gearIron").add(GroupMatType.ROD, "rodIron");
        gold = new GroupMat(370, "gold").add(GroupMatType.ITEM, "ingotGold", new ItemStack(Items.GOLD_INGOT)).add(GroupMatType.DUST, "dustGold").add(GroupMatType.GEAR, "gearGold").add(GroupMatType.ROD, "rodGold");
        diamond = new GroupMat(380, "diamond").add(GroupMatType.ITEM, "gemDiamond", new ItemStack(Items.DIAMOND)).add(GroupMatType.NUGGET, "nuggetDiamond").add(GroupMatType.DUST, "dustDiamond").add(GroupMatType.GEAR, "gearDiamond").add(GroupMatType.ROD, "rodDiamond");
        lapis = new GroupMat(390, "lapis").add(GroupMatType.ITEM, "gemLapis", new ItemStack(Items.DYE, 1, 4)).add(GroupMatType.NUGGET, "shardLapis").add(GroupMatType.DUST, "dustLapis");
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

        stone.loadRecipes();
        iron.loadRecipes();
        gold.loadRecipes();
        diamond.loadRecipes();
        lapis.loadRecipes();
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
            subItems.add(m.stack(1));
        }
    }
}