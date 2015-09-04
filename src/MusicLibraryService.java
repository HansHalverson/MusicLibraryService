import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;

import database.MusicDatabase;
import repository.*;
import util.MusicLibraryRequestException;
import util.UrlUtil;

@WebServlet("/api")
public class MusicLibraryService extends HttpServlet {

    ArtistRepository artistRepository = new ArtistRepository();
    AlbumRepository albumRepository = new AlbumRepository();
    GenreRepository genreRepository = new GenreRepository();
    SongRepository songRepository = new SongRepository();

    private Repository selectRepository(HttpServletRequest request) throws MusicLibraryRequestException {
        String resource = UrlUtil.getPathSegment(request, 1);

        // Handle case where resource is not specified
        if (resource == null) {
            throw new MusicLibraryRequestException(HttpServletResponse.SC_BAD_REQUEST, "No resource specified.");
        }

        switch (resource) {
            case "artists":
                return artistRepository;
            case "albums":
                return albumRepository;
            case "songs":
                return songRepository;
            case "genres":
                return genreRepository;
            default:
                throw new MusicLibraryRequestException(HttpServletResponse.SC_NOT_FOUND, "Resource " + resource + " could not be found.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Repository repository = selectRepository(request);

            // Add the returned JSON to the http response
            response.setContentType("application/json");

            JsonValue responseJson = repository.handleGet(request);

            PrintWriter responseWriter = response.getWriter();
            responseWriter.write(responseJson.toString());

        } catch (MusicLibraryRequestException e) {
            response.sendError(e.getStatusCode(), e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Repository repository = selectRepository(request);
            repository.handlePost(request);

        } catch (MusicLibraryRequestException e) {
            response.sendError(e.getStatusCode(), e.getMessage());
        }
    }

    @Override
    public void init() {
        try {
            MusicDatabase.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
