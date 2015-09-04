package endpoints;

import model.Genre;
import repository.GenreRepository;

public class GenreEndpoint extends Endpoint<Genre> {

    public GenreEndpoint() {
        repository = new GenreRepository();
    }
}
