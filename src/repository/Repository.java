package repository;

import database.MusicDatabase;
import model.JsonMappable;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class Repository<T> {

    public abstract String getTableName();

    public JsonArray handleGet(HttpServletRequest request, HttpServletResponse response) {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            List<JsonMappable> entries = MusicDatabase.getAllEntries(this);
            for (JsonMappable entry : entries) {
                builder.add(entry.toJsonObjectBuilder());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    public abstract JsonMappable objectFromResultSet(ResultSet result);
}
