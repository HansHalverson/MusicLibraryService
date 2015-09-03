package repository;

import database.Column;
import model.Album;
import model.JsonMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AlbumRepository extends Repository<Album> {

    private Map<String, Column> paramsToColumns;

    public AlbumRepository() {
        paramsToColumns = new HashMap<>();
        paramsToColumns.put("id", new Column("album_id", Column.ColumnType.INT));
        paramsToColumns.put("name", new Column("name", Column.ColumnType.STRING));
        paramsToColumns.put("artistId", new Column("artist_id", Column.ColumnType.INT));
    }

    @Override
    public String getTableName() {
        return "album";
    }

    @Override
    public Map<String, Column> getParamsToColumns() {
        return paramsToColumns;
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
