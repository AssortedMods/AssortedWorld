package com.grim3212.assorted.world.common.gen.feature;

import com.google.common.collect.ImmutableList;
import com.grim3212.assorted.world.common.block.WorldBlocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class WorldTargets {

	static RuleTest stoneOreTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
	static RuleTest deepslateOreTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
	static RuleTest endStoneOreTest = new BlockMatchTest(Blocks.END_STONE);

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_RANDOMITE_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, WorldBlocks.RANDOMITE_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, WorldBlocks.DEEPSLATE_RANDOMITE_ORE.get().defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_VOID_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(endStoneOreTest, WorldBlocks.VOID_CRYSTAL_ORE.get().defaultBlockState()));

}
