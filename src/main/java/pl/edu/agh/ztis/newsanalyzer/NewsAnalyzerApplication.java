package pl.edu.agh.ztis.newsanalyzer;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
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
		try {
			URL feedUrl = new URL("https://www.tvn24.pl/najwazniejsze.xml");

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));

			// Get the entry items...
			for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
				String uri = entry.getUri();
				String title = entry.getTitle();
				String link = entry.getLink();
				String description = entry.getDescription().getValue();
				Date publishedDate = entry.getPublishedDate();
				String content = extractText(entry);

				repository.save(new Feed(uri, title, link, description, publishedDate, content));
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		for (Feed feed : repository.findAll()) {
			System.out.println(feed);
		}
	}

	public static String extractText(SyndEntry entry) throws IOException {
		URL url = new URL(entry.getLink());
		URLConnection connection = url.openConnection();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(
						connection.getInputStream()));

		StringBuffer stringBuffer = new StringBuffer();
		String inputLine;
		StringBuilder buffer = new StringBuilder("");
		while ((inputLine = bufferedReader.readLine()) != null)
			buffer.append(inputLine);
		bufferedReader.close();

		Document doc = Jsoup.parseBodyFragment(buffer.toString());
		Elements story = doc.getElementsByTag("article"); //TVN
		return story.text();
	}

}
