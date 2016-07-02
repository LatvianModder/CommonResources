package com.latmod.commonresources.item;

import com.latmod.commonresources.CommonResources;

/**
 * Created by LatvianModder on 02.07.2016.
 */
public class CRItems
{
    public static final ItemMaterials MATERIALS = CommonResources.register("mat", new ItemMaterials());

    public static void init()
    {
        MATERIALS.init();
    }
}