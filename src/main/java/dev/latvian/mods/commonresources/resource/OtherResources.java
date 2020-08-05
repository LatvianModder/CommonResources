package dev.latvian.mods.commonresources.resource;

import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

/**
 * @author LatvianModder
 */
public class OtherResources
{
	private static Resource<ResourceItem> add(String name, String displayName, ItemType itemType)
	{
		Resource<ResourceItem> gem = new Resource<>(name, displayName, itemType, ResourceItem::new);
		Resource.ALL_MAP.put(name, gem);
		return gem;
	}

	public static Resource<ResourceItem> STONE;
	public static Resource<ResourceItem> WOODEN;
	public static Resource<ResourceItem> REDSTONE;
	public static Resource<ResourceItem> GLOWSTONE;

	public static void init()
	{
		STONE = add("stone", "Stone", ItemType.create("item").tagName("forge:cobblestone"))
				.item(() -> Items.COBBLESTONE)
				.newDust()
				.newRod()
				.newGear()
		;

		STONE.rod.disableRecipe = true;

		WOODEN = add("wooden", "Wooden", ItemType.create("item").tagName("minecraft:logs"))
				.rod(() -> Items.STICK)
				.newGear()
		;

		REDSTONE = add("redstone", "Redstone", ItemType.create("item"))
				.ore(() -> Items.REDSTONE_ORE, () -> Blocks.REDSTONE_ORE)
				.storageBlock(() -> Items.REDSTONE_BLOCK, () -> Blocks.REDSTONE_BLOCK)
				.dust(() -> Items.REDSTONE)
		;

		GLOWSTONE = add("glowstone", "Glowstone", ItemType.create("item"))
				.dust(() -> Items.GLOWSTONE_DUST)
				.smallStorageSize()
		;
	}
}