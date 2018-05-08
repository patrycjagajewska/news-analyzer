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
import java.util.*;


public abstract class Extractor {

    private FeedRepository repository;
    private Map<String, String> dict;
    private final String feedUrl;

    public Extractor(String feedUrl, FeedRepository repository, Map<String, String> dict) {
        this.feedUrl = feedUrl;
        this.repository = repository;
        this.dict = dict;
    }

    private URL getFeedUrl() throws MalformedURLException {
        return new URL(this.feedUrl);
    }

    public String getFeedName() {
        return this.feedUrl;
    }

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
        return this.getContent(doc);
    }

    protected abstract String getContent(Document doc);

    public int extract() {
        System.out.printf("Extracting from %s\n", this.feedUrl);
        SyndFeedInput input;
        XmlReader reader;
        int count = 0;
        try {
            URL feedUrl = this.getFeedUrl();
            input = new SyndFeedInput();
            reader = new XmlReader(feedUrl);
        } catch (IOException e) {
            System.err.printf("Incorrect url: %s, error: %s\n", this.feedUrl, e);
            return count;
        }

        SyndFeed feed = null;
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
            String description = entry.getDescription().getValue();
            Date publishedDate = entry.getPublishedDate();
            String content = null;
            try {
                content = extractText(entry);
            } catch (IOException e) {
                System.err.printf("Incorrect feed entry format, error: %s\n", e);
                continue;
            }
            Feed feedObj = new Feed(uri, title, link, description, publishedDate, content);
            Set<String> tags = this.getTags(feedObj);
            feedObj.setTags(tags);
            repository.save(feedObj);
            count++;
        }
        return count;
    }

    private Set<String> getTags(Feed feed) {
        Set<String> tags = new HashSet<>();
        String[] texts = {feed.getTitle(), feed.getDescription(), feed.getContent()};
        for (String text : texts) {
            for (String word : text.split(" ")) {
                word = word.toLowerCase().replaceAll("[,!?]", "");
                ;
                if (this.dict.containsKey(word)) {
                    tags.add(this.dict.get(word));
                }
            }
        }
        return tags;
    }
}
