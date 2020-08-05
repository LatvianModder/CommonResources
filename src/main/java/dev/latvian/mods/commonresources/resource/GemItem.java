package dev.latvian.mods.commonresources.resource;

/**
 * @author LatvianModder
 */
public final class GemItem extends ResourceItem
{
	public GemItem(Resource<GemItem> g, ItemType t)
	{
		super(g, t);
	}

	public String getPath()
	{
		return "gem/" + resource.name + "/" + type.path;
	}
}