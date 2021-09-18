package dev.adrwas.dimensions.bucket;

import dev.adrwas.dimensions.character.DungeonsPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.UUID;

public class BucketData {

    private MinecraftServer world;

    private BucketStorageManager storageManager;

    private HashMap<UUID, DungeonsPlayer> DUNGEONS_PLAYER_HASHMAP = new HashMap<>();

    public BucketData(MinecraftServer world) {
        this.world = world;
        this.storageManager = new BucketStorageManager(world, this);
    }

    public MinecraftServer getWorld() {
        return world;
    }

    public HashMap<UUID, DungeonsPlayer> getDungeonsPlayers() {
        return DUNGEONS_PLAYER_HASHMAP;
    }

    public BucketStorageManager getStorageManager() {
        return storageManager;
    }
}
