package com.latmod.commonresources.item;

/**
 * Created by LatvianModder on 06.07.2016.
 */
public enum GroupMatType
{
    ITEM,
    NUGGET,
    DUST,
    GEAR,
    ROD;

    public final int flag;
    public final String name;

    GroupMatType()
    {
        flag = 1 << ordinal();
        name = name().toLowerCase();
    }
}