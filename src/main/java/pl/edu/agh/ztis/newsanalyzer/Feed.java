package pl.edu.agh.ztis.newsanalyzer;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Set;

public class Feed {

    @Id
    private String id;

    private String title;
    private String link;
    private String description;
    private Date publishedDate;
    private String content;
    private Set<String> tags;

    public Feed(){}

    public Feed(String id, String title, String link, String description,
                Date publishedDate, String content, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.publishedDate = publishedDate;
        this.content = content;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.format("Feed[id=%s, title='%s', link='%s', tags='%s']", id, title, link, tags);
    }
}
