package dev.adrwas.dimensions.character;

import java.util.ArrayList;
import java.util.UUID;

public class Character {

    private final UUID realPlayer;

    private final String characterVariation;

    private ArrayList<Run> runs;

    public Character(UUID realPlayer, String characterVariation, ArrayList<Run> runs) {
        this.realPlayer = realPlayer;
        this.characterVariation = characterVariation;
        this.runs = runs;
    }

    public UUID getRealPlayer() {
        return realPlayer;
    }

    public String getCharacterVariation() {
        return characterVariation;
    }

    public ArrayList<Run> getRuns() {
        return runs;
    }
}
