package com.carloslopez.koukiotest.tasks;

import com.carloslopez.koukiotest.controllers.PostController;
import com.carloslopez.koukiotest.entities.Post;
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

    private static final Pattern pattern = Pattern.compile("/(\\d+)");
    private static final String JORNAL_URL = "http://feeds.nos.nl/nosjournaal?format=xml";

    @Autowired
    PostController postController;

    Logger logger = LoggerFactory.getLogger(ReadFeed.class);

    @Scheduled(fixedRate = 300000)
    public void readFeedAndUpdatePosts() {
        List<SyndEntry> entries = getLatest20Entries();
        List<Post> posts = mapPosts(entries);
        postController.updatePosts(posts);
        logger.info("Finished reading RSS feed.");
    }

    public List<SyndEntry> getLatest20Entries() {
        logger.info("Starting to read the feed.");
        try (XmlReader reader = new XmlReader(new URL(JORNAL_URL))) {
            SyndFeed feed = new SyndFeedInput().build(reader);
            return feed.getEntries();
        } catch (Exception e) {
            logger.error(String.format("Couldn't read from feed: %s", e.getMessage()));
            return Collections.emptyList();
        }
    }

    private List<Post> mapPosts(List<SyndEntry> entries) {
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
                logger.error(String.format("Couldn't map entry: %s", mappingException.getMessage()));
            }
        }
        return posts;
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
