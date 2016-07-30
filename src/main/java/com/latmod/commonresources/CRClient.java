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
        CRItems.ORES.loadModels();
        CRItems.BLOCKS.loadModels();
        CRItems.MATERIALS.loadModels();
        CRItems.HAMMER.loadModels();
    }
}