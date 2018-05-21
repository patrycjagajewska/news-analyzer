package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

import java.util.Map;


public class WSJExtractor extends Extractor {

    public WSJExtractor(String feedUrl, String channel, Map<String, String> dict) {
        super(feedUrl, channel, dict);
    }

    protected String getContent(Document doc) {
        Elements story = doc.getElementsByClass("wsj-snippet-body");
        return story.text();
    }

    public Source getSource(){
        return Source.WSJ;
    }
}
