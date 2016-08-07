package com.latmod.commonresources.item;

import com.latmod.commonresources.CRCommon;
import com.latmod.commonresources.CommonResources;
import com.latmod.commonresources.block.EnumGemType;
import com.latmod.commonresources.block.EnumMetalType;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class ItemMaterials extends ItemCR
{
    public class Mat implements Comparable<Mat>
    {
        public final String id;
        public final String uname;
        public final String oreName;
        public final GroupMatType type;
        private final int meta;

        public Mat(int m, String s, String ore, GroupMatType t)
        {
            meta = m;
            id = s;
            uname = "item." + s.replace('_', '.');
            oreName = ore;
            type = t;
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
            int i = Integer.compare(type.ordinal(), o.type.ordinal());

            if(i == 0)
            {
                i = id.compareTo(o.id);
            }

            return i;
        }
    }

    public class FakeMat extends Mat
    {
        private final Item item;

        public FakeMat(int m, String s, String ore, Item i, GroupMatType t)
        {
            super(m, s, ore, t);
            item = i;
        }

        public FakeMat(String s, String ore, ItemStack is, GroupMatType t)
        {
            this(is.getMetadata(), s, ore, is.getItem(), t);
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
            map.put(type, addMaterial(new Mat(meta0 + type.ordinal(), id + '_' + type.name, ore, type)));
            return this;
        }

        public GroupMat add(GroupMatType type, String ore, ItemStack is)
        {
            map.put(type, new FakeMat(id + '_' + type.name, ore, is, type));
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
                    CRCommon.addRecipe(item.stack(1), "SSS", "SSS", "SSS", 'S', nugget.oreName);
                    CRCommon.addShapelessRecipe(nugget.stack(9), item.oreName);
                }

                if(map.containsKey(GroupMatType.DUST))
                {
                    GameRegistry.addSmelting(map.get(GroupMatType.DUST).stack(1), item.stack(1), 0F);
                }

                if(map.containsKey(GroupMatType.GEAR))
                {
                    if(this == stone)
                    {
                        CRCommon.addRecipe(map.get(GroupMatType.GEAR).stack(1), " S ", "SBS", " S ", 'S', "stickWood", 'B', "cobblestone");
                    }
                    else
                    {
                        CRCommon.addRecipe(map.get(GroupMatType.GEAR).stack(1), " I ", "IGI", " I ", 'I', item.oreName, 'G', "gearStone");
                    }
                }

                if(oreBlock != null)
                {
                    GameRegistry.addSmelting(oreBlock, item.stack(1), 1F);
                }

                if(block != null)
                {
                    CRCommon.addRecipe(block, "SSS", "SSS", "SSS", 'S', item.stack(1));
                    CRCommon.addShapelessRecipe(item.stack(9), block);
                }

                if(map.containsKey(GroupMatType.ROD))
                {
                    CRCommon.addRecipe(map.get(GroupMatType.ROD).stack(4), "I", "I", 'I', item.oreName);
                }
            }
        }
    }

    public class NewMetal extends GroupMat
    {
        public NewMetal(int i, EnumMetalType metal)
        {
            super(i, metal.name);
            add(GroupMatType.ITEM, "ingot" + metal.oreName);
            add(GroupMatType.NUGGET, "nugget" + metal.oreName);
            add(GroupMatType.DUST, "dust" + metal.oreName);
            add(GroupMatType.GEAR, "gear" + metal.oreName);

            setBlock(metal.stack(true, 1));

            if(metal.hasOre)
            {
                setOre(metal.stack(false, 1));
            }
        }
    }

    public class NewGem extends GroupMat
    {
        public NewGem(int i, EnumGemType gem)
        {
            super(i, gem.name);
            add(GroupMatType.ITEM, "gem" + gem.oreName);
            add(GroupMatType.NUGGET, "shard" + gem.oreName);

            setBlock(gem.stack(true, 1));

            if(gem.hasOre)
            {
                setOre(gem.stack(false, 1));
            }
        }
    }

    public final TIntObjectHashMap<Mat> materials;
    public final List<Mat> sortedMaterials;

    public final Map<EnumMetalType, NewMetal> new_metals;
    public final Map<EnumGemType, NewGem> new_gems;
    public final GroupMat stone, iron, gold, diamond, lapis;
    public final Mat dye_blue, silicon;

    public ItemMaterials()
    {
        setMaxDamage(0);
        setHasSubtypes(true);

        materials = new TIntObjectHashMap<>();

        new_metals = new EnumMap<>(EnumMetalType.class);

        for(EnumMetalType t : EnumMetalType.BLOCKS)
        {
            new_metals.put(t, new NewMetal(t.meta * 10, t));
        }

        new_gems = new EnumMap<>(EnumGemType.class);

        for(EnumGemType t : EnumGemType.BLOCKS)
        {
            new_gems.put(t, new NewGem(200 + t.meta * 10, t));
        }

        stone = new GroupMat(350, "stone").add(GroupMatType.ITEM, "stone", new ItemStack(Blocks.STONE)).add(GroupMatType.DUST, "dustStone").add(GroupMatType.GEAR, "gearStone").add(GroupMatType.ROD, "rodStone");
        iron = new GroupMat(360, "iron").add(GroupMatType.ITEM, "ingotIron", new ItemStack(Items.IRON_INGOT)).add(GroupMatType.NUGGET, "nuggetIron").add(GroupMatType.DUST, "dustIron").add(GroupMatType.GEAR, "gearIron").add(GroupMatType.ROD, "rodIron");
        gold = new GroupMat(370, "gold").add(GroupMatType.ITEM, "ingotGold", new ItemStack(Items.GOLD_INGOT)).add(GroupMatType.DUST, "dustGold").add(GroupMatType.GEAR, "gearGold").add(GroupMatType.ROD, "rodGold");
        diamond = new GroupMat(380, "diamond").add(GroupMatType.ITEM, "gemDiamond", new ItemStack(Items.DIAMOND)).add(GroupMatType.NUGGET, "nuggetDiamond").add(GroupMatType.DUST, "dustDiamond").add(GroupMatType.GEAR, "gearDiamond").add(GroupMatType.ROD, "rodDiamond");
        lapis = new GroupMat(390, "lapis").add(GroupMatType.ITEM, "gemLapis", new ItemStack(Items.DYE, 1, 4)).add(GroupMatType.NUGGET, "shardLapis").add(GroupMatType.DUST, "dustLapis");

        dye_blue = addMaterial(new Mat(1200, "dye_blue", "dyeBlue", GroupMatType.OTHER));
        silicon = addMaterial(new Mat(1210, "silicon", "itemSilicon", GroupMatType.OTHER));

        sortedMaterials = new ArrayList<>(materials.size());
    }

    private Mat addMaterial(Mat m)
    {
        materials.put(m.meta, m);
        return m;
    }

    public void init()
    {
        sortedMaterials.addAll(materials.valueCollection());
        Collections.sort(sortedMaterials, null);

        for(Mat m : sortedMaterials)
        {
            if(m.oreName != null)
            {
                OreDictionary.registerOre(m.oreName, new ItemStack(this, 1, m.meta));
            }
        }

        OreDictionary.registerOre("dye", dye_blue.stack(1));
    }

    public void loadRecipes()
    {
        for(NewMetal metal : new_metals.values())
        {
            metal.loadRecipes();
        }

        for(NewGem gem : new_gems.values())
        {
            gem.loadRecipes();
        }

        stone.loadRecipes();
        iron.loadRecipes();
        gold.loadRecipes();
        diamond.loadRecipes();
        lapis.loadRecipes();

        CRCommon.addShapelessRecipe(dye_blue.stack(2), "dyeLightBlue", "dyeBlack");
        CRCommon.addRecipe(new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()), "GGG", "GWG", "GGG", 'G', Blocks.TALLGRASS, 'W', Items.WATER_BUCKET);
        CRCommon.addShapelessRecipe(new_metals.get(EnumMetalType.BRONZE).map.get(GroupMatType.DUST).stack(4), "dustTin", "dustCopper", "dustCopper", "dustCopper");
        CRCommon.addShapelessRecipe(new_metals.get(EnumMetalType.INVAR).map.get(GroupMatType.DUST).stack(3), "dustIron", "dustIron", "dustNickel");
    }

    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        for(Mat m : sortedMaterials)
        {
            ModelLoader.setCustomModelResourceLocation(this, m.meta, new ModelResourceLocation(new ResourceLocation(CommonResources.MOD_ID, m.id), "inventory"));
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
        for(Mat m : sortedMaterials)
        {
            subItems.add(m.stack(1));
        }
    }
}