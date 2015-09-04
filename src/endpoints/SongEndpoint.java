package endpoints;

import model.Song;
import repository.SongRepository;

public class SongEndpoint extends Endpoint<Song> {

    public SongEndpoint() {
        repository = new SongRepository();
    }
}
