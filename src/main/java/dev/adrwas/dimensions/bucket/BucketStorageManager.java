package dev.adrwas.dimensions.bucket;

import dev.adrwas.dimensions.character.Character;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.World;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class BucketStorageManager {

    private World world;

    private static ArrayList<Character> characters;

    public BucketStorageManager(World world) {
        this.world = world;
    }

    public ArrayList<Character> getCharacters(boolean forceUpdate) {
        return getCharactersStorage();
    }

    private ArrayList<Character> getCharactersStorage() {
        File bucketFolder = new File(world.getServer().getSavePath(WorldSavePath.ROOT).toString() + File.separator + "bucket");

        ArrayList<Character> returnable = new ArrayList<>();

        if(bucketFolder.exists()) {
            for(File file : bucketFolder.listFiles()) {
                String fileName = file.getName();

                String extension = FilenameUtils.getExtension(fileName);
                String withoutExtension = FilenameUtils.removeExtension(fileName);

                UUID uuid;

                if(!extension.equalsIgnoreCase("json"))
                    continue;

                try {
                    uuid = UUID.fromString(withoutExtension);
                } catch (IllegalArgumentException exception) {
                    continue;
                }

                returnable.add(new Character(uuid, uuid.hashCode() % 2 == 0 ? Character.CharacterVariation.JANE : Character.CharacterVariation.JOHN));
            }

            return returnable;
        } else {
            bucketFolder.mkdir();
            return returnable;
        }
    }
}
