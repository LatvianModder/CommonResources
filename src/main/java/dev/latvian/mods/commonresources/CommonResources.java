package dev.latvian.mods.commonresources;

import dev.latvian.mods.commonresources.resource.Gems;
import dev.latvian.mods.commonresources.resource.Metals;
import dev.latvian.mods.commonresources.resource.OtherResources;
import dev.latvian.mods.commonresources.resource.Resource;
import dev.latvian.mods.commonresources.resource.ResourceItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by LatvianModder on 02.07.2016.
 */
@Mod(CommonResources.MOD_ID)
public class CommonResources
{
	public static final String MOD_ID = "commonresources";
	public static final Logger LOGGER = LogManager.getLogger("Common Resources");

	public static CommonResources mod;

	public final DeferredRegister<Block> blocks;
	public final DeferredRegister<Item> items;

	public final ItemGroup itemGroup;

	public CommonResources()
	{
		mod = this;
		OtherResources.init();
		Metals.init();
		Gems.init();

		blocks = DeferredRegister.create(Block.class, MOD_ID);
		items = DeferredRegister.create(Item.class, MOD_ID);

		itemGroup = new ItemGroup(MOD_ID)
		{
			@Override
			@OnlyIn(Dist.CLIENT)
			public ItemStack createIcon()
			{
				return new ItemStack(Gems.RUBY.item.getItem());
			}
		};

		for (Resource<?> r : Resource.ALL_MAP.values())
		{
			for (ResourceItem i : r.getResourceItems())
			{
				boolean addBlock = false;

				if (i.block != null && i.block.get() == Blocks.AIR)
				{
					i.block = blocks.register(i.id, () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3F, 3F)));
					addBlock = true;
					i.modItem = true;
				}

				if (i.item != null && i.item.get() == Items.AIR)
				{
					if (addBlock)
					{
						i.item = items.register(i.id, () -> new BlockItem(i.block.get(), new Item.Properties().group(CommonResources.mod.itemGroup)));
					}
					else
					{
						i.item = items.register(i.id, () -> new Item(new Item.Properties().group(CommonResources.mod.itemGroup)));
					}

					i.modItem = true;
				}

				i.created();
			}
		}

		CommonResources.LOGGER.info(Resource.ALL_MOD_ITEMS.size() + " new Common Resources items registered");

		blocks.register(FMLJavaModLoadingContext.get().getModEventBus());
		items.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}