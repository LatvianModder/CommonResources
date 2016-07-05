package com.latmod.commonresources;

import com.latmod.commonresources.block.BlockBlocks;
import com.latmod.commonresources.block.BlockOres;
import com.latmod.commonresources.block.ItemBlockBlocks;
import com.latmod.commonresources.block.ItemBlockOres;
import com.latmod.commonresources.item.GroupMatType;
import com.latmod.commonresources.item.ItemMaterials;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * Created by LatvianModder on 02.07.2016.
 */
@Mod(modid = CommonResources.MOD_ID, name = "Common Resources", version = "@VERSION@")
public class CommonResources
{
    public static final String MOD_ID = "commonresources";

    @Mod.Instance(CommonResources.MOD_ID)
    public static CommonResources inst;

    @SidedProxy(serverSide = "com.latmod.commonresources.CRCommon", clientSide = "com.latmod.commonresources.CRClient")
    public static CRCommon proxy;

    public static CreativeTabs creativeTab;
    public static BlockOres ores;
    public static BlockBlocks blocks;
    public static ItemMaterials materials;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        creativeTab = new CreativeTabs("commonresources")
        {
            @Nonnull
            @Override
            @SideOnly(Side.CLIENT)
            public Item getTabIconItem()
            {
                return materials;
            }

            @Override
            @SideOnly(Side.CLIENT)
            public int getIconItemDamage()
            {
                return materials.diamond.map.get(GroupMatType.GEAR).getMeta();
            }
        };

        CRConfig.load(new File(event.getModConfigurationDirectory(), "CommonResources.json"));

        ItemBlock itemBlock;

        ores = new BlockOres();
        ores.setRegistryName(MOD_ID, "ores");
        GameRegistry.register(ores);

        itemBlock = new ItemBlockOres(ores);
        itemBlock.setRegistryName(ores.getRegistryName());
        GameRegistry.register(itemBlock);

        blocks = new BlockBlocks();
        blocks.setRegistryName(MOD_ID, "blocks");
        GameRegistry.register(blocks);

        itemBlock = new ItemBlockBlocks(blocks);
        itemBlock.setRegistryName(blocks.getRegistryName());
        GameRegistry.register(itemBlock);

        materials = new ItemMaterials();
        materials.setRegistryName(MOD_ID, "items");
        GameRegistry.register(materials);

        ores.init();
        blocks.init();
        materials.init();

        proxy.preInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if(CRConfig.enable_crafting)
        {
            materials.loadRecipes();
        }
    }
}