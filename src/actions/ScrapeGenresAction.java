package actions;

import model.Genre;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.MusicLibraryRequestException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ScrapeGenresAction implements Action {

    @Override
    public void run() throws MusicLibraryRequestException {
        try {
//            Document doc = Jsoup.parse(new URL("http://rateyourmusic.com/rgenre/"), 60000);
            Document doc = Jsoup.parse(new File("genres.html"), "UTF-8", "http://rateyourmusic.com");

            ArrayList<ParsedGenre> topLevelGenres = parseGenres(doc);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MusicLibraryRequestException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private ArrayList<ParsedGenre> parseGenres(Document doc) {
        ArrayList<ParsedGenre> topLevelGenres = new ArrayList<>();
        HashMap<String, ParsedGenre> allGenres = new HashMap<>();

        // Find all genres that contain subgenres
        Elements supergenreElements = doc.select("#content > div:has(div)");
        for (int i = 0; i < supergenreElements.size() - 1; i++) {
            Element supergenreElement = supergenreElements.get(i);
            topLevelGenres.add(makeGenreWithSubgenres(supergenreElement, allGenres));
        }

        // Find all genres without subgenres
        Elements genreElements = doc.select("#content > a.genre");
        for (int i = 0; i < genreElements.size(); i++) {
            Element genreElement = genreElements.get(i);
            topLevelGenres.add(new ParsedGenre(genreElement.text()));
        }

        return topLevelGenres;
    }

    private ParsedGenre makeGenreWithSubgenres(Element element, HashMap<String, ParsedGenre> allGenres) {
        String name = element.child(0).child(0).text();
        ParsedGenre newGenre = new ParsedGenre(name);

        Element subgenreBlock = element.select("blockquote").get(0);
        for (Element block : subgenreBlock.children()) {
            // Find all genres that do not contain other genres (a.genre tags)
            if (block.tagName().equals("a") && block.className().equals("genre")) {
                if (allGenres.containsKey(block.text())) {
                    newGenre.subgenres.add(allGenres.get(block.text()));
                } else {
                    ParsedGenre subgenre = new ParsedGenre(block.text());
                    newGenre.subgenres.add(subgenre);
                    allGenres.put(block.text(), subgenre);
                }

            // Find all genres that contain other genres (div tags that contain blockquotes)
            } else if (block.tagName().equals("div") && block.children().size() > 0 && block.child(0).tagName().equals("b")) {
                if (allGenres.containsKey(block.text())) {
                    newGenre.subgenres.add(allGenres.get(block.text()));
                } else {
                    ParsedGenre subgenre = makeGenreWithSubgenres(block, allGenres);
                    newGenre.subgenres.add(subgenre);
                    allGenres.put(block.text(), subgenre);
                }
            }
        }
        return newGenre;
    }

    private static class ParsedGenre extends Genre {

        private static int uid = 1;

        ArrayList<ParsedGenre> subgenres;

        ParsedGenre(String name) {
            super(uid++, name);
            subgenres = new ArrayList<>();
        }

    }

}
