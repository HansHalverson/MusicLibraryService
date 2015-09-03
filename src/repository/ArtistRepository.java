package repository;

import database.MusicDatabase;
import model.Artist;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class ArtistRepository {

    public static JsonArray handleGet(HttpServletRequest request, HttpServletResponse response) {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            List<Artist> artists = MusicDatabase.getAllArtists();
            for (Artist artist : artists) {
                builder.add(artist.toJsonObjectBuilder());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return builder.build();
    }

}
