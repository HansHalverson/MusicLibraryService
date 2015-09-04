package endpoints;

import database.MusicDatabase;
import repository.Repository;
import util.MusicLibraryRequestException;
import util.UrlUtil;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class Endpoint<T> {

    protected Repository<T> repository;

    public JsonValue handleGet(HttpServletRequest request) throws MusicLibraryRequestException {
        String resourceId = UrlUtil.getPathSegment(request, 2);
        Map<String, String> queryParams = UrlUtil.getQueryParameters(request);

        if (resourceId == null) {
            if (queryParams.isEmpty()) {
                return repository.getAllEntries();
            } else {
                return repository.getFilteredEntries(queryParams);
            }
        } else {
            if (queryParams.isEmpty()) {
                try {
                    return repository.getEntryWithId(Integer.valueOf(resourceId));
                } catch (NumberFormatException e) {
                    throw new MusicLibraryRequestException(HttpServletResponse.SC_NOT_FOUND, "Invalid resource id.");
                }
            } else {
                throw new MusicLibraryRequestException(HttpServletResponse.SC_BAD_REQUEST, "Cannot specify both resource id and query parameters.");
            }
        }
    }

    public void handlePost(HttpServletRequest request) throws MusicLibraryRequestException {
        String resourceId = UrlUtil.getPathSegment(request, 2);

        if (resourceId == null) {
            try {
                JsonReader reader = Json.createReader(request.getInputStream());
                JsonArray newEntries = reader.readArray();
                MusicDatabase.createEntries(repository, newEntries);

            } catch (IOException | SQLException e) {
                e.printStackTrace();
                throw new MusicLibraryRequestException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }

        } else {
            throw new MusicLibraryRequestException(HttpServletResponse.SC_NOT_FOUND, "Cannot POST to resource");
        }
    }
}
