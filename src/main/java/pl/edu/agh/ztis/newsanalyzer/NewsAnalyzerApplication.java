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
        DictReader dictReader = new DictReader();
        dict = dictReader.read(filepath);


        List<Extractor> extractors = Arrays.asList(
                new BBCExtractor("http://feeds.bbci.co.uk/news/rss.xml", repository, dict),
                new WSJExtractor("http://www.wsj.com/xml/rss/3_7085.xml", repository, dict),
                new TVNExtractor("https://www.tvn24.pl/najwazniejsze.xml", repository, dict));
        int extractedCount;
        for (Extractor extractor : extractors) {
            extractedCount = extractor.extract();
            System.out.printf("Extracted %d from %s", extractedCount, extractor.getFeedName());
        }


        System.out.printf("Repository current count: %d\n", repository.count());
	}
}
