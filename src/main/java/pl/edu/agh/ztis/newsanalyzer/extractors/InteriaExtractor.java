package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

import java.util.Map;

public class InteriaExtractor extends Extractor {

    public InteriaExtractor(String feedUrl, String channel, Map<String, String> dict) {
        super(feedUrl, channel, dict);
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
