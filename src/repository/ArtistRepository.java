package repository;

import model.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistRepository extends Repository<Artist> {

    @Override
    public String getTableName() {
        return "artist";
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
