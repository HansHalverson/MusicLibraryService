package repository;

import database.Column;
import model.JsonMappable;
import model.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SongRepository extends Repository<Song> {

    private Map<String, Column> paramsToColumns;

    public SongRepository() {
        paramsToColumns = new HashMap<>();
        paramsToColumns.put("name", new Column("name", Column.ColumnType.STRING));
        paramsToColumns.put("file", new Column("file", Column.ColumnType.STRING));
        paramsToColumns.put("time", new Column("time", Column.ColumnType.INT));
        paramsToColumns.put("plays", new Column("plays", Column.ColumnType.INT));
        paramsToColumns.put("trackNumber", new Column("track_number", Column.ColumnType.INT));
        paramsToColumns.put("discNumber", new Column("disc_number", Column.ColumnType.INT));
        paramsToColumns.put("albumId", new Column("album_id", Column.ColumnType.INT));
    }

    @Override
    public String getTableName() {
        return "song";
    }

    @Override
    public Map<String, Column> getParamsToColumns() {
        return paramsToColumns;
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
