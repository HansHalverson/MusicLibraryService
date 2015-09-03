package repository;

import model.JsonMappable;
import model.Song;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRepository extends Repository<Song> {

    @Override
    public String getTableName() {
        return "song";
    }

    @Override
    public JsonMappable objectFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("song_id");
            String name = resultSet.getString("name");
            String file = resultSet.getString("file");
            int time = resultSet.getInt("time");
            int plays = resultSet.getInt("plays");
            int trackNumber = resultSet.getInt("track_number");
            int discNumber = resultSet.getInt("disc_number");

            return new Song(id, name, file, time, plays, trackNumber, discNumber);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
