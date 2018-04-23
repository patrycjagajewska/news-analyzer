package pl.edu.agh.ztis.newsanalyzer;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedRepository extends MongoRepository<Feed, String> {

    public Feed findByTitle(String title);
    public Feed findByLink(String link);
    public List<Feed> findByPublishedDate(Date publishedDate);

}
