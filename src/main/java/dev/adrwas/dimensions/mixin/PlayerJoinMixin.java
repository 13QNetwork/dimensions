package dev.adrwas.dimensions.mixin;

import dev.adrwas.dimensions.bucket.BucketStorageManager;
import dev.adrwas.dimensions.character.Character;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerJoinMixin {

    @Inject(at = @At(value = "TAIL"), method = "onPlayerConnect", cancellable = true)
    private void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        new Thread(() -> {
            for(Character character : new BucketStorageManager(player.world).getCharacters(false)) {
                System.out.println(character.getRealPlayer().toString() + " using " + character.getCharacterVariation().toString());
            };
        }).start();
    }
}