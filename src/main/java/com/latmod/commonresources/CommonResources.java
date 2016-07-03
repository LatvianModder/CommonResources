package com.latmod.commonresources;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    public static BlockMetals metals;
    public static ItemMaterials materials;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        metals = new BlockMetals();
        metals.setRegistryName(MOD_ID, "metals");
        GameRegistry.register(metals);

        ItemBlock itemBlock = new ItemBlockMetals(metals);
        itemBlock.setRegistryName(metals.getRegistryName());
        GameRegistry.register(itemBlock);

        materials = new ItemMaterials();
        materials.setRegistryName(MOD_ID, "mat");
        GameRegistry.register(materials);

        metals.init();
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