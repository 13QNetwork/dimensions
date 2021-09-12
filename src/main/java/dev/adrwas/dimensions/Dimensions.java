package dev.adrwas.dimensions;

import dev.adrwas.dimensions.bucket.DungeonsBucket;
import dev.adrwas.dimensions.mixin.GeneratorTypeAccessor;
import net.fabricmc.api.ModInitializer;

public class Dimensions implements ModInitializer {

    public static final DungeonsBucket DUNGEONS_BUCKET = new DungeonsBucket();

    @Override
    public void onInitialize() {

        GeneratorTypeAccessor.getValues().add(DUNGEONS_BUCKET);

//        SheepShearCallback.EVENT.register((player, sheep) -> {
//            sheep.setSheared(true);
//
//            // create diamond item entity at sheep position
//            ItemStack stack = new ItemStack(Items.DIAMOND);
//            ItemEntity itemEntity = new ItemEntity(player.world, sheep.getX(), sheep.getY(), sheep.getZ(), stack);
//            player.world.spawnEntity(itemEntity);
//
//            return ActionResult.FAIL;
//        });

        System.out.println("🎉 Dimensions enabled!");

    }

}
