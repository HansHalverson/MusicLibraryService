package repository;

import database.Column;
import database.MusicDatabase;
import model.JsonMappable;
import util.MusicLibraryRequestException;

import javax.json.*;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class Repository<T> {

    public abstract String getTableName();

    public abstract Map<String, Column> getParamsToColumns();

    public abstract JsonMappable objectFromResultSet(ResultSet result);

    public JsonArray getAllEntries() throws MusicLibraryRequestException {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            List<JsonMappable> entries = MusicDatabase.getAllEntries(this);
            for (JsonMappable entry : entries) {
                builder.add(entry.toJsonObjectBuilder());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MusicLibraryRequestException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return builder.build();
    }

    public JsonObject getEntryWithId(int resourceId) throws MusicLibraryRequestException {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        try {
            JsonMappable entry = MusicDatabase.getEntryWithId(this, resourceId);

            if (entry == null) {
                throw new MusicLibraryRequestException(HttpServletResponse.SC_NOT_FOUND, "No resource found with given id.");
            }

            builder = entry.toJsonObjectBuilder();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MusicLibraryRequestException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return builder.build();
    }

    public JsonArray getFilteredEntries(Map<String, String> queryParams) throws MusicLibraryRequestException {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            List<JsonMappable> entries = MusicDatabase.getFilteredEntries(this, queryParams);
            for (JsonMappable entry : entries) {
                builder.add(entry.toJsonObjectBuilder());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MusicLibraryRequestException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return builder.build();
    }
}
