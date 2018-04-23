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

    public Feed(){}

    public Feed(String id, String title, String link, String description, Date publishedDate, String content) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.publishedDate = publishedDate;
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format(
                "Feed[id=%s, title='%s', link='%s']",
                id, title, link);
    }
}
