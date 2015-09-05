package actions;

import util.MusicLibraryRequestException;

public interface Action {

    void run() throws MusicLibraryRequestException;

}
