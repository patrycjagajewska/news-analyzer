package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.FeedRepository;


public class BBCExtractor extends Extractor {
    public BBCExtractor(String feedUrl, FeedRepository repository) {
        super(feedUrl, repository);
    }

    protected String getContent(Document doc) {
        Elements story = doc.getElementsByTag("story-body__inner");
        return story.text();
    }
}
