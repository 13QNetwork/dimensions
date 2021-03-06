package dev.adrwas.dimensions.bucket;

import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.adrwas.dimensions.character.Character;
import dev.adrwas.dimensions.character.DungeonsPlayer;
import dev.adrwas.dimensions.character.Run;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BucketStorageManager {

    private MinecraftServer world;
    private BucketData data;

    public Timer timer = new Timer();

    public BucketStorageManager(MinecraftServer world, BucketData data) {
        this.world = world;
        this.data = data;

        getData();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                save();
            }
        }, 0, 30_000);
    }

    public void getData() {
        System.out.println("🌶 Fetching Data...");
        File charactersDirectory = getBucketDirectory("characters");
        File runsDirectory = getBucketDirectory("runs");

        for(String fileName : charactersDirectory.list()) {
            System.out.println("🌶 Reading " + fileName);
            UUID uuid = UUID.fromString(Files.getNameWithoutExtension(fileName));
            ArrayList<Character> characterArrayList = new ArrayList<>();

            File file = Paths.get(charactersDirectory.getPath(), fileName).toFile();

            FileReader fileReader;
            try {
                fileReader = new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            System.out.println("🌶 Parsing JSON");
            JsonParser jsonParser = new JsonParser();
            Object object = jsonParser.parse(fileReader);

            JsonArray jsonArray = (JsonArray) object;
            jsonArray.forEach(jsonElement -> {
                JsonObject character = jsonElement.getAsJsonObject();

                ArrayList<Run> runs = new ArrayList<>();

                System.out.println("iterating runs");
                character.get("runs").getAsJsonArray().forEach(run -> {
                    String runString = run.getAsString();
                    System.out.println("run " + runString);

                    File runFile = Paths.get(runsDirectory.getPath(), runString + ".json").toFile();
                    FileReader runFileReader;
                    try {
                        runFileReader = new FileReader(runFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                    JsonParser runFileParser = new JsonParser();
                    JsonObject runObject = runFileParser.parse(runFileReader).getAsJsonObject();

                    String name = runObject.get("name").getAsString();
                    boolean multiplayer = runObject.get("multiplayer").getAsBoolean();
                    long lastPlayed = runObject.get("lastPlayed").getAsLong();

                    System.out.println("name: " + name + ", multiplayer: " + multiplayer + ", last played" + lastPlayed);

                    runs.add(new Run(
                       UUID.fromString(runString),
                       uuid,
                       false,
                       false,
                       0
                    ));
                });

                characterArrayList.add(
                    new Character(
                        uuid,
                        character.get("character").getAsString(),
                        runs,
                        data
                    )
                );
            });

            DungeonsPlayer dungeonsPlayer = new DungeonsPlayer(uuid, characterArrayList, 0);
            data.getDungeonsPlayers().put(uuid, dungeonsPlayer);
        }

        System.out.println("🌶 Done Fetching Data");
    }

    public void save() {
        System.out.println("🌶 Saving data...");

        File characters = getBucketDirectory("characters");
        File runsDirectory = getBucketDirectory("runs");

        data.getDungeonsPlayers().values().forEach(dungeonsPlayer -> {
            File jsonFile = Paths.get(characters.getPath(), dungeonsPlayer.getUUID().toString() + ".json").toFile();

            JsonArray jsonArray = new JsonArray();

            dungeonsPlayer.getCharacters().forEach(character -> {
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("character", character.getCharacterVariation());
                jsonObject.addProperty("lastPlayed", 0);

                JsonArray runs = new JsonArray();

                character.getRuns().forEach(run -> {
                    System.out.println("run loop, " + run.getRunId().toString());
                    File runFile = Paths.get(runsDirectory.getPath(), run.getRunId().toString() + ".json").toFile();
                    if(!runFile.exists()) {
                        System.out.println("run file doesnt exist");
                        try {
                            System.out.println("creating");
                            runFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }

                    JsonObject jsonObjectRun = new JsonObject();
                    jsonObjectRun.addProperty("name", "My custom run! 😁");
                    jsonObjectRun.addProperty("multiplayer", false);
                    jsonObjectRun.addProperty("lastPlayed" ,0);

                    try {
                        FileWriter runFileWriter = new FileWriter(runFile);
                        runFileWriter.write(jsonObjectRun.toString());
                        runFileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    runs.add(run.getRunId().toString());
                });

                jsonObject.add("runs", runs);
                jsonArray.add(jsonObject);
            });

            try {
                FileWriter fileWriter = new FileWriter(jsonFile);
                fileWriter.write(jsonArray.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public UUID getAvailableRunId() {
        File runDirectory = getBucketDirectory("runs");
        UUID uuid = UUID.randomUUID();
        while(true) {
            if(!Paths.get(runDirectory.getPath(), uuid.toString()).toFile().exists()) {
                return uuid;
            }
            uuid = UUID.randomUUID();
        }
    }

    private File getBucketDirectory(String path) {
        File bucketFile = Paths.get(world.getSavePath(WorldSavePath.ROOT).toString(), "bucket").toFile();
        if(!bucketFile.exists()) bucketFile.mkdir();

        File file = Paths.get(bucketFile.getPath(), path).toFile();
        if(!file.exists()) file.mkdir();

        return file;
    }
}
