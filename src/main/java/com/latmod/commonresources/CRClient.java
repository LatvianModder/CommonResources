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
    public void initItems()
    {
        super.initItems();

        METAL_ORES.loadModels();
        METAL_BLOCKS.loadModels();
        GEM_ORES.loadModels();
        GEM_BLOCKS.loadModels();
        MATERIALS.loadModels();
        HAMMER.loadModels();
    }
}