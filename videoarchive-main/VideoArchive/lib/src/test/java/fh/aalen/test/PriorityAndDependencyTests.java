package fh.aalen.test;

import static org.testng.Assert.*;


import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import fh.aalen.video.Video;

public class PriorityAndDependencyTests extends AbstractVideoTestBase {

    private static final Logger log = LoggerFactory.getLogger(PriorityAndDependencyTests.class);
    
    
    
    
    
    
    
    
     //Wird einmal vor allen Tests der Klasse aufgerufen.
     
    @BeforeClass
    public void videoInitialisation() throws InterruptedException {
        log.info(" Starte Initialisierung der Testklasse");
        videoService.getAllVideos(); // optional zur Prüfung vorhandener Daten
        Thread.sleep(5000);
    }

    
     //Wird nach der gesamten Klasse aufgerufen, um Datenbank aufzuräumen.
     
    @AfterClass
    public void cleanUpAfterClass() throws InterruptedException {
        log.info(" Nach der Testklasse: Lösche alle Videos");
        Thread.sleep(10000); 
        videoRepository.deleteAll();
    }

    
    // Wird vor jedem einzelnen Test aufgerufen. Zeigt aktuellen Testnamen.
     
    @BeforeMethod
    public void logTestStart(Method method) throws InterruptedException {
        log.info("\n\n▶️ Der aktuelle Test: {} wird ausgeführt\n", method.getName());
        Thread.sleep(2000); // kurze Pause zur Hervorhebung
    }

    
     //Erstellt ein neues Video und speichert es für spätere Verwendung.
     
    
   
    @Test(priority = 1 ) // 1 Passes
    public void AddVideoTest() throws InterruptedException {
        log.info(" AddVideoTest gestartet");

        Video testVideo = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        latestVideo = videoService.addVideo(testVideo);

        Thread.sleep(5000); // kurze Pause für Präsentation
        assertEquals(latestVideo.getTitle(), "Matrix");
        assertNotNull(latestVideo.getId());

        log.info("✅ Video hinzugefügt: {}", latestVideo.getTitle());
    }

    
     //Aktualisiert das zuvor erstellte Video. Läuft nur, wenn AddVideoTest erfolgreich war.
    
    
    @Test(priority = 3) // 4 Passes
    public void testUpdateVideo() throws InterruptedException {
        log.info(" Starte Update-Test für Video mit ID: {}", latestVideo.getId());

        Thread.sleep(3000); //Zeit zur manuellen DB-Prüfung vor Update

        Video update = new Video("Matrix Reloaded", "18", "Zweiter Teil", "Action");
        Video updated = videoService.updateVideo(latestVideo.getId(), update);
        latestVideo = updated;

        Thread.sleep(5000); //Zeit zur Prüfung nach dem Update

        assertEquals(updated.getTitle(), "Matrix Reloaded");
        assertEquals(updated.getAge_rating(), "18");
        assertEquals(updated.getDescription(), "Zweiter Teil");
        assertEquals(updated.getGenre(), "Action");

        log.info("✅ Update erfolgreich: {}", updated.getTitle());
    }
    
    
     //Test mit absichtlicher Falscherwartung – zu Demo-Zwecken.
    

  //3 Fail
    @Test(priority = 2) 
    public void testAddVideoFail() throws InterruptedException {
        log.info("🚨 Starte Demo-Fehltest");

        Video testVideo = new Video("Inception", "12", "Traum im Traum", "Thriller");

        Video savedVideo = videoService.addVideo(testVideo);

        Thread.sleep(5000); //Pause für Präsentation
        assertEquals(savedVideo.getTitle(), "Spongebob"); //absichtlich falsch

        log.info("❌ Sollte nicht erfolgreich sein");
    }

    
    
    
    
    
    
    
    
    
    
   // 2 fail
    @Test( dependsOnMethods = {"AddVideoTest"})
    public void testUpdateVideo2() throws InterruptedException {
        log.info(" Starte Update-Test für Video mit ID: {}", latestVideo.getId());

        Thread.sleep(30000); //Zeit zur manuellen DB-Prüfung vor Update

        Video update = new Video("The Dark Knight", "16", "Batman gegen Joker", "Action");

        Video updated = videoService.updateVideo(latestVideo.getId(), update);
        latestVideo = updated;

        Thread.sleep(5000); //Zeit zur Prüfung nach dem Update

        assertEquals(updated.getTitle(), "Matrix Reloaded");
        assertEquals(updated.getAge_rating(), "18");
        assertEquals(updated.getDescription(), "Zweiter Teil");
        assertEquals(updated.getGenre(), "Action");

        log.info("✅ Update erfolgreich: {}", updated.getTitle());
    }
    
    
    // 5 Skip
    @Test(dependsOnMethods = {"testUpdateVideo2"})
    public void testDeleteVideo() throws InterruptedException {
        Thread.sleep(3000); //vor dem Löschen

        videoService.deleteVideo(latestVideo.getId());

        Thread.sleep(3000); //nach dem Löschen

        boolean videoExists = videoService.getVideoById(latestVideo.getId()).isPresent();
        assertFalse(videoExists, "Video sollte nach dem Löschen nicht mehr vorhanden sein.");
    }


   
   
}
