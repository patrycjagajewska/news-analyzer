package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.FeedRepository;

import java.util.Map;


public class BBCExtractor extends Extractor {

    public BBCExtractor(String feedUrl, FeedRepository repository, Map<String, String> dict) {
        super(feedUrl, repository, dict);
    }

    protected String getContent(Document doc) {
        Elements story = doc.getElementsByTag("story-body__inner");
        return story.text();
    }
}
