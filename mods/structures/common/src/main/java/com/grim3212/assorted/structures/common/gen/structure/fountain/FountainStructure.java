package com.grim3212.assorted.structures.common.gen.structure.fountain;

import java.util.Optional;

import com.grim3212.assorted.lib.conditions.LibParts;
import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.common.gen.structure.StructuresTypes;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class FountainStructure extends Structure {

	public static final MapCodec<FountainStructure> CODEC = simpleCodec(FountainStructure::new);

	public FountainStructure(Structure.StructureSettings settings) {
		super(settings);
	}

	private static boolean extraSpawningChecks(Structure.GenerationContext context) {
		if (!StructuresTypes.validBiomeOnTop(context, Heightmap.Types.WORLD_SURFACE_WG)) {
			return false;
		}
		return Structure.getLowestY(context, 12, 15) >= context.chunkGenerator().getSeaLevel();
	}

	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		if (!LibParts.isEnabled(Constants.MOD_ID) || !FountainStructure.extraSpawningChecks(context)) {
			return Optional.empty();
		}

		return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (piecesBuilder) -> {
			generatePieces(piecesBuilder, context);
		});
	}

	@Override
	public StructureType<?> type() {
		return StructuresTypes.FOUNTAIN_STRUCTURE_TYPE.get();
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
		ChunkPos chunkPos = context.chunkPos();
		BlockPos middlePos = chunkPos.getMiddleBlockPosition(0);
		int topLandY = context.chunkGenerator().getFirstFreeHeight(middlePos.getX(), middlePos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
		BlockPos blockpos = new BlockPos(chunkPos.getMinBlockX(), topLandY, chunkPos.getMinBlockZ());

		int height = 4 * (3 + context.random().nextInt(3));
		int type = context.random().nextInt(2);

		builder.addPiece(new FountainPiece(context.random(), blockpos, height, type));
	}
}
