package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

import java.util.Map;

public class OnetExtractor extends Extractor {

    public OnetExtractor(String feedUrl, String channel, Map<String, String> dict) {
        super(feedUrl, channel, dict);
    }

    @Override
    protected String getContent(Document doc) {
        Elements story = doc.getElementsByClass("articleBody");
        return story.text();
    }

    @Override
    protected Source getSource() {
        return Source.ONET;
    }
}
