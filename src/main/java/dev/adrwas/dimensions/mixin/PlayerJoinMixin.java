package dev.adrwas.dimensions.mixin;

import com.google.common.collect.Lists;
import dev.adrwas.dimensions.Dimensions;
import dev.adrwas.dimensions.bucket.BucketData;
import dev.adrwas.dimensions.bucket.BucketStorageManager;
import dev.adrwas.dimensions.character.Character;
import dev.adrwas.dimensions.character.DungeonsPlayer;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.security.DigestException;
import java.util.ArrayList;
import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerJoinMixin {

    @Inject(at = @At(value = "TAIL"), method = "onPlayerConnect", cancellable = true)
    private void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        System.out.println(player.getUuid() + " " + player.getName());
        UUID uuid = player.getUuid();
        BucketData data = Dimensions.getBucketData(player.server);

        data.getDungeonsPlayers().keySet().forEach(key -> {
            System.out.println(key.toString());
        });


        if(data.getDungeonsPlayers().containsKey(uuid)) {
            System.out.println("Player already in data!");
        } else {
            System.out.println("Player NOT already in data!");
            Character character = new Character(uuid, "john", new ArrayList<>(), data);

            Dimensions.getBucketData(player.server).getDungeonsPlayers().put(
                uuid,
                new DungeonsPlayer(uuid, Lists.newArrayList(character), 0)
            );
        }
    }
}