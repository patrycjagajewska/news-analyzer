package pl.edu.agh.ztis.newsanalyzer;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;


public abstract class Extractor {

    private final String feedUrl;
    private final String channel;

    public Extractor(String feedUrl, String channel) {
        this.feedUrl = feedUrl;
        this.channel = channel;
    }

    private URL getFeedUrl() throws MalformedURLException {
        return new URL(feedUrl);
    }

    public String getFeedName() {
        return feedUrl;
    }

    protected abstract Source getSource();

    public String extractText(SyndEntry entry) throws IOException {
        URL url = new URL(entry.getLink());
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;
        StringBuilder buffer = new StringBuilder("");
        while ((inputLine = bufferedReader.readLine()) != null)
            buffer.append(inputLine);
        bufferedReader.close();

        Document doc = Jsoup.parseBodyFragment(buffer.toString());
        return getContent(doc);
    }

    protected abstract String getContent(Document doc);

    public int extract(FeedRepository repository) {
        System.out.printf("Extracting from %s\n", feedUrl);
        SyndFeedInput input;
        XmlReader reader;
        SyndFeed feed;

        int count = 0;
        try {
            URL feedUrl = getFeedUrl();
            input = new SyndFeedInput();
            reader = new XmlReader(feedUrl);
        } catch (IOException e) {
            System.err.printf("Incorrect url: %s, error: %s\n", feedUrl, e);
            return count;
        }
        try {
            feed = input.build(reader);
        } catch (FeedException e) {
            System.err.printf("Incorrect feed format, error: %s\n", e);
            return count;
        }
        for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
            String uri = entry.getUri();
            String title = entry.getTitle();
            String link = entry.getLink();
            String description = (entry.getDescription() == null) ? "" : entry.getDescription().getValue() ;
            Date publishedDate = entry.getPublishedDate();
            String content = null;
            try {
                content = extractText(entry);
            } catch (IOException e) {
                System.err.printf("Incorrect feed entry format, error: %s\n", e);
                continue;
            }
            repository.save(new Feed(uri, title, link, description, publishedDate, content, getSource(), channel));
            count++;
        }
        return count;
    }
}
