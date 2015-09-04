package repository;

import database.Column;
import model.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ArtistRepository extends Repository<Artist> {

    private Map<String, Column> paramsToColumns;

    public ArtistRepository() {
        paramsToColumns = new HashMap<>();
        paramsToColumns.put("name", new Column("name", Column.ColumnType.STRING));
    }

    @Override
    public String getTableName() {
        return "artist";
    }

    @Override
    public Map<String, Column> getParamsToColumns() {
        return paramsToColumns;
    }


    @Override
    public Artist objectFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("artist_id");
            String name = resultSet.getString("name");
            return new Artist(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
