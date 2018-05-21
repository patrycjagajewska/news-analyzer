package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;

import java.util.Map;


public class BBCExtractor extends Extractor {

    public BBCExtractor(String feedUrl, String channel, Map<String, String> dict) {
        super(feedUrl, channel, dict);
    }

    protected String getContent(Document doc) {
        Elements story = doc.getElementsByClass("story-body");
        return story.text();
    }

    public Source getSource(){
        return Source.BBC;
    }
}
