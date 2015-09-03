package model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Album implements JsonMappable {
    private int id;
    private String name;
    private int artistId;

    public Album(int id, String name, int artistId) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getArtistId() {
        return artistId;
    }

    @Override
    public JsonObjectBuilder toJsonObjectBuilder() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);
        builder.add("name", name);
        builder.add("artistId", artistId);

        return builder;
    }
}
