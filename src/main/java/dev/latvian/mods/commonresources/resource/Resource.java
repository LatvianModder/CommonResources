package dev.latvian.mods.commonresources.resource;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author LatvianModder
 */
public class Resource<IT extends ResourceItem>
{
	public static final Map<String, Resource<?>> ALL_MAP = new LinkedHashMap<>();
	public static final List<ResourceItem> ALL_MOD_ITEMS = new ArrayList<>();

	public final String name;
	public final String displayName;
	public final ItemType itemType;
	private final BiFunction<Resource<IT>, ItemType, IT> resourceItemFactory;
	public boolean storageSize3x3;
	public boolean dropWithFortune;
	public final Map<String, Integer> colorMap;

	public final IT item;
	public IT nugget;
	public IT dust;
	public IT rod;
	public IT gear;
	public IT storageBlock;
	public IT ore;

	public Resource(String n, String dn, ItemType it, BiFunction<Resource<IT>, ItemType, IT> f)
	{
		name = n;
		displayName = dn;
		itemType = it;
		resourceItemFactory = f;
		storageSize3x3 = true;
		dropWithFortune = false;
		colorMap = new HashMap<>();

		item = createResourceItem(itemType);
		nugget = createResourceItem(ItemType.NUGGET);
		dust = createResourceItem(ItemType.DUST);
		rod = createResourceItem(ItemType.ROD);
		gear = createResourceItem(ItemType.GEAR);
		storageBlock = createResourceItem(ItemType.STORAGE_BLOCK);
		ore = createResourceItem(ItemType.ORE);
	}

	public IT createResourceItem(ItemType type)
	{
		return resourceItemFactory.apply(this, type);
	}

	public Resource<IT> smallStorageSize()
	{
		storageSize3x3 = false;
		return this;
	}

	public Resource<IT> dropWithFortune()
	{
		dropWithFortune = true;
		return this;
	}

	public Resource<IT> item(Supplier<Item> i)
	{
		item.item = i;
		return this;
	}

	public Resource<IT> newItem()
	{
		return item(() -> Items.AIR);
	}

	public Resource<IT> nugget(Supplier<Item> i)
	{
		nugget.item = i;
		return this;
	}

	public Resource<IT> newNugget()
	{
		return nugget(() -> Items.AIR);
	}

	public Resource<IT> dust(Supplier<Item> i)
	{
		dust.item = i;
		return this;
	}

	public Resource<IT> newDust()
	{
		return dust(() -> Items.AIR);
	}

	public Resource<IT> gear(Supplier<Item> i)
	{
		gear.item = i;
		return this;
	}

	public Resource<IT> newGear()
	{
		return gear(() -> Items.AIR);
	}

	public Resource<IT> rod(Supplier<Item> i)
	{
		rod.item = i;
		return this;
	}

	public Resource<IT> newRod()
	{
		return rod(() -> Items.AIR);
	}

	public Resource<IT> storageBlock(Supplier<Item> i, Supplier<Block> b)
	{
		storageBlock.item = i;
		storageBlock.block = b;
		return this;
	}

	public Resource<IT> newStorageBlock()
	{
		return storageBlock(() -> Items.AIR, () -> Blocks.AIR);
	}

	public Resource<IT> ore(Supplier<Item> i, Supplier<Block> b)
	{
		ore.item = i;
		ore.block = b;
		return this;
	}

	public Resource<IT> newOre()
	{
		return ore(() -> Items.AIR, () -> Blocks.AIR);
	}

	public Resource<IT> newEverythingExceptOre()
	{
		return newItem().newNugget().newDust().newRod().newGear().newStorageBlock();
	}

	public Resource<IT> newEverything()
	{
		return newEverythingExceptOre().newOre();
	}

	public Resource<IT> color(String id, int col)
	{
		colorMap.put(id, 0xFFFFFF & col);
		return this;
	}

	public List<IT> getResourceItems()
	{
		return Arrays.asList(item, nugget, dust, rod, gear, storageBlock, ore);
	}
}