package repository;

import model.Album;
import model.JsonMappable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRepository extends Repository<Album> {

    @Override
    public String getTableName() {
        return "album";
    }

    @Override
    public JsonMappable objectFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("album_id");
            String name = resultSet.getString("name");
            return new Album(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
