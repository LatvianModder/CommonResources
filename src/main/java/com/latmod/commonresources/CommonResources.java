package com.latmod.commonresources;

import com.latmod.commonresources.block.BlockCR;
import com.latmod.commonresources.block.CRBlocks;
import com.latmod.commonresources.item.CRItems;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

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

    public static <K extends IForgeRegistryEntry<?>> K register(String s, K obj)
    {
        obj.setRegistryName(new ResourceLocation(MOD_ID, s));

        if(obj instanceof BlockCR)
        {
            ItemBlock ib = ((BlockCR) obj).createItemBlock();
            ib.setRegistryName(new ResourceLocation(MOD_ID, s));
            GameRegistry.register(ib);
        }

        return GameRegistry.register(obj);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        CRBlocks.init();
        CRItems.init();

        proxy.preInit();
    }
}