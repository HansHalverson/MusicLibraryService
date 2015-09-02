import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import database.MusicDatabase;

@WebServlet("/api")
public class MusicLibraryService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write("{ \"pathInfo\": \"" + request.getPathInfo() + "\",");
        writer.write("\"requestUri\": \"" + request.getRequestURI() + "\",");
        writer.write("\"queryString\": \"" + request.getQueryString() + "\" }");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() {
        MusicDatabase database = new MusicDatabase();
        System.out.println("Started MusicLibraryService server");
    }
}
