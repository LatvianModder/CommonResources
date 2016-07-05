package com.latmod.commonresources;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 02.07.2016.
 */
@SideOnly(Side.CLIENT)
public class CRClient extends CRCommon
{
    @Override
    public void preInit()
    {
        CommonResources.ores.loadModels();
        CommonResources.blocks.loadModels();
        CommonResources.materials.loadModels();
    }
}