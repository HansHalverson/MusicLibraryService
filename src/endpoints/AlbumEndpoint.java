package endpoints;

import model.Album;
import repository.AlbumRepository;

public class AlbumEndpoint extends Endpoint<Album> {

    public AlbumEndpoint() {
        repository = new AlbumRepository();
    }

}
