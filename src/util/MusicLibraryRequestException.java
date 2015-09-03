package util;

public class MusicLibraryRequestException extends Exception {

    private int statusCode;

    public MusicLibraryRequestException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
