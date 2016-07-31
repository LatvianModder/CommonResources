package com.latmod.commonresources;

import com.latmod.commonresources.item.GroupMatType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
                return CRCommon.MATERIALS;
            }

            @Override
            @SideOnly(Side.CLIENT)
            public int getIconItemDamage()
            {
                return CRCommon.MATERIALS.diamond.map.get(GroupMatType.GEAR).getMeta();
            }
        };

        CRConfig.load(new File(event.getModConfigurationDirectory(), "CommonResources.json"));
        proxy.initItems();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if(CRConfig.enable_crafting)
        {
            CRCommon.MATERIALS.loadRecipes();
            CRCommon.HAMMER.loadRecipes();
        }
    }
}