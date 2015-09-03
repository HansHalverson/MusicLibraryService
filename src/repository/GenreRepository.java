package repository;

import model.Genre;
import model.JsonMappable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRepository extends Repository<Genre> {

    @Override
    public String getTableName() {
        return "genre";
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
