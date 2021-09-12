package dev.adrwas.dimensions.character;

import java.util.UUID;

public class Character {

    public enum CharacterVariation {
        JOHN,
        JANE
    }

    private final UUID realPlayer;
    private final CharacterVariation characterVariation;
    //public Run[] runs;

    public Character(UUID realPlayer, CharacterVariation characterVariation) {
        this.realPlayer = realPlayer;
        this.characterVariation = characterVariation;
    }

    public UUID getRealPlayer() {
        return realPlayer;
    }

    public CharacterVariation getCharacterVariation() {
        return characterVariation;
    }
}
