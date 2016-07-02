package com.latmod.commonresources;

import com.latmod.commonresources.block.CRBlocks;
import com.latmod.commonresources.item.CRItems;
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
        CRBlocks.METALS.loadModels();

        CRItems.MATERIALS.loadModels();
    }
}