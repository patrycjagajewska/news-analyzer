package pl.edu.agh.ztis.newsanalyzer;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Feed {

    @Id
    private String id;

    private String title;
    private String link;
    private String description;
    private Date publishedDate;
    private String content;
    private Source source;
    private String channel;

    public Feed(){}

    public Feed(String id, String title, String link, String description, Date publishedDate, String content, Source source, String channel) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.publishedDate = publishedDate;
        this.content = content;
        this.source = source;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return String.format(
                "Feed[id=%s, title='%s', link='%s']",
                id, title, link);
    }
}
