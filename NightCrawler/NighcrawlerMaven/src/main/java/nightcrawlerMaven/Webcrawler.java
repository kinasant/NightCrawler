package nightcrawlerMaven;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class Webcrawler implements Runnable {

    public static final int MAX_DEPTH = 3;
    private final String firstLink;
    private final int id;
    private final Set<String> visitedUrls = new HashSet<>(); 

    public Webcrawler(String link, int num) {
        System.out.println("Nightcrawler " + num + " Rolling...");
        this.firstLink = link;
        this.id = num;
    }

    @Override
    public void run() {
        boolean success = crawl(1, firstLink);
        if (!success) {
            System.out.println("Error: Crawling failed for Nightcrawler " + id);
        }
    }

    private boolean crawl(int level, String url) {
        if (level <= MAX_DEPTH) {
            try {
                if (!visitedUrls.contains(url)) {
                    Document doc = request(url);
                    if (doc != null) {
                        System.out.println("\nID " + id + " Received at " + url);
                        String title = doc.title();
                        System.out.println(title);
                        visitedUrls.add(url);
                        for (Element link : doc.select("a[href]")) {
                            String nextLink = getValidUrl(link.absUrl("href")); 
                            if (nextLink != null) {
                                crawl(level + 1, nextLink);
                            }
                        }
                        return true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error: Crawling failed for " + url + " - " + e.getMessage());
            }
        }
        return false;
    }

    private Document request(String url) throws IOException {
        Connection con = Jsoup.connect(url);
        Document doc = con.get();
        if (con.response().statusCode() == 200) {
            return doc;
        } else {
            System.err.println("Error: HTTP Error " + con.response().statusCode() + " for " + url);
            return null;
        }
    }


    private String getValidUrl(String url) {
        try {
            URI uri = new URI(url);
            if (!uri.isAbsolute() || uri.getPath().isEmpty() || uri.getPath().endsWith(".jpg") || uri.getPath().endsWith(".png")) {
                return null;
            }
            return url;
        } catch (URISyntaxException e) {
            System.err.println("Error: Invalid URL format - " + url);
            return null;
        }
    }
}