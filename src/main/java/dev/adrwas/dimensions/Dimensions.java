package dev.adrwas.dimensions;

import dev.adrwas.dimensions.bucket.BucketData;
import dev.adrwas.dimensions.bucket.DungeonsBucket;
import dev.adrwas.dimensions.mixin.GeneratorTypeAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.HashMap;
import java.util.Random;

public class Dimensions implements ModInitializer {

    public static final DungeonsBucket DUNGEONS_BUCKET = new DungeonsBucket();

    private static final HashMap<MinecraftServer, BucketData> BUCKET_DATA_HASH_MAP = new HashMap<>();

    public static BucketData getBucketData(MinecraftServer server) {
        return BUCKET_DATA_HASH_MAP.get(server);
    }

    @Override
    public void onInitialize() {

        GeneratorTypeAccessor.getValues().add(DUNGEONS_BUCKET);

        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            BUCKET_DATA_HASH_MAP.put(server, new BucketData(server));
        });

        ServerLifecycleEvents.SERVER_STOPPED.register((server) -> {
            if(BUCKET_DATA_HASH_MAP.containsKey(server)) {
                BucketData data = BUCKET_DATA_HASH_MAP.get(server);
                data.getStorageManager().timer.cancel();
                BUCKET_DATA_HASH_MAP.remove(data);
            }
        });


        System.out.println("🎉 Dimensions enabled!");

    }

}
