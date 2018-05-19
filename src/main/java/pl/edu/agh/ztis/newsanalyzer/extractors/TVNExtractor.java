package pl.edu.agh.ztis.newsanalyzer.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.agh.ztis.newsanalyzer.Extractor;
import pl.edu.agh.ztis.newsanalyzer.Source;


public class TVNExtractor extends Extractor {

    public TVNExtractor(String feedUrl, String channel) {
        super(feedUrl, channel);
    }

    protected String getContent(Document doc) {
        Elements story = doc.getElementsByTag("article");
        if(story == null){
            story = doc.getElementsByClass("content");
        }
        return story.text();
    }

    public Source getSource(){
        return Source.TVN;
    }
}
