package dev.adrwas.dimensions.character;

import java.util.ArrayList;
import java.util.UUID;

public class DungeonsPlayer {

    private UUID uuid;

    private ArrayList<Character> characters;

    private int selectedCharacter;

    public DungeonsPlayer(UUID uuid, ArrayList<Character> characters, int selectedCharacter) {
        this.uuid = uuid;
        this.characters = characters;
        this.selectedCharacter = selectedCharacter;
    }

    public UUID getUUID() {
        return uuid;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public int getSelectedCharacter() {
        return selectedCharacter;
    }
}
