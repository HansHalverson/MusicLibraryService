package model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Genre implements JsonMappable {
    private int id;
    private String name;
    private int supergenreId;

    public Genre(int id, String name, int supergenreId) {
        this.id = id;
        this.name = name;
        this.supergenreId = supergenreId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSupergenreId() {
        return supergenreId;
    }

    @Override
    public JsonObjectBuilder toJsonObjectBuilder() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);
        builder.add("name", name);
        builder.add("supergenreId", supergenreId);
        
        return builder;
    }
}
