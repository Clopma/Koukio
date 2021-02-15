package com.carloslopez.koukiotest.Tasks;

import com.carloslopez.koukiotest.Controllers.PostController;
import com.carloslopez.koukiotest.Entities.Post;
import com.carloslopez.koukiotest.Repositories.PostRepository;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReadFeed {

    private final Pattern pattern = Pattern.compile("/(\\d+)");
    private final String JORNAL_URL = "http://feeds.nos.nl/nosjournaal?format=xml";

    @Autowired
    PostController postController;

    Logger logger = LoggerFactory.getLogger(ReadFeed.class);

    @Scheduled(fixedRate = 300000)
    public void readFeed() {

            logger.info("Starting to read the feed.");
            try (XmlReader reader = new XmlReader(new URL(JORNAL_URL))) {
                SyndFeed feed = new SyndFeedInput().build(reader);
                //Latest 20 entries
                List<SyndEntry> entries = feed.getEntries();

                List<Post> posts = new ArrayList<>();

                for (SyndEntry e : entries) {
                    try {
                        posts.add(Post.builder()
                                .id(extractIdFromUri(e.getUri()))
                                .title(e.getTitle())
                                .description(e.getDescription().getValue())
                                .imageUrl(getImageFrom(e))
                                .publication(e.getPublishedDate()).build());
                    } catch (NoSuchElementException mappingException) {
                        logger.error("Couldn't map entry: " + mappingException.getMessage());
                    }
                }
                postController.updatePosts(posts);
                logger.info("Posts updated.");
            } catch (Exception e){
                e.printStackTrace();
                logger.error("Couldn't read from feed: " + e.getMessage());
            }
    }

    private String getImageFrom(SyndEntry entry) {
        Optional<SyndEnclosure> enclousure = entry.getEnclosures().stream().findAny();
        return enclousure.map(SyndEnclosure::getUrl).orElse(null);
    }

    private int extractIdFromUri(String uri) {
        Matcher m = pattern.matcher(uri);
        if(m.find()){
           return Integer.parseInt(m.group(1));
        } else {
            throw new NoSuchElementException("Couldn't extract any id form the uri: " + uri);
        }
    }


}
