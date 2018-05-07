package pl.edu.agh.ztis.newsanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.ztis.newsanalyzer.extractors.BBCExtractor;
import pl.edu.agh.ztis.newsanalyzer.extractors.TVNExtractor;
import pl.edu.agh.ztis.newsanalyzer.extractors.WSJExtractor;

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
                new BBCExtractor("http://feeds.bbci.co.uk/news/rss.xml", repository),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7085.xml", repository),
                new TVNExtractor("https://www.tvn24.pl/najwazniejsze.xml", repository));
        int extractedCount;
        for (Extractor extractor : extractors) {
            extractedCount = extractor.extract();
            System.out.printf("Extracted %d from %s", extractedCount, extractor.getFeedName());
        }

        System.out.printf("Repository current count: %d\n", repository.count());
	}
}
