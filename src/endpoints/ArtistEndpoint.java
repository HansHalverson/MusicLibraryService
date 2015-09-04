package endpoints;

import model.Artist;
import repository.ArtistRepository;

public class ArtistEndpoint extends Endpoint<Artist> {

    public ArtistEndpoint() {
        repository = new ArtistRepository();
    }
}
