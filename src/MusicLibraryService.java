import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;

import database.MusicDatabase;
import repository.ArtistRepository;

@WebServlet("/api")
public class MusicLibraryService extends HttpServlet {

    private String getResourceFromRequest(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String resource = null;

        if (pathInfo != null) {
            String[] pathSections = pathInfo.substring(1).split("/");
            if (pathSections.length > 0) {
                resource = pathSections[0];
            }
        }

        return resource;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resource = getResourceFromRequest(request);

        // Handle case where resource is not specified
        if (resource == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No resource specified.");
            return;
        }

        JsonArray responseJson = null;

        switch (resource) {
            case "artists":
                responseJson = ArtistRepository.handleGet(request, response);
                break;
            case "albums":
                break;
            case "songs":
                break;
            case "genres":
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource " + resource + " could not be found.");
                return;
        }

        // Add the returned JSON to the http response
        response.setContentType("application/json");

        PrintWriter responseWriter = response.getWriter();

        responseWriter.write(responseJson.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
