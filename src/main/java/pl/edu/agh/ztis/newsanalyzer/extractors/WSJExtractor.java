package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.FeedRepository;

import java.util.Map;


public class WSJExtractor extends Extractor {

    public WSJExtractor(String feedUrl, FeedRepository repository, Map<String, String> dict) {
        super(feedUrl, repository, dict);
    }

    protected String getContent(Document doc) {
        Elements story = doc.getElementsByTag("wsj-snippet-body");
        return story.text();
    }
}
