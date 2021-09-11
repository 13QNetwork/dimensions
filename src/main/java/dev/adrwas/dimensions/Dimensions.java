package dev.adrwas.dimensions;

import dev.adrwas.dimensions.bucket.DungeonsBucket;
import dev.adrwas.dimensions.mixin.GeneratorTypeAccessor;
import net.fabricmc.api.ModInitializer;

public class Dimensions implements ModInitializer {

    public static final DungeonsBucket DUNGEONS_BUCKET = new DungeonsBucket();

    @Override
    public void onInitialize() {

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GeneratorTypeAccessor.getValues().add(DUNGEONS_BUCKET);
        }).start();

        System.out.println("🎉 Dimensions enabled!");

    }

}
