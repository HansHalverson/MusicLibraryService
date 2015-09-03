package model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Song implements JsonMappable {
    private int id;
    private String name;
    private String file;
    private int time;
    private int plays;
    private int trackNumber;
    private int discNumber;

    public Song(int id, String name, String file, int time, int plays, int trackNumber, int discNumber) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.time = time;
        this.plays = plays;
        this.trackNumber = trackNumber;
        this.discNumber = discNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFile() {
        return file;
    }

    public int getTime() {
        return time;
    }

    public int getPlays() {
        return plays;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    @Override
    public JsonObjectBuilder toJsonObjectBuilder() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);
        builder.add("name", name);
        builder.add("file", file);
        builder.add("time", time);
        builder.add("plays", plays);
        builder.add("trackNumber", trackNumber);
        builder.add("discNumber", discNumber);

        return builder;
    }
}
