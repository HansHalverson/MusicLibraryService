package model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Album implements JsonMappable {
    private int id;
    private String name;

    public Album(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public JsonObjectBuilder toJsonObjectBuilder() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", id);
        builder.add("name", name);
        return builder;
    }
}
