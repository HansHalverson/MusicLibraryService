package repository;

import database.MusicDatabase;
import model.JsonMappable;
import util.UrlUtil;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class Repository<T> {

    public abstract String getTableName();

    public JsonValue handleGet(HttpServletRequest request) {
        String resourceId = UrlUtil.getPathSegment(request, 2);
        Map<String, String[]> params = request.getParameterMap();

        if (resourceId == null) {
            if (params.isEmpty()) {
                return getAllEntries();
            } else {
                return null;
            }
        } else {
            if (params.isEmpty()) {
                return getEntryWithId(Integer.valueOf(resourceId));
            } else {
                return null;
            }
        }
    }

    private JsonArray getAllEntries() {
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

    private JsonObject getEntryWithId(int resourceId) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        try {
            JsonMappable entry = MusicDatabase.getEntryWithId(this, resourceId);
            builder = entry.toJsonObjectBuilder();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    public abstract JsonMappable objectFromResultSet(ResultSet result);
}
