package pl.edu.agh.ztis.newsanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NewsAnalyzerApplication implements CommandLineRunner {

    @Autowired
    private FeedRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(NewsAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

        String[] feedUrls = {
                "http://feeds.bbci.co.uk/news/rss.xml",
                "http://www.wsj.com/xml/rss/3_7085.xml",
                "https://www.tvn24.pl/najwazniejsze.xml",
        };
        for (String feedUrl : feedUrls) {
            new Extractor(feedUrl, repository).extract();
        }

        System.out.printf("Repository current count: %d\n", repository.count());
	}
}
