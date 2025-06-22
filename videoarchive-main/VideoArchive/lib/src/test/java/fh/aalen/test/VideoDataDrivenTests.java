package fh.aalen.test;

import static org.testng.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fh.aalen.video.Video;


/**
 * Diese Klasse enthält zwei Data-Driven Testfälle:
 * 1. Einfache Tests mit verschiedenen Genres
 * 2. Komplexe Kombinationen aus mehreren Feldern (Titel, Rating, Beschreibung, Genre)
 */
public class VideoDataDrivenTests extends AbstractVideoTestBase {

	 private static final Logger log = LoggerFactory.getLogger(GroupedTests.class);
	 
	@BeforeClass (alwaysRun = true) 
    public void videoInitialisation() throws InterruptedException {
        log.info("📥 Starte Initialisierung der Testklasse");
        videoService.getAllVideos(); // optional zur Prüfung vorhandener Daten
        Thread.sleep(5000);
    }

    
    // Wird nach der gesamten Klasse aufgerufen, um Datenbank aufzuräumen.
     
    
    @AfterClass (alwaysRun = true)
    public void cleanUpAfterClass() throws InterruptedException {
        log.info("🧼 Nach der Testklasse: Lösche alle Videos");
        videoRepository.deleteAll();
    }
    
    
    
    
	
    // 1️⃣ Einfacher DataProvider für verschiedene Genres
    @DataProvider(name = "genreProvider")
    public Object[][] provideGenres() {
        return new Object[][] {
            {"Action"}, {"SciFi"}, {"Dokumentation"}, {"Drama"}, {"Komödie"}
        };
    }

    @Test(dataProvider = "genreProvider", priority = 1, groups = {"dataprovider"})
    public void testAddVideoWithGenre(String genre) throws InterruptedException {
        Video video = new Video("Genre-Test", "12", "Test für Genre", genre);
        Video saved = videoService.addVideo(video);
        Thread.sleep(5000); // Für Präsentationszwecke
        assertEquals(saved.getGenre(), genre);
    }
    
    
    
    
    

    // 2️⃣ Erweiterter DataProvider mit mehreren Feldern
    @DataProvider(name = "videoCombinations")
    public Object[][] videoData() {
        return new Object[][] {
            {"Batman", "12", "Superheld rettet Gotham", "Action"},
            {"Planet Erde", "0", "Naturdokumentation", "Dokumentation"},
            {"", "16", "Leerer Titel", "Drama"},
            {"Ghostbusters", "", "Fehlende Altersfreigabe", "Comedy"},
            {"Interstellar", "PG", null, "SciFi"}
        };
    }

    @Test(dataProvider = "videoCombinations", priority = 2, groups = {"dataprovider"})
    public void testCreateVideoWithCombinations(String title, String rating, String description, String genre) {
        Video video = new Video(title, rating, description, genre);
        Video saved = videoService.addVideo(video);

        assertEquals(saved.getTitle(), title, "Titel stimmt nicht überein");
        assertEquals(saved.getAge_rating(), rating, "Altersfreigabe stimmt nicht überein");
        assertEquals(saved.getDescription(), description, "Beschreibung stimmt nicht überein");
        assertEquals(saved.getGenre(), genre, "Genre stimmt nicht überein");
    }
}
