package com.carloslopez.koukiotest.tasks;

import com.carloslopez.koukiotest.KoukiotestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KoukiotestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadFeedIT {

    @Autowired
    ReadFeed readFeed;

    @Test
    public void retrievesEntriesFromRSSFeedTest() {

        assertEquals(20, readFeed.getLatest20Entries().size());

    }


}
