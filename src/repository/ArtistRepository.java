package repository;

import model.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ArtistRepository extends Repository<Artist> {

    @Override
    public String getTableName() {
        return "artist";
    }

    @Override
    public Map<String, String> getParamsToColumnNames() {
        Map<String, String> paramsToColumnNames = new HashMap<>();

        paramsToColumnNames.put("id", "artist_id");
        paramsToColumnNames.put("name", "name");

        return paramsToColumnNames;
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
