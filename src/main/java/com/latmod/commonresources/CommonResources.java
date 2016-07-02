package com.latmod.commonresources;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by LatvianModder on 02.07.2016.
 */
@Mod(modid = CommonResources.MOD_ID, name = "Common Resources", version = "@VERSION@")
public class CommonResources
{
    public static final String MOD_ID = "commonresources";

    @Mod.Instance(CommonResources.MOD_ID)
    public static CommonResources inst;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("Common Resources mod loaded");
    }
}