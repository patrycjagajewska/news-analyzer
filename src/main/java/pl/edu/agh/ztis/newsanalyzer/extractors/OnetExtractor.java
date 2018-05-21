package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

public class OnetExtractor extends Extractor {

    public OnetExtractor(String feedUrl, String channel) {
        super(feedUrl, channel);
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
