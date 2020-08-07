package dev.latvian.mods.commonresources.resource;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

/**
 * @author LatvianModder
 */
public final class ItemType
{
	public static ItemType create(String name)
	{
		return new ItemType(name);
	}

	public static final ItemType NUGGET = create("nugget").textureParts("shine", "shade_1", "shade_2", "shade_3", "shade_4", "border");
	public static final ItemType DUST = create("dust").textureParts().textureParts("shine", "shade_1", "shade_2", "shade_3", "shade_4", "border");
	public static final ItemType ROD = create("rod").textureParts().textureParts("shine", "shade_1", "shade_3", "shade_4");
	public static final ItemType GEAR = create("gear").textureParts("shade_1", "shade_2", "shade_3", "shade_4", "border");
	public static final ItemType PLATE = create("plate").textureParts("shine", "shade_1", "shade_2", "shade_3", "shade_4", "border");
	public static final ItemType STORAGE_BLOCK = create("storage_block").idFormat("%s_block").displayNameFormat("%s Block").block(true).textureParts("shade_1", "shade_2", "shade_3", "shade_4", "border");
	public static final ItemType ORE = create("ore").block(true).textureParts("stone", "shade_1", "shade_2", "shade_3", "shade_4");

	public final String name;
	public String idFormat;
	public String tagName;
	public String displayNameFormat;
	public String path;
	public boolean block;
	private ITag.INamedTag<Item> itemTag;
	private ITag.INamedTag<Block> blockTag;
	public String[] textureParts;

	private ItemType(String n)
	{
		name = n;
		idFormat = "%s_" + name;
		tagName = "forge:" + name + "s";
		displayNameFormat = "%s " + name.substring(0, 1).toUpperCase() + name.substring(1);
		path = name;
		block = false;
		textureParts = new String[0];
	}

	public ItemType copy(String name)
	{
		ItemType type = new ItemType(name);
		type.idFormat = idFormat;
		type.tagName = tagName;
		type.displayNameFormat = displayNameFormat;
		type.path = path;
		type.block = block;
		type.textureParts = new String[textureParts.length];
		System.arraycopy(textureParts, 0, type.textureParts, 0, textureParts.length);
		return type;
	}

	public ItemType idFormat(String f)
	{
		idFormat = f;
		return this;
	}

	public ItemType tagName(String t)
	{
		tagName = t;
		return this;
	}

	public ItemType displayNameFormat(String f)
	{
		displayNameFormat = f;
		return this;
	}

	public ItemType path(String n)
	{
		path = n;
		return this;
	}

	public ItemType block(boolean b)
	{
		block = b;
		return this;
	}

	public ItemType textureParts(String... parts)
	{
		textureParts = parts;
		return this;
	}

	public ITag.INamedTag<Item> getItemTag()
	{
		if (itemTag == null)
		{
			itemTag = ItemTags.makeWrapperTag(tagName);
		}

		return itemTag;
	}

	public ITag.INamedTag<Block> getBlockTag()
	{
		if (blockTag == null)
		{
			blockTag = BlockTags.makeWrapperTag(tagName);
		}

		return blockTag;
	}
}
