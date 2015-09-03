package repository;

import model.JsonMappable;
import model.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SongRepository extends Repository<Song> {

    @Override
    public String getTableName() {
        return "song";
    }

    @Override
    public Map<String, String> getParamsToColumnNames() {
        Map<String, String> paramsToColumnNames = new HashMap<>();

        paramsToColumnNames.put("id", "song_id");
        paramsToColumnNames.put("name", "name");
        paramsToColumnNames.put("file", "file");
        paramsToColumnNames.put("time", "time");
        paramsToColumnNames.put("plays", "plays");
        paramsToColumnNames.put("trackNumber", "track_number");
        paramsToColumnNames.put("discNumber", "disc_number");
        paramsToColumnNames.put("albumId", "album_id");

        return paramsToColumnNames;
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
            int albumId = resultSet.getInt("album_id");

            return new Song(id, name, file, time, plays, trackNumber, discNumber, albumId);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
