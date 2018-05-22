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

    private Map<String, String> dict;
    private final String feedUrl;
    private final String channel;

    public Extractor(String feedUrl, String channel, Map<String, String> dict) {
        this.feedUrl = feedUrl;
        this.channel = channel;
        this.dict = dict;
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
          
            Feed feedObj = new Feed(uri, title, link, description, publishedDate, content, getSource(), channel);
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
        for (String key : this.dict.keySet()) {
            for (String text : texts) {
                text = text.toLowerCase().replaceAll("[,!?]", "");
                if (text.contains(key)) {
                    tags.add(this.dict.get(key));
                }
            }
        }
        return tags;
    }
}
