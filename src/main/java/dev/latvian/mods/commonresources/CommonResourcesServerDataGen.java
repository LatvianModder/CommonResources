package dev.latvian.mods.commonresources;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.latvian.mods.commonresources.resource.ItemType;
import dev.latvian.mods.commonresources.resource.Resource;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = CommonResources.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonResourcesServerDataGen
{
	public static final String MOD_ID = CommonResources.MOD_ID;

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();

		if (event.includeServer())
		{
			BlockTags t = new BlockTags(gen);
			gen.addProvider(t);
			gen.addProvider(new ItemTags(gen, t));
			gen.addProvider(new Recipes(gen));
			gen.addProvider(new LootTables(gen));
		}
	}

	private static class BlockTags extends BlockTagsProvider
	{
		public BlockTags(DataGenerator generatorIn)
		{
			super(generatorIn);
		}

		@Override
		protected void registerTags()
		{
			Resource.ALL_MOD_ITEMS.stream().filter(i -> i.type.block).forEach(i -> {
				getOrCreateBuilder(i.getBlockTag()).addItemEntry(i.block.get());
				getOrCreateBuilder(i.type.getBlockTag()).addItemEntry(i.block.get());
			});
		}
	}

	private static class ItemTags extends ItemTagsProvider
	{
		public ItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider)
		{
			super(dataGenerator, blockTagProvider);
		}

		@Override
		protected void registerTags()
		{
			Resource.ALL_MOD_ITEMS.forEach(i -> {
				getOrCreateBuilder(i.getItemTag()).addItemEntry(i.item.get());
				getOrCreateBuilder(i.type.getItemTag()).addItemEntry(i.item.get());
			});
		}
	}

	private static class Recipes extends RecipeProvider
	{
		public Recipes(DataGenerator generatorIn)
		{
			super(generatorIn);
		}

		@Override
		protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
		{
			Resource.ALL_MAP.values().forEach(r -> {
				if ((r.item.modItem || r.ore.modItem) && !r.item.disableRecipe && r.ore.itemExists())
				{
					CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(r.ore.getItemTag()), r.item.getItem(), 0.1F, 200)
							.addCriterion("has_ore", hasItem(r.ore.getItemTag()))
							.build(consumer, new ResourceLocation(MOD_ID, "items/ore_smelting/" + r.name));

					CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(r.ore.getItemTag()), r.item.getItem(), 0.1F, 100)
							.addCriterion("has_ore", hasItem(r.ore.getItemTag()))
							.build(consumer, new ResourceLocation(MOD_ID, "items/ore_blasting/" + r.name));
				}

				if ((r.item.modItem || r.dust.modItem) && !r.item.disableRecipe)
				{
					CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(r.dust.getItemTag()), r.item.getItem(), 0.1F, 200)
							.addCriterion("has_dust", hasItem(r.dust.getItemTag()))
							.build(consumer, new ResourceLocation(MOD_ID, "items/dust_smelting/" + r.name));

					CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(r.dust.getItemTag()), r.item.getItem(), 0.1F, 100)
							.addCriterion("has_dust", hasItem(r.dust.getItemTag()))
							.build(consumer, new ResourceLocation(MOD_ID, "items/dust_blasting/" + r.name));
				}

				if ((r.item.modItem || r.storageBlock.modItem) && !r.item.disableRecipe)
				{
					ShapelessRecipeBuilder.shapelessRecipe(r.item.getItem(), r.storageSize3x3 ? 9 : 4)
							.addCriterion("has_block", hasItem(r.storageBlock.getItemTag()))
							.addIngredient(r.storageBlock.getItemTag())
							.build(consumer, new ResourceLocation(MOD_ID, "items/storage_blocks/" + r.name));
				}

				if ((r.item.modItem || r.nugget.modItem) && !r.item.disableRecipe)
				{
					ShapedRecipeBuilder.shapedRecipe(r.item.getItem())
							.addCriterion("has_nugget", hasItem(r.nugget.getItemTag()))
							.patternLine("NNN")
							.patternLine("NNN")
							.patternLine("NNN")
							.key('N', r.nugget.getItemTag())
							.build(consumer, new ResourceLocation(MOD_ID, "items/nuggets/" + r.name));
				}

				if (r.storageBlock.modItem && !r.storageBlock.disableRecipe)
				{
					if (r.storageSize3x3)
					{
						ShapedRecipeBuilder.shapedRecipe(r.storageBlock.getItem())
								.addCriterion("has_item", hasItem(r.item.getItemTag()))
								.patternLine("III")
								.patternLine("III")
								.patternLine("III")
								.key('I', r.item.getItemTag())
								.build(consumer, new ResourceLocation(MOD_ID, "storage_blocks/" + r.name));
					}
					else
					{
						ShapedRecipeBuilder.shapedRecipe(r.storageBlock.getItem())
								.addCriterion("has_item", hasItem(r.item.getItemTag()))
								.patternLine("II")
								.patternLine("II")
								.key('I', r.item.getItemTag())
								.build(consumer, new ResourceLocation(MOD_ID, "storage_blocks/" + r.name));
					}
				}

				if (r.nugget.modItem && !r.nugget.disableRecipe)
				{
					ShapelessRecipeBuilder.shapelessRecipe(r.nugget.getItem(), 9)
							.addCriterion("has_item", hasItem(r.item.getItemTag()))
							.addIngredient(r.item.getItemTag())
							.build(consumer, new ResourceLocation(MOD_ID, "nuggets/" + r.name));
				}

				if (r.rod.modItem && !r.rod.disableRecipe)
				{
					ShapedRecipeBuilder.shapedRecipe(r.rod.getItem(), 2)
							.addCriterion("has_item", hasItem(r.item.getItemTag()))
							.patternLine("I")
							.patternLine("I")
							.key('I', r.item.getItemTag())
							.build(consumer, new ResourceLocation(MOD_ID, "rods/" + r.name));
				}

				if (r.gear.modItem && !r.gear.disableRecipe)
				{
					ShapedRecipeBuilder.shapedRecipe(r.gear.getItem())
							.addCriterion("has_item", hasItem(r.rod.getItemTag()))
							.patternLine(" R ")
							.patternLine("R R")
							.patternLine(" R ")
							.key('R', r.rod.getItemTag())
							.build(consumer, new ResourceLocation(MOD_ID, "gears/" + r.name));
				}
			});
		}
	}

	private static class LootTables extends LootTableProvider
	{
		public LootTables(DataGenerator dataGeneratorIn)
		{
			super(dataGeneratorIn);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
		{
			return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK));
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker)
		{
		}
	}

	private static class ModBlockLootTables extends BlockLootTables
	{
		@Override
		protected void addTables()
		{
			Resource.ALL_MOD_ITEMS.stream().filter(i -> i.type.block).forEach(i -> {
				if (i.type == ItemType.ORE && i.resource.dropWithFortune)
				{
					registerLootTable(i.block.get(), b -> droppingItemWithFortune(b, i.resource.item.getItem()));
				}
				else
				{
					registerDropSelfLootTable(i.block.get());
				}
			});
		}

		@Override
		protected Iterable<Block> getKnownBlocks()
		{
			return CommonResources.mod.blocks.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
		}
	}
}
