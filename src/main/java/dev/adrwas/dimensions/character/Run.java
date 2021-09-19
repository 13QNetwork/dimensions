package dev.adrwas.dimensions.character;

import java.util.UUID;

public class Run {

    private UUID runId;
    private UUID owner;
    private boolean multiplayer;
    private boolean selected;
    private long lastPlayed;

    public Run(UUID runId, UUID owner, boolean multiplayer, boolean selected, long lastPlayed) {
        this.runId = runId;
        this.owner = owner;
        this.multiplayer = multiplayer;
        this.selected = selected;
        this.lastPlayed = lastPlayed;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }
}
