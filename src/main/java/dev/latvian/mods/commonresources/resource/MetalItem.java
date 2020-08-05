package dev.latvian.mods.commonresources.resource;

/**
 * @author LatvianModder
 */
public final class MetalItem extends ResourceItem
{
	public MetalItem(Resource<MetalItem> m, ItemType t)
	{
		super(m, t);
	}

	public String getPath()
	{
		return "metal/" + resource.name + "/" + type.path;
	}
}