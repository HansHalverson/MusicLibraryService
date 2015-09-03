package database;

import model.JsonMappable;
import repository.Repository;
import util.MusicLibraryRequestException;
import util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MusicDatabase {

    private final String url = "jdbc:mysql://localhost:3306/music_library";
    private final String username = "java";
    private final String password = "javapass";

    private static Connection connection;

    private static MusicDatabase database;

    private MusicDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public static void init() throws ExceptionInInitializerError, SQLException {
        if (database != null) {
            throw new ExceptionInInitializerError();
        } else {
            database = new MusicDatabase();

            // Use the music_library database
            Statement statement = null;

            try {
                statement = connection.createStatement();
                statement.executeQuery("USE music_library;");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }
    }


    public static List<JsonMappable> getAllEntries(Repository repository) throws SQLException {
        List<JsonMappable> entries = new ArrayList<>();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + repository.getTableName() + ";");

            while(result.next()) {
                JsonMappable entry = repository.objectFromResultSet(result);
                if (entry != null) {
                    entries.add(entry);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return entries;
    }

    public static JsonMappable getEntryWithId(Repository repository, int resourceId) throws SQLException {
        JsonMappable entry = null;
        Statement statement = null;

        try {
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM " + repository.getTableName() + " WHERE " + repository.getTableName() + "_id = " + resourceId + ";");
            result.next();

            entry = repository.objectFromResultSet(result);

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return entry;
    }

    public static List<JsonMappable> getFilteredEntries(Repository repository, Map<String, String> queryParams) throws SQLException, MusicLibraryRequestException {
        List<JsonMappable> entries = new ArrayList<>();
        PreparedStatement statement = null;

        try {
            Map<String, String> sqlParams = SQLUtil.formatQueryParams(repository, queryParams);
            statement = connection.prepareStatement("SELECT * FROM " + repository.getTableName() + " WHERE " + SQLUtil.createQueryTemplate(sqlParams.size()) + ";");


            ResultSet result = statement.executeQuery();

            while(result.next()) {
                JsonMappable entry = repository.objectFromResultSet(result);
                if (entry != null) {
                    entries.add(entry);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return entries;
    }
}
