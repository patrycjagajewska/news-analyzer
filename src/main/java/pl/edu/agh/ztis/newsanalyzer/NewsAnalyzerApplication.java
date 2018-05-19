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

    private String filepath;
    private Map<String, String> dict;

	public static void main(String[] args) {
		SpringApplication.run(NewsAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	    filepath = "./src/main/resources/result_dict_v1-lowercase.csv";
        DictReader dictReader = new DictReader(filepath);
        dict = dictReader.read();


        List<Extractor> extractors = Arrays.asList(
                new TVNExtractor("https://www.tvn24.pl/najwazniejsze.xml", "najważniejsze", dict),
                new TVNExtractor("https://www.tvn24.pl/najnowsze.xml", "najnowsze", dict),
                new TVNExtractor("https://www.tvn24.pl/wiadomosci-ze-swiata,2.xml", "świat", dict),
                new TVNExtractor("https://www.tvn24.pl/biznes-gospodarka,6.xml", "biznes", dict),
                new InteriaExtractor("http://fakty.interia.pl/feed", "fakty", dict),
                new InteriaExtractor("http://fakty.interia.pl/prasa/feed", "prasa", dict),
                new InteriaExtractor("http://fakty.interia.pl/swiat/feed", "świat", dict),
                new InteriaExtractor("http://kanaly.rss.interia.pl/biznes.xml", "biznes", dict),
                new WPExtractor("https://wiadomosci.wp.pl/rss.xml", "wiadomości", dict),
                new OnetExtractor("https://wiadomosci.onet.pl/.feed", "wiadomości", dict),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/13", "społeczne", dict),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/1", "biznes", dict),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/25", "wiadomości", dict),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7085.xml", "world", dict),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7014.xml", "business", dict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/rss.xml", "news", dict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/world/rss.xml", "world", dict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/business/rss.xml", "business", dict),
                new BBCExtractor("http://feeds.bbci.co.uk/news/politics/rss.xml", "politics", dict),
                new NYTExtractor("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml", "news", dict)
        );
    
        int extractedCount;
        for (Extractor extractor : extractors) {
            extractedCount = extractor.extract(repository);
            System.out.printf("Extracted %d from %s\n", extractedCount, extractor.getFeedName());
        }


        System.out.printf("Repository current count: %d\n", repository.count());
	}
}
