package com.carloslopez.koukiotest.Tasks;

import com.carloslopez.koukiotest.Controllers.PostController;
import com.carloslopez.koukiotest.Entities.Post;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.carloslopez.koukiotest.Tasks.*")
public class ReadFeedTest {

    @InjectMocks
    ReadFeed readFeed;

    @Mock
    PostController postController;

    List<SyndEntry> mockedEntries;
    ArrayList<Post> posts = new ArrayList<>();

    @Before
    public void defaults() throws Exception {

        mockedEntries = defaultMockedEntries();

        //Mocks
        XmlReader xmlReaderMock = mock(XmlReader.class);

        PowerMockito.whenNew(XmlReader.class)
                .withAnyArguments().thenReturn(xmlReaderMock);

        SyndFeedInput feedInputMock = mock(SyndFeedInput.class);
        PowerMockito.whenNew(SyndFeedInput.class)
                .withNoArguments().thenReturn(feedInputMock);

        SyndFeed feedMock = mock(SyndFeed.class);


        PowerMockito.whenNew(ArrayList.class)
                .withNoArguments().thenReturn(posts);

        //Expectations
        when(feedInputMock.build(xmlReaderMock)).thenReturn(feedMock);
        when(feedMock.getEntries()).thenReturn(mockedEntries);


    }

    @Test
    public void entriesAreMappedTest() {

        //Execution
        readFeed.readFeed();

        //Validations
        assertEquals(5, posts.size());

        //Testing regex
        assertEquals(111, posts.get(0).getId());
        assertEquals(222, posts.get(1).getId());
        assertEquals(333, posts.get(2).getId());
        assertEquals(444, posts.get(3).getId());
        assertEquals(555, posts.get(4).getId());

        //Testing the other properties
        for (int i = 0; i < posts.size(); i++) {

            SyndEntry entry = mockedEntries.get(i);
            Post post = posts.get(i);

            assertEquals(entry.getTitle(), post.getTitle());
            assertEquals(entry.getDescription().getValue(), post.getDescription());
            assertEquals(entry.getEnclosures().get(0).getUrl(), post.getImageUrl());
            assertEquals(entry.getPublishedDate(), post.getPublication());
        }


    }

    @Test
    public void continuesExecutionAfterNotBeingAbleToParseID() {

        mockedEntries.get(0).setUri("http://this.doesnt.have/anyID/");
        readFeed.readFeed();
        assertEquals(4, posts.size());

    }

    private List<SyndEntry> defaultMockedEntries() {

        return Arrays.asList(
                buildMockedEntry("http://anything.that.may/haveAnID/111", "Wekdienst 14/2: Waarschijnlijk laatste schaatsdag • Verkiezingen Catalonië", "<p>In Egypte zijn de restanten van een 5000 jaar oude bierbrouwerij ontdekt. Volgens Egyptische en Amerikaanse onderzoekers is het complex in Abydos mogelijk de oudste grootschalige brouwerij ter wereld.</p>", "https://cdn.nos.nl/image/2021/02/14/715299/1008x567.jpg\t", new Date()),
                buildMockedEntry("http://without.subdomain/222", "Na 5 jaar weer ebola in Guinee", "Some html: <img src=\"http://feeds.feedburner.com/~r/nosjournaal/~4/KiGHfFY_pQs\" height=\"1\" width=\"1\" alt=\"\"/>", "https://cdn.nos.nl/image/2021/02/14/715299/1008x567.jpg\t", DateUtils.addDays(new Date(), 1)),
                buildMockedEntry("justDomain.com/333", "Mogelijk oudste bierbrouwerij ter wereld ontdekt in Egypte", "<p>De luchtvaartpolitie doet onderzoek naar de oorzaak van het ongeval, meldt de politie Groningen op Twitter.</p>", "https://cdn.nos.nl/image/2021/02/14/715299/1008x567.jpg\t", DateUtils.addDays(new Date(), -100)),
                buildMockedEntry("http://www.withAFinalBar.es/444/", "Stemhulpen weer online: 'Ze zetten vooral aan tot nadenken'", "Voor het eerst in vijf jaar zijn er weer gevallen van ebola opgedoken in het West-Afrikaanse Guinee.", "https://cdn.nos.nl/image/2021/02/14/715299/1008x567.jpg\t", DateUtils.addYears(new Date(), -500)),
                buildMockedEntry("numbersIn1234567890TheMiddle/555", "Piloot neergestort vliegtuigje in Groningen was man van 75", "Nu dook de ziekte op na de dood van een verpleegkundige, die op 1 februari werd begraven.", "https://cdn.nos.nl/image/2021/02/14/715299/1008x567.jpg\t", DateUtils.addYears(new Date(), -500))
        );
    }

    private SyndEntry buildMockedEntry(String uri, String title, String description, String urlImage, Date publication) {
        SyndEntry mockedEntry = new SyndEntryImpl();
        mockedEntry.setUri(uri);
        mockedEntry.setTitle(title);

        SyndContent content = new SyndContentImpl();
        content.setValue(description);
        mockedEntry.setDescription(content);

        SyndEnclosure enclosure = new SyndEnclosureImpl();
        enclosure.setUrl(uri);
        mockedEntry.getEnclosures().add(enclosure);

        mockedEntry.setPublishedDate(publication);
        return mockedEntry;
    }
}
