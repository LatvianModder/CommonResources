package dev.latvian.mods.commonresources.resource;

import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

/**
 * @author LatvianModder
 */
public class Metals
{
	public static final ItemType INGOT = ItemType.create("ingot").textureParts("shine", "shade_1", "shade_2", "shade_3", "shade_4", "border");

	public static Resource<MetalItem> add(String name, String displayName)
	{
		Resource<MetalItem> metal = new Resource<>(name, displayName, INGOT, MetalItem::new);
		Resource.ALL_MAP.put(name, metal);
		return metal;
	}

	public static Resource<MetalItem> IRON;
	public static Resource<MetalItem> GOLD;

	public static Resource<MetalItem> COPPER;
	public static Resource<MetalItem> TIN;
	public static Resource<MetalItem> SILVER;
	public static Resource<MetalItem> LEAD;
	public static Resource<MetalItem> NICKEL;
	public static Resource<MetalItem> PLATINUM;

	public static Resource<MetalItem> BRONZE;
	public static Resource<MetalItem> STEEL;
	public static Resource<MetalItem> INVAR;

	public static void init()
	{
		IRON = add("iron", "Iron")
				.item(() -> Items.IRON_INGOT)
				.nugget(() -> Items.IRON_NUGGET)
				.ore(() -> Items.IRON_ORE, () -> Blocks.IRON_ORE)
				.storageBlock(() -> Items.IRON_BLOCK, () -> Blocks.IRON_BLOCK)
				.newDust()
				.newRod()
				.newGear()
				.color("border", 0x5E5E5E)
				.color("shine", 0xFFFFFF)
				.color("shade_1", 0xD8D8D8)
				.color("shade_2", 0xA8A8A8)
				.color("shade_3", 0x828282)
				.color("shade_4", 0x727272)
		;

		GOLD = add("gold", "Gold")
				.item(() -> Items.GOLD_INGOT)
				.nugget(() -> Items.GOLD_NUGGET)
				.ore(() -> Items.GOLD_ORE, () -> Blocks.GOLD_ORE)
				.storageBlock(() -> Items.GOLD_BLOCK, () -> Blocks.GOLD_BLOCK)
				.newDust()
				.newRod()
				.newGear()
				.color("border", 0xB26411)
				.color("shine", 0xFFFDE0)
				.color("shade_1", 0xFDF55F)
				.color("shade_2", 0xFAD64A)
				.color("shade_3", 0xE9B115)
				.color("shade_4", 0xDC9613)
		;

		// New metals //

		COPPER = add("copper", "Copper")
				.newEverything()
				.color("border", 0x82573A)
				.color("shine", 0xFFDDC6)
				.color("shade_1", 0xFDB98D)
				.color("shade_2", 0xF7A66E)
				.color("shade_3", 0xD89260)
				.color("shade_4", 0xC88759)
		;

		TIN = add("tin", "Tin")
				.newEverything()
				.color("border", 0x8DA9BC)
				.color("shine", 0xD8EFFF)
				.color("shade_1", 0xC4E3F7)
				.color("shade_2", 0xB5D9F2)
				.color("shade_3", 0xABCBE1)
				.color("shade_4", 0xA6C3D8)
		;

		SILVER = add("silver", "Silver")
				.newEverything()
				.color("border", 0x809293)
				.color("shine", 0xFFFFFF)
				.color("shade_1", 0xDCE9EC)
				.color("shade_2", 0xC9E4E6)
				.color("shade_3", 0xC1DADC)
				.color("shade_4", 0xB5CDCF)
		;

		LEAD = add("lead", "Lead")
				.newEverything()
				.color("border", 0x52506F)
				.color("shine", 0xDBD8FF)
				.color("shade_1", 0xB1AEDF)
				.color("shade_2", 0x9C98D3)
				.color("shade_3", 0x8985B8)
				.color("shade_4", 0x7F7BAB)
		;

		NICKEL = add("nickel", "Nickel")
				.newEverything()
				.color("border", 0x857C4C)
				.color("shine", 0xFFFBE8)
				.color("shade_1", 0xFFF1A9)
				.color("shade_2", 0xFEEC93)
				.color("shade_3", 0xDBCD7F)
				.color("shade_4", 0xCDC176)
		;

		PLATINUM = add("platinum", "Platinum")
				.newEverything()
				.color("border", 0x5F8781)
				.color("shine", 0xDDF5F1)
				.color("shade_1", 0x98D7CE)
				.color("shade_2", 0x8DC7BE)
				.color("shade_3", 0x79ACA4)
				.color("shade_4", 0x6C9A93)
		;

		// New non-ore metals //

		BRONZE = add("bronze", "Bronze")
				.newEverythingExceptOre()
				.color("border", 0x8B6D30)
				.color("shine", 0xFFEEC9)
				.color("shade_1", 0xFFD87D)
				.color("shade_2", 0xFFCE5B)
				.color("shade_3", 0xE7B74E)
				.color("shade_4", 0xD5A948)
		;

		STEEL = add("steel", "Steel")
				.newEverythingExceptOre()
				.color("border", 0x424242)
				.color("shine", 0xB3B3B3)
				.color("shade_1", 0x979797)
				.color("shade_2", 0x767676)
				.color("shade_3", 0x606060)
				.color("shade_4", 0x505050)
		;

		INVAR = add("invar", "Invar")
				.newEverythingExceptOre()
				.color("border", 0xA5A5A5)
				.color("shine", 0xE5E5E5)
				.color("shade_1", 0xD4D4D4)
				.color("shade_2", 0xCCCCCC)
				.color("shade_3", 0xC0C0C0)
				.color("shade_4", 0xBBBBBB)
		;
	}
}