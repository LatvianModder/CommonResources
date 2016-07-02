package com.latmod.commonresources.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class ItemMaterials extends ItemCR
{
    public class Material
    {
        public final int meta;
        public final String id;
        public final String uname;
        public String oreName;

        public Material(int m, String s)
        {
            meta = m;
            id = s;
            uname = "commonresources.item." + s.replace('_', '.');
        }

        public ItemStack stack(int q)
        {
            return new ItemStack(ItemMaterials.this, q, meta);
        }

        public Material setOreName(String s)
        {
            oreName = s;
            return this;
        }
    }

    public final Map<Integer, Material> materials;

    public ItemMaterials()
    {
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.MATERIALS);

        materials = new LinkedHashMap<>();

        addMaterial(0, "ingot_copper").setOreName("ingotCopper");
        addMaterial(1, "nugget_copper").setOreName("nuggetCopper");
        addMaterial(2, "gear_copper").setOreName("gearCopper");

        addMaterial(10, "ingot_tin").setOreName("ingotTin");
        addMaterial(11, "nugget_tin").setOreName("nuggetTin");
        addMaterial(12, "gear_tin").setOreName("gearTin");

        addMaterial(20, "ingot_silver").setOreName("ingotSilver");
        addMaterial(21, "nugget_silver").setOreName("nuggetSilver");
        addMaterial(22, "gear_silver").setOreName("gearSilver");

        addMaterial(30, "ingot_lead").setOreName("ingotLead");
        addMaterial(31, "nugget_lead").setOreName("nuggetLead");
        addMaterial(32, "gear_lead").setOreName("gearLead");

        addMaterial(40, "ingot_bronze").setOreName("ingotBronze");
        addMaterial(41, "nugget_bronze").setOreName("nuggetBronze");
        addMaterial(42, "gear_stone").setOreName("gearStone");

        addMaterial(50, "ingot_steel").setOreName("ingotSteel");
        addMaterial(51, "nugget_steel").setOreName("nuggetSteel");
        addMaterial(52, "gear_steel").setOreName("gearSteel");

        addMaterial(100, "gem_ruby").setOreName("gemRuby");
        addMaterial(101, "gem_sapphire").setOreName("gemSapphire");
        addMaterial(102, "gem_peridot").setOreName("gemPeridot");

        addMaterial(105, "shard_ruby").setOreName("shardRuby");
        addMaterial(106, "shard_sapphire").setOreName("shardSapphire");
        addMaterial(107, "shard_peridot").setOreName("shardPeridot");

        addMaterial(110, "gear_iron").setOreName("gearIron");
        addMaterial(111, "gear_gold").setOreName("gearGold");
        addMaterial(112, "gear_diamond").setOreName("gearDiamond");
    }

    private Material addMaterial(int i, String s)
    {
        Material m = new Material(i, s);
        materials.put(i, m);
        return m;
    }

    public void init()
    {
        for(Material m : materials.values())
        {
            if(m.oreName != null)
            {
                OreDictionary.registerOre(m.oreName, new ItemStack(this, 1, m.meta));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        for(Material m : materials.values())
        {
            ModelLoader.setCustomModelResourceLocation(this, m.meta, new ModelResourceLocation(getRegistryName(), "variant=" + m.id));
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        Material m = materials.get(stack.getMetadata());

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
        for(Material m : materials.values())
        {
            subItems.add(new ItemStack(itemIn, 1, m.meta));
        }
    }
}