package dev.adrwas.dimensions.character;

import java.util.UUID;

public class Run {

    private UUID runId;
    private UUID owner;
    private boolean multiplayer;

    public Run(UUID runId, UUID owner, boolean multiplayer) {
        this.runId = runId;
        this.owner = owner;
        this.multiplayer = multiplayer;
    }

    public UUID getRunId() {
        return runId;
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }
}
