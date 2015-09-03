package model;

import javax.json.JsonObjectBuilder;

public interface JsonMappable {

    public JsonObjectBuilder toJsonObjectBuilder();
}
