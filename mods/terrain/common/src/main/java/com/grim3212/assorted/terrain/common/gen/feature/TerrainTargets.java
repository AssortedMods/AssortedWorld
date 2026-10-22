package com.grim3212.assorted.terrain.common.gen.feature;

import com.google.common.collect.ImmutableList;
import com.grim3212.assorted.terrain.common.block.TerrainBlocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class TerrainTargets {

	static RuleTest stoneOreTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
	static RuleTest deepslateOreTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

	public static final ImmutableList<BlockReplacement> ORE_RANDOMITE_TARGET_LIST = ImmutableList.of(BlockReplacement.replace(stoneOreTest, TerrainBlocks.RANDOMITE_ORE.get().defaultBlockState()), BlockReplacement.replace(deepslateOreTest, TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get().defaultBlockState()));

}
