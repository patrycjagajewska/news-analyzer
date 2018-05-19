package pl.edu.agh.ztis.newsanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.ztis.newsanalyzer.extractors.*;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class NewsAnalyzerApplication implements CommandLineRunner {

    @Autowired
    private FeedRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(NewsAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

        List<Extractor> extractors = Arrays.asList(
                new TVNExtractor("https://www.tvn24.pl/najwazniejsze.xml", "najważniejsze"),
                new TVNExtractor("https://www.tvn24.pl/najnowsze.xml", "najnowsze"),
                new TVNExtractor("https://www.tvn24.pl/wiadomosci-ze-swiata,2.xml", "świat"),
                new TVNExtractor("https://www.tvn24.pl/biznes-gospodarka,6.xml", "biznes"),
                new InteriaExtractor("http://fakty.interia.pl/feed", "fakty"),
                new InteriaExtractor("http://fakty.interia.pl/prasa/feed", "prasa"),
                new InteriaExtractor("http://fakty.interia.pl/swiat/feed", "świat"),
                new InteriaExtractor("http://kanaly.rss.interia.pl/biznes.xml", "biznes"),
                new WPExtractor("https://wiadomosci.wp.pl/rss.xml", "wiadomości"),
                new OnetExtractor("https://wiadomosci.onet.pl/.feed", "wiadomości"),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/13", "społeczne"),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/1", "biznes"),
                new PAPExtractor("http://centrumprasowe.pap.pl/cp/pl/rss/25", "wiadomości"),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7085.xml", "world"),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7014.xml", "business"),
                new BBCExtractor("http://feeds.bbci.co.uk/news/rss.xml", "news"),
                new BBCExtractor("http://feeds.bbci.co.uk/news/world/rss.xml", "world"),
                new BBCExtractor("http://feeds.bbci.co.uk/news/business/rss.xml", "business"),
                new BBCExtractor("http://feeds.bbci.co.uk/news/politics/rss.xml", "politics"),
                new NYTExtractor("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml", "news")
        );

        int extractedCount;
        for (Extractor extractor : extractors) {
            extractedCount = extractor.extract(repository);
            System.out.printf("Extracted %d from %s\n", extractedCount, extractor.getFeedName());
        }

        System.out.printf("Repository current count: %d\n", repository.count());
	}
}
