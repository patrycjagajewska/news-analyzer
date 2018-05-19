package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

public class InteriaExtractor extends Extractor {

    public InteriaExtractor(String feedUrl, String channel) {
        super(feedUrl, channel);
    }

    @Override
    protected String getContent(Document doc) {
        Elements story = doc.getElementsByClass("article-body");
        return story.text();
    }

    @Override
    protected Source getSource() {
        return Source.INTERIA;
    }
}
