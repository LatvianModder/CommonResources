package dev.latvian.mods.commonresources;

import dev.latvian.mods.commonresources.resource.Resource;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.IResource;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;
import java.util.Collections;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = CommonResources.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CommonResourcesClientDataGen
{
	public static final String MOD_ID = CommonResources.MOD_ID;

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();

		if (event.includeClient())
		{
			gen.addProvider(new Lang(gen));
			ExistingFileHelper fileHelper = new ExistingFileHelper(Collections.emptyList(), false)
			{
				@Override
				public IResource getResource(ResourceLocation loc, ResourcePackType type, String pathSuffix, String pathPrefix) throws IOException
				{
					return event.getExistingFileHelper().getResource(loc, type, pathSuffix, pathPrefix);
				}
			};

			gen.addProvider(new CombinedTextures(gen, event.getExistingFileHelper()));
			gen.addProvider(new BlockStates(gen, fileHelper));
			gen.addProvider(new ItemModels(gen, fileHelper));
			gen.addProvider(new BlockModels(gen, fileHelper));
		}
	}

	private static class BlockStates extends BlockStateProvider
	{
		public BlockStates(DataGenerator gen, ExistingFileHelper existingFileHelper)
		{
			super(gen, MOD_ID, existingFileHelper);
		}

		@Override
		protected void registerStatesAndModels()
		{
			Resource.ALL_MOD_ITEMS.stream().filter(i -> i.type.block).forEach(i -> simpleBlock(i.block.get(), new ModelFile.UncheckedModelFile(new ResourceLocation(MOD_ID, i.getPath()))));
		}
	}

	private static class ItemModels extends ItemModelProvider
	{
		public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper)
		{
			super(generator, MOD_ID, existingFileHelper);
		}

		@Override
		protected void registerModels()
		{
			Resource.ALL_MOD_ITEMS.forEach(i -> {
				if (i.type.block)
				{
					getBuilder(i.id).parent(new ModelFile.UncheckedModelFile(new ResourceLocation(MOD_ID, i.getPath())));
				}
				else
				{
					getBuilder(i.id)
							.parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
							.texture("layer0", new ResourceLocation(MOD_ID, i.getPath()))
					;
				}
			});
		}
	}

	private static class BlockModels extends BlockModelProvider
	{
		public BlockModels(DataGenerator generator, ExistingFileHelper existingFileHelper)
		{
			super(generator, MOD_ID, existingFileHelper);
		}

		@Override
		protected void registerModels()
		{
			Resource.ALL_MOD_ITEMS.stream().filter(i -> i.type.block).forEach(i -> getBuilder(i.getPath())
					.parent(new ModelFile.UncheckedModelFile(mcLoc("block/cube_all")))
					.texture("all", new ResourceLocation(MOD_ID, i.getPath()))
			);
		}
	}

	private static class Lang extends LanguageProvider
	{
		public Lang(DataGenerator gen)
		{
			super(gen, MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations()
		{
			add("itemGroup." + MOD_ID, "Common Resources");
			Resource.ALL_MOD_ITEMS.forEach(i -> addItem(i.item, i.displayName));
		}
	}

	private static class CombinedTextures extends CombinedTextureProvider
	{
		public CombinedTextures(DataGenerator gen, ExistingFileHelper existingFileHelper)
		{
			super(gen, existingFileHelper, MOD_ID);
		}

		@Override
		public void registerTextures()
		{
			Resource.ALL_MOD_ITEMS.forEach(i -> {
				if (i.type.textureParts.length > 0 && !i.resource.colorMap.isEmpty())
				{
					CombinedTexture combinedTexture = createTexture(i.getPath(), "base/" + i.type.path);

					for (String s : i.type.textureParts)
					{
						combinedTexture.layerColor(s, i.resource.colorMap.getOrDefault(s, 0xFFFFFF));
					}
				}
			});
		}
	}
}
