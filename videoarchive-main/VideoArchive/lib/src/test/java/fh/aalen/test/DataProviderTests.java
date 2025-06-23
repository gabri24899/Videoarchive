package fh.aalen.test;

import static org.testng.Assert.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fh.aalen.video.Video;

public class DataProviderTests extends AbstractVideoTestBase {

    private static final Logger log = LoggerFactory.getLogger(DataProviderTests.class);

    @BeforeClass(alwaysRun = true)
    public void videoInitialisation() throws InterruptedException {
        log.info("📥 Starte Initialisierung der Testklasse");
        videoService.getAllVideos();
        Thread.sleep(2000);
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpAfterClass() throws InterruptedException {
        log.info("🧼 Nach der Testklasse: Lösche alle Videos");
        Thread.sleep(5000);
        videoRepository.deleteAll();
    }
    
    

    //übergeben von sinnvollen testGenres
    @DataProvider(name = "validGenres")
    public Object[][] validGenres() {
        return new Object[][] {
            {"Action"},
            {"SciFi"},
            {"Komödie"}
        };
    }

    @Test(dataProvider = "validGenres")
    public void testAddVideoWithValidGenre(String genre) throws InterruptedException {
        Video video = new Video("Genre-Test", "12", "Test für Genre", genre);
        Video saved = videoService.addVideo(video);
        Thread.sleep(1000);
        assertEquals(saved.getGenre(), genre);
    }
   
    //Eingabe von nicht sinnvollen Parametern wie negativ Alter, etc.
    @DataProvider(name = "videoCombinations")
    public Object[][] videoData() {
        return new Object[][] {
            {"Batman", "12", "Superheld rettet Gotham", "Action"},
            {"Planet Erde", "-1", "Negatives Alter", "Dokumentation"},
            {"", "16", "Leerer Titel", "Drama"},
            {"Ghostbusters", "", "Fehlende Altersfreigabe", "Comedy"},
            {"Interstellar", "PG", null, "SciFi"}
        };
    }

    @Test(dataProvider = "videoCombinations")
    public void testCreateVideoWithCombinations(String title, String rating, String description, String genre) {
        Video video = new Video(title, rating, description, genre);
        Video saved = videoService.addVideo(video);

        assertEquals(saved.getTitle(), title);
        assertEquals(saved.getAge_rating(), rating);
        assertEquals(saved.getDescription(), description);
        assertEquals(saved.getGenre(), genre);
    }
}