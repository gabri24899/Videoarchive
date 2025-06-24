package fh.aalen.test;

import static org.testng.Assert.*;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import fh.aalen.video.Video;

 


public class GroupedTests extends AbstractVideoTestBase {

    private static final Logger log = LoggerFactory.getLogger(GroupedTests.class);
    
    @BeforeSuite(alwaysRun = true)
    public void beforeEntireSuite()  {
        log.info(" Test-Suite beginnt (BeforeSuite)");
       
    }

    @AfterSuite(alwaysRun = true )
    public void afterEntireSuite() {
        log.info(" Test-Suite beendet (AfterSuite)");
        
        
    }
    
    
   
    @BeforeClass (groups = {"crud"}) 
    public void videoInitialisation() throws InterruptedException {
        log.info("Starte Initialisierung der Testklasse");
        videoService.getAllVideos(); // optional zur Prüfung vorhandener Daten
        Thread.sleep(5000);
    }

    
    // Wird nach der gesamten Klasse aufgerufen, um Datenbank aufzuräumen.
     
    
    @AfterClass (groups = {"crud"})
    public void cleanUpAfterClass() throws InterruptedException {
        log.info("Nach der Testklasse: Lösche alle Videos");
        videoRepository.deleteAll();
    }
    
   

    //Testet ob ein Video korrekt erstellt und in der DB abgespeichert wird. (CREATE).
    
    @Test(priority = 1, groups = {"crud"})
    public void testCreateVideo() throws InterruptedException {
        log.info("CREATE-Test gestartet");

        Video testVideo = new Video("Inception", "12", "Träume in Träumen", "Thriller");
        latestVideo = videoService.addVideo(testVideo);

        Thread.sleep(5000); // Präsentationspause
        assertNotNull(latestVideo.getId());
        assertEquals(latestVideo.getTitle(), "Inception");

        log.info("✅ Video erstellt mit ID {}", latestVideo.getId());
    }

    //Ein Video wird gelesen anhand seiner ID (READ).
     
    @Test(priority = 2, groups = {"crud"})
    public void testReadVideoById() throws InterruptedException {
        log.info("READ (by ID)-Test gestartet");

        Video found = videoService.getVideoById(latestVideo.getId())
                                  .orElseThrow(() -> new AssertionError("❌ Video wurde nicht gefunden"));
        Thread.sleep(3000);
        
        assertEquals(found.getTitle(), "Inception");

        log.info("✅ Video erfolgreich geladen: {}", found.getTitle());
    }


    //testen der Aktualisierung eines bereits gespeicherten Videos. (UPDATE).
    @Test(priority = 4, groups = {"crud"})
    public void testUpdateVideo() throws InterruptedException {
        log.info("UPDATE-Test gestartet");

        Thread.sleep(3000);
        Video updated = videoService.updateVideo(latestVideo.getId(),
                new Video("Inception 2", "16", "Neue Ebenen des Traums", "SciFi"));
        latestVideo = updated;

        assertEquals(updated.getTitle(), "Inception 2");
        assertEquals(updated.getAge_rating(), "16");
        assertEquals(updated.getGenre(), "SciFi");   

        log.info("✅ Video aktualisiert: {}", updated.getTitle());
    }

   
    //testen ob das Video gelöscht wird ohne crud (DELETE).
    
    @Test
    public void testDeleteVideo() throws InterruptedException {
        log.info("DELETE-Test gestartet");

        Thread.sleep(3000);
        videoService.deleteVideo(latestVideo.getId());

        boolean videoExists = videoService.getVideoById(latestVideo.getId()).isPresent();
        Thread.sleep(3000);

        assertFalse(videoExists, "Video sollte nach dem Löschen nicht mehr vorhanden sein.");
        log.info("✅ Video gelöscht");
    }
}