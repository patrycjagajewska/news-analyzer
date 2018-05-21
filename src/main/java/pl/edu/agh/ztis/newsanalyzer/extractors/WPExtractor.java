package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

public class WPExtractor extends Extractor {

    public WPExtractor(String feedUrl, String channel) {
        super(feedUrl, channel);
    }

    @Override
    protected String getContent(Document doc) {
        Elements story = doc.getElementsByClass("article--text");
        return story.text();
    }

    @Override
    protected Source getSource() {
        return Source.WP;
    }
}
