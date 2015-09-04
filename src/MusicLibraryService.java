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
import endpoints.*;
import util.MusicLibraryRequestException;
import util.UrlUtil;

@WebServlet("/api")
public class MusicLibraryService extends HttpServlet {

    ArtistEndpoint artistEndpoint = new ArtistEndpoint();
    AlbumEndpoint albumEndpoint = new AlbumEndpoint();
    GenreEndpoint genreEndpoint = new GenreEndpoint();
    SongEndpoint songEndpoint = new SongEndpoint();

    private Endpoint selectEndpoint(HttpServletRequest request) throws MusicLibraryRequestException {
        String resource = UrlUtil.getPathSegment(request, 1);

        // Handle case where resource is not specified
        if (resource == null) {
            throw new MusicLibraryRequestException(HttpServletResponse.SC_BAD_REQUEST, "No resource specified.");
        }

        switch (resource) {
            case "artists":
                return artistEndpoint;
            case "albums":
                return albumEndpoint;
            case "songs":
                return songEndpoint;
            case "genres":
                return genreEndpoint;
            default:
                throw new MusicLibraryRequestException(HttpServletResponse.SC_NOT_FOUND, "Resource " + resource + " could not be found.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Endpoint endpoint = selectEndpoint(request);

            // Add the returned JSON to the http response
            response.setContentType("application/json");

            JsonValue responseJson = endpoint.handleGet(request);

            PrintWriter responseWriter = response.getWriter();
            responseWriter.write(responseJson.toString());

        } catch (MusicLibraryRequestException e) {
            response.sendError(e.getStatusCode(), e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Endpoint endpoint = selectEndpoint(request);
            endpoint.handlePost(request);

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
