package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

import java.util.Map;

public class PAPExtractor extends Extractor {

    public PAPExtractor(String feedUrl, String channel, Map<String, String> dict) {
        super(feedUrl, channel, dict);
    }

    @Override
    protected String getContent(Document doc) {
        Element story = doc.getElementById("depesza");
        return story.text();
    }

    @Override
    protected Source getSource() {
        return Source.PAP;
    }
}
