package com.grim3212.assorted.portals.common.gen.feature;

import com.google.common.collect.ImmutableList;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class PortalsTargets {

	static RuleTest endStoneOreTest = new BlockMatchTest(Blocks.END_STONE);

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_VOID_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(endStoneOreTest, PortalsBlocks.VOID_CRYSTAL_ORE.get().defaultBlockState()));

}
