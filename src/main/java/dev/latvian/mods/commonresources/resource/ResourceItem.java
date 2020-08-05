package dev.latvian.mods.commonresources.resource;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

import java.util.function.Supplier;

/**
 * @author LatvianModder
 */
public class ResourceItem
{
	public final Resource<?> resource;
	public final ItemType type;
	public final String id;
	public final String displayName;
	public boolean modItem;
	private ITag.INamedTag<Item> itemTag;
	private ITag.INamedTag<Block> blockTag;
	public Supplier<Block> block;
	public Supplier<Item> item;
	public boolean disableRecipe;

	public ResourceItem(Resource<?> r, ItemType t)
	{
		resource = r;
		type = t;
		id = String.format(type.idFormat, resource.name);
		displayName = String.format(type.displayNameFormat, resource.displayName);
		modItem = false;
		itemTag = null;
		blockTag = null;
		block = null;
		item = null;
		disableRecipe = false;
	}

	public ITag.INamedTag<Item> getItemTag()
	{
		if (itemTag == null)
		{
			itemTag = ItemTags.makeWrapperTag(type.tagName + "/" + resource.name);
		}

		return itemTag;
	}

	public ITag.INamedTag<Block> getBlockTag()
	{
		if (blockTag == null)
		{
			blockTag = BlockTags.makeWrapperTag(type.tagName + "/" + resource.name);
		}

		return blockTag;
	}

	public boolean blockExists()
	{
		return block != null;
	}

	public boolean itemExists()
	{
		return item != null;
	}

	public Block getBlock()
	{
		return block == null ? Blocks.AIR : block.get();
	}

	public Item getItem()
	{
		return item == null ? Items.AIR : item.get();
	}

	@Override
	public String toString()
	{
		return displayName + " [" + (itemTag == null ? "-" : itemTag.getName()) + "]";
	}

	public String getPath()
	{
		return "other/" + resource.name + "/" + type.path;
	}

	public void created()
	{
		if (modItem)
		{
			Resource.ALL_MOD_ITEMS.add(this);
		}
	}
}