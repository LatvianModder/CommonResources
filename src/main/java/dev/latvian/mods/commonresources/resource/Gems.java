package dev.latvian.mods.commonresources.resource;

import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

/**
 * @author LatvianModder
 */
public class Gems
{
	public static final ItemType GEM = ItemType.create("gem").idFormat("%s").displayNameFormat("%s").tagName("forge:gems").textureParts("shine", "shade_1", "shade_2", "shade_3", "shade_4", "border");
	public static final ItemType GEM_ORE = ItemType.ORE.copy("ore").path("gem_ore").textureParts("stone", "shine", "shade_1", "shade_2", "shade_3", "shade_4", "border");
	public static final ItemType GEM_STORAGE_BLOCK = ItemType.STORAGE_BLOCK.copy("storage_block").path("gem_storage_block").textureParts("shine", "shade_1", "shade_2", "shade_3", "shade_4", "border");

	private static Resource<GemItem> add(String name, String displayName)
	{
		Resource<GemItem> gem = new Resource<>(name, displayName, GEM, GemItem::new);
		gem.ore = gem.createResourceItem(GEM_ORE);
		gem.storageBlock = gem.createResourceItem(GEM_STORAGE_BLOCK);
		Resource.ALL_MAP.put(name, gem);
		return gem;
	}

	public static Resource<GemItem> QUARTZ;
	public static Resource<GemItem> LAPIS;
	public static Resource<GemItem> DIAMOND;
	public static Resource<GemItem> EMERALD;

	public static Resource<GemItem> RUBY;
	public static Resource<GemItem> SAPPHIRE;
	public static Resource<GemItem> PERIDOT;
	public static Resource<GemItem> AMETHYST;

	public static void init()
	{
		QUARTZ = add("quartz", "Quartz")
				.smallStorageSize()
				.item(() -> Items.QUARTZ)
				.ore(() -> Items.NETHER_QUARTZ_ORE, () -> Blocks.NETHER_QUARTZ_ORE)
				.storageBlock(() -> Items.QUARTZ_BLOCK, () -> Blocks.QUARTZ_BLOCK)
				.newNugget()
				.newDust()
				.newRod()
				.newGear()
				.newPlate()
				.color("border", 0x897B73)
				.color("shine", 0xF7F5F2)
				.color("shade_1", 0xEAE5DE)
				.color("shade_2", 0xD4CABA)
				.color("shade_3", 0xC4B19B)
				.color("shade_4", 0xB3A091)
		;

		LAPIS = add("lapis", "Lapis Lazuli")
				.item(() -> Items.LAPIS_LAZULI)
				.ore(() -> Items.LAPIS_ORE, () -> Blocks.LAPIS_ORE)
				.storageBlock(() -> Items.LAPIS_BLOCK, () -> Blocks.LAPIS_BLOCK)
				.newNugget()
				.newDust()
				.newRod()
				.newGear()
				.newPlate()
				.color("border", 0x1A3D8F)
				.color("shine", 0x7497EA)
				.color("shade_1", 0x5A82E2)
				.color("shade_2", 0x345EC3)
				.color("shade_3", 0x154EA8)
				.color("shade_4", 0x12408B)
		;

		DIAMOND = add("diamond", "Diamond")
				.item(() -> Items.DIAMOND)
				.ore(() -> Items.DIAMOND_ORE, () -> Blocks.DIAMOND_ORE)
				.storageBlock(() -> Items.DIAMOND_BLOCK, () -> Blocks.DIAMOND_BLOCK)
				.newNugget()
				.newDust()
				.newRod()
				.newGear()
				.newPlate()
				.color("border", 0x11727A)
				.color("shine", 0xFFFFFF)
				.color("shade_1", 0xA1FBE8)
				.color("shade_2", 0x4AEDD9)
				.color("shade_3", 0x20C5B5)
				.color("shade_4", 0x1C919A)
		;

		EMERALD = add("emerald", "Emerald")
				.item(() -> Items.EMERALD)
				.ore(() -> Items.EMERALD_ORE, () -> Blocks.EMERALD_ORE)
				.storageBlock(() -> Items.EMERALD_BLOCK, () -> Blocks.EMERALD_BLOCK)
				.newNugget()
				.newDust()
				.newRod()
				.newGear()
				.newPlate()
				.color("border", 0x005300)
				.color("shine", 0xAFFDCD)
				.color("shade_1", 0x41F384)
				.color("shade_2", 0x17DD62)
				.color("shade_3", 0x00AA2C)
				.color("shade_4", 0x007B18)
		;

		RUBY = add("ruby", "Ruby")
				.smallStorageSize()
				.newEverything()
				.color("border", 0x420409)
				.color("shine", 0xE39BA2)
				.color("shade_1", 0xCB3747)
				.color("shade_2", 0xC12636)
				.color("shade_3", 0xB30D1F)
				.color("shade_4", 0x850815)
		;

		SAPPHIRE = add("sapphire", "Sapphire")
				.smallStorageSize()
				.newEverything()
				.color("border", 0x030B45)
				.color("shine", 0x9BA4E3)
				.color("shade_1", 0x374BCB)
				.color("shade_2", 0x263CC1)
				.color("shade_3", 0x0D25B3)
				.color("shade_4", 0x081985)
		;

		PERIDOT = add("peridot", "Peridot")
				.smallStorageSize()
				.newEverything()
				.color("border", 0x294702)
				.color("shine", 0xC6E898)
				.color("shade_1", 0x92D631)
				.color("shade_2", 0x84CC1F)
				.color("shade_3", 0x69AD03)
				.color("shade_4", 0x548E02)
		;

		AMETHYST = add("amethyst", "Amethyst")
				.smallStorageSize()
				.newEverything()
				.color("border", 0x3E0345)
				.color("shine", 0xDB9BE3)
				.color("shade_1", 0xB937CB)
				.color("shade_2", 0xAE26C1)
				.color("shade_3", 0x9E0DB3)
				.color("shade_4", 0x760885)
		;
	}
}