import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.util.HashMap;

import actions.Action;
import actions.ScrapeGenresAction;
import database.MusicDatabase;
import endpoints.*;
import model.Album;
import model.Artist;
import model.Genre;
import model.Song;
import repository.AlbumRepository;
import repository.ArtistRepository;
import repository.GenreRepository;
import repository.SongRepository;
import util.MusicLibraryRequestException;
import util.UrlUtil;

@WebServlet("/api")
public class MusicLibraryService extends HttpServlet {

    private Endpoint<Artist> artistEndpoint;
    private Endpoint<Album> albumEndpoint;
    private Endpoint<Genre> genreEndpoint;
    private Endpoint<Song> songEndpoint;

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

            artistEndpoint = new Endpoint<>(new ArtistRepository());
            albumEndpoint = new Endpoint<>(new AlbumRepository());
            songEndpoint = new Endpoint<>(new SongRepository());

            // Set up actions and initialize genre endpoint
            HashMap<String, Action> genreEndpointActions = new HashMap<>();
            genreEndpointActions.put("scrape", new ScrapeGenresAction());
            genreEndpoint = new Endpoint<>(new GenreRepository(), genreEndpointActions);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
