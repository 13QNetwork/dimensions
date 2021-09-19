package dev.adrwas.dimensions.character;

import dev.adrwas.dimensions.Dimensions;
import dev.adrwas.dimensions.bucket.BucketData;
import dev.adrwas.dimensions.bucket.BucketStorageManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class Character {

    private final UUID realPlayer;

    private final String characterVariation;

    private ArrayList<Run> runs;

    private BucketData data;

    public Character(UUID realPlayer, String characterVariation, ArrayList<Run> runs, BucketData data) {
        this.realPlayer = realPlayer;
        this.characterVariation = characterVariation;
        this.runs = runs;
        this.data = data;
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

    public Run createRun(boolean multiplayer) {
        runs.forEach(run -> {
            run.setSelected(false);
        });

        Run run = new Run(data.getStorageManager().getAvailableRunId(), realPlayer, multiplayer, true, Instant.now().getEpochSecond());
        runs.add(run);
        return run;
    }
}
