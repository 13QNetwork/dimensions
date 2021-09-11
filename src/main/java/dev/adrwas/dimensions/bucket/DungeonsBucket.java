package dev.adrwas.dimensions.bucket;


import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.*;

import java.util.Collections;
import java.util.Optional;

public class DungeonsBucket extends GeneratorType {

    public DungeonsBucket() {
        super("dungeons_bucket");
    }

    @Override
    protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
        FlatChunkGeneratorConfig config = new FlatChunkGeneratorConfig(
                new StructuresConfig(Optional.empty(), Collections.emptyMap()), biomeRegistry);
        config.updateLayerBlocks();

        return new FlatChunkGenerator(config);
    }
}
