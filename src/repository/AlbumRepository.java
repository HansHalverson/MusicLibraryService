package repository;

import model.Album;
import model.JsonMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AlbumRepository extends Repository<Album> {

    @Override
    public String getTableName() {
        return "album";
    }

    @Override
    public Map<String, String> getParamsToColumnNames() {
        Map<String, String> paramsToColumnNames = new HashMap<>();

        paramsToColumnNames.put("id", "album_id");
        paramsToColumnNames.put("name", "name");
        paramsToColumnNames.put("artistId", "artist_id");

        return paramsToColumnNames;
    }

    @Override
    public JsonMappable objectFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("album_id");
            String name = resultSet.getString("name");
            int artistId = resultSet.getInt("artist_id");
            return new Album(id, name, artistId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
