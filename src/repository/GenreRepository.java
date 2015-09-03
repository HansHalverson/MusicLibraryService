package repository;

import model.Genre;
import model.JsonMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GenreRepository extends Repository<Genre> {

    @Override
    public String getTableName() {
        return "genre";
    }

    @Override
    public Map<String, String> getParamsToColumnNames() {
        Map<String, String> paramsToColumnNames = new HashMap<>();

        paramsToColumnNames.put("id", "genre_id");
        paramsToColumnNames.put("name", "id");
        paramsToColumnNames.put("supergenreId", "supergenre_id");

        return paramsToColumnNames;
    }

    @Override
    public JsonMappable objectFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("genre_id");
            String name = resultSet.getString("name");
            int supergenreId = resultSet.getInt("supergenre_id");

            return new Genre(id, name, supergenreId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
