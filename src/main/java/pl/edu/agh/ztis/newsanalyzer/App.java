package pl.edu.agh.ztis.newsanalyzer;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class App
{

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
//        Elements story = doc.getElementsByClass("story-body__inner"); //BBC
//        Elements story = doc.getElementsByClass("wsj-snippet-body"); //WSJ
        Elements story = doc.getElementsByTag("article"); //TVN
        return story.text();
    }

    public static void main(String[] args) {

        try {
//            URL feedUrl = new URL("http://feeds.bbci.co.uk/news/rss.xml");
//            URL feedUrl = new URL("http://www.wsj.com/xml/rss/3_7085.xml");
            URL feedUrl = new URL("https://www.tvn24.pl/najwazniejsze.xml");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            System.out.println("Feed Title: " + feed.getTitle());

            // Get the entry items...
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
                System.out.println();
                System.out.println("Title: " + entry.getTitle());
                System.out.println("Unique Identifier: " + entry.getUri());
                System.out.println("Published Date: " + entry.getPublishedDate());
                System.out.println("Link: " + entry.getLink());

                // Get the Links
                for (SyndLinkImpl link : (List<SyndLinkImpl>) entry.getLinks()) {
                    System.out.println("Link: " + link.getHref());
                }

                // Get the Contents
                for (SyndContentImpl content : (List<SyndContentImpl>) entry.getContents()) {
                    System.out.println("Content: " + content.getValue());
                }

                // Get the Categories
                for (SyndCategoryImpl category : (List<SyndCategoryImpl>) entry.getCategories()) {
                    System.out.println("Category: " + category.getName());
                }

                System.out.println(extractText(entry));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}