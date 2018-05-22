package pl.edu.agh.ztis.newsanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.ztis.newsanalyzer.extractors.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class NewsAnalyzerApplication implements CommandLineRunner {

    @Autowired
    private FeedRepository repository;

    private Map<String, String> plDict;
    private Map<String, String> enDict;

	public static void main(String[] args) {
		SpringApplication.run(NewsAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        DictReader dictReader = new DictReader();
        plDict = dictReader.read("./src/main/resources/result_dict_v1_pl_extended.csv");
        enDict = dictReader.read("./src/main/resources/result_dict_v1_en_extended.csv");


        List<Extractor> extractors = Arrays.asList(
                new TVNExtractor("https://www.tvn24.pl/najwazniejsze.xml", "najważniejsze", plDict),
                new TVNExtractor("https://www.tvn24.pl/najnowsze.xml", "najnowsze", plDict),
                new TVNExtractor("https://www.tvn24.pl/wiadomosci-ze-swiata,2.xml", "świat", plDict),
                new TVNExtractor("https://www.tvn24.pl/biznes-gospodarka,6.xml", "biznes", plDict),
                new InteriaExtractor("http://fakty.interia.pl/feed", "fakty", plDict),
                new InteriaExtractor("http://fakty.interia.pl/prasa/feed", "prasa", plDict),
                new InteriaExtractor("http://fakty.interia.pl/swiat/feed", "świat", plDict),
                new InteriaExtractor("http://kanaly.rss.interia.pl/biznes.xml", "biznes", plDict),
                new WPExtractor("https://wiadomosci.wp.pl/rss.xml", "wiadomości", plDict),
                new OnetExtractor("https://wiadomosci.onet.pl/.feed", "wiadomości", plDict),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/13", "społeczne", plDict),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/1", "biznes", plDict),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/25", "wiadomości", plDict),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7085.xml", "world", enDict),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7014.xml", "business", enDict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/rss.xml", "news", enDict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/world/rss.xml", "world", enDict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/business/rss.xml", "business", enDict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/politics/rss.xml", "politics", enDict),
                new NYTExtractor("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml", "news", enDict)
        );
    
        int extractedCount;
        for (Extractor extractor : extractors) {
            extractedCount = extractor.extract(repository);
            System.out.printf("Extracted %d from %s\n", extractedCount, extractor.getFeedName());
        }


        System.out.printf("Repository current count: %d\n", repository.count());
	}
}
